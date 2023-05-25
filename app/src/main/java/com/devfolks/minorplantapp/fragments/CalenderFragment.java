package com.devfolks.minorplantapp.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.devfolks.minorplantapp.AlarmReceiver;
import com.devfolks.minorplantapp.databinding.FragmentCalenderBinding;

import java.util.Calendar;


public class CalenderFragment extends Fragment  {
    private FragmentCalenderBinding binding;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    String name, description;
    String channelId="channel1";
    int hour, min, day, month, year;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    public CalenderFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentCalenderBinding.inflate(getLayoutInflater());
        View v= binding.getRoot();
        Context context= getContext();
        createNotificationChannel(context);
        binding.selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
            }
        });
        binding.selectTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });
        binding.createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleNotification();
            }
        });
        return v;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(Context context) {
        String name="Minor App";
        String desc="Description of channel";
        int importance= NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel= new NotificationChannel(channelId,name,importance);
        channel.setDescription(description);
        NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void scheduleNotification() {
        Intent i= new Intent(getContext(), AlarmReceiver.class);
        String title= binding.eventName.getText().toString();
        String message= binding.eventDescription.getText().toString();
        i.putExtra("titleExtra",title);
        i.putExtra("messageExtra",message);
        Toast.makeText(getContext(),"Event created"+title+message,Toast.LENGTH_SHORT).show();
        PendingIntent pendingIntent= PendingIntent.getBroadcast(getContext(), 1, i,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager= (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Calendar c= Calendar.getInstance();
        c.set(year,month+1,day);
        Long time= c.getTimeInMillis();
        Toast.makeText(getContext(),"year"+year+"month"+month+"day"+day+"hour"+hour+"min"+min+" "+time,Toast.LENGTH_SHORT).show();
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,time, pendingIntent);

    }

    private Long getTime(String date, String time) {
        int getMinute= min;
        int getHour= hour;
        int getDay= day;
        int getMonth= month;
        int getYear= year;

        Calendar c= Calendar.getInstance();
        c.set(year, month, day, hour, min);
        return c.getTimeInMillis();
    }

    private void showTimeDialog() {
        timePickerDialog= new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        hour= h;
                        min= m;
                        Calendar c=Calendar.getInstance();
                        c.set(0,0,0,hour,min);
                        Toast.makeText(getContext(), "time"+hour+min, Toast.LENGTH_SHORT).show();
                        binding.selectTimeBtn.setText(DateFormat.format("hh:mm aa",c));
                    }
                },12,0,false);
        timePickerDialog.updateTime(hour,min);
        timePickerDialog.show();
    }

    private void showCalendar() {
        Calendar c= Calendar.getInstance();
        year= c.get(Calendar.YEAR);
        month= c.get(Calendar.MONTH);
        day= c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date= day+"/"+(month+1)+"/"+year;
                binding.selectDateBtn.setText(date);
            }
        },year,month,day);
        datePickerDialog.updateDate(year,month,day);
        datePickerDialog.show();

    }
}