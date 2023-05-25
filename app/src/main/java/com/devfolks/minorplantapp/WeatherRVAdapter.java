package com.devfolks.minorplantapp;

import static android.text.TextUtils.concat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeatherRVModel> weatherRVModalArraylist;

    public WeatherRVAdapter(Context context, ArrayList<WeatherRVModel> weatherRVModalArraylist) {
        this.context = context;
        this.weatherRVModalArraylist = weatherRVModalArraylist;
    }

    @NonNull
    @Override
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.weather_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder holder, int position) {
        WeatherRVModel model= weatherRVModalArraylist.get(position);
        holder.temperature.setText(model.getTemperature()+"°C");
        Picasso.get().load("http:"+concat(model.getIcon())).into(holder.condition);
        holder.wind.setText(model.getWindSpeed()+"km/hr");
        holder.date.setText(model.getDate());

    }

    @Override
    public int getItemCount() {


        return weatherRVModalArraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView wind, temperature, date;
        private ImageView condition;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wind=itemView.findViewById(R.id.idTVWindSpeed);
            temperature=itemView.findViewById(R.id.idTVTemperature);
            date=itemView.findViewById(R.id.idTVDate);
            condition=itemView.findViewById(R.id.idTVCondition);
        }


    }
}
