package com.devfolks.minorplantapp.registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devfolks.minorplantapp.R;
import com.devfolks.minorplantapp.databinding.ActivityAddCropBinding;
import com.devfolks.minorplantapp.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddCropActivity extends AppCompatActivity {
    private ActivityAddCropBinding binding;
    String crop;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddCropBinding.inflate(getLayoutInflater());
        View v= binding.getRoot();
        setContentView(v);
        firestore= FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser= fauth.getCurrentUser();

        final Calendar calendar= Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month= calendar.get(Calendar.MONTH);
        int day= calendar.get(Calendar.DAY_OF_MONTH);
        binding.cropSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getApplicationContext(), HomeFragment.class);
                startActivity(i);
            }
        });
        ArrayAdapter<CharSequence> crop_adapter=ArrayAdapter.createFromResource(this, R.array.crop,android.R.layout.simple_spinner_item);
        crop_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.selectCropSpinner.setAdapter(crop_adapter);
        binding.selectCropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    crop=adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(),crop,Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> area_adapter=ArrayAdapter.createFromResource(this,R.array.area,android.R.layout.simple_spinner_item);
       area_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.areaSpinner.setAdapter(area_adapter);
        binding.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    crop=adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(),crop,Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.sowingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddCropActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener,year,month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=dayOfMonth+"/"+month+"/"+year;
                binding.sowingDate.setText(date);
            }
        };
    }

}