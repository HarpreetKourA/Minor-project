package com.devfolks.minorplantapp.fragments;

import static android.text.TextUtils.concat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devfolks.minorplantapp.WeatherRVAdapter;
import com.devfolks.minorplantapp.WeatherRVModel;
import com.devfolks.minorplantapp.databinding.FragmentWeatherBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private ArrayList<WeatherRVModel> weatherRVModelArrayList;
    private WeatherRVAdapter weatherRVAdapter;
    private LocationManager locationManager;
    private String cityName;


    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        weatherRVModelArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(getContext(), weatherRVModelArrayList);
        /*binding.idRvWeather.setAdapter(weatherRVAdapter);
        cityName="Indore";
        getWeatherInfo(cityName);
        binding.idTVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city= binding.idEdtCity.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(getContext(),"Please enter valid city name",Toast.LENGTH_SHORT).show();
                }
                else{
                    binding.location.setText(city);
                    getWeatherInfo(city);
                }
            }
        });
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getContext(),
                    new String[]{
                            permission.ACCESS_FINE_LOCATION
                    });
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);*/
        return v;
    }

    private void getWeatherInfo(String cityName){
        String url="http://api.weatherapi.com/v1/forecast.json?key=e3895924c89b4234982171939222704&q="+cityName+"&days=6&aqi=no&alerts=no";
        Toast.makeText(getContext(),url,Toast.LENGTH_SHORT).show();
        binding.location.setText(cityName);
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.idPBLoading.setVisibility(View.GONE);
                        binding.homeRL.setVisibility(View.VISIBLE);
                        weatherRVModelArrayList.clear();
                        try{
                            String temperature= response.getJSONObject("current").getString("temp_c");
                            binding.idDegree.setText(temperature+"°C");
                            int isDay= response.getJSONObject("current").getInt("is_day");
                            String wind=response.getJSONObject("current").getString("wind_kph");
                            binding.idWind.setText(wind +"km/hr");
                            String humidity=response.getJSONObject("current").getString("humidity");
                            binding.idHumidity.setText(humidity +"%");
                            String pressure=response.getJSONObject("current").getString("pressure_mb");
                            binding.idPressure.setText(pressure);
                            String condition= response.getJSONObject("current").getJSONObject("condition").getString("text");
                            binding.idMain.setText(condition);
                            String conditionIcon= response.getJSONObject("current").getJSONObject("condition").getString("icon");
                            Picasso.get().load("http:"+ concat(conditionIcon)).into(binding.weatherImage);
                            JSONObject forecastObj= response.getJSONObject("forecast");
                            JSONArray forcastDayArray= forecastObj.getJSONArray("forecastday");
                            for(int i=0;i<forcastDayArray.length(); i++){
                                JSONObject dayObj= forcastDayArray.getJSONObject(i);
                                String date= dayObj.getString("date");
                                String temp= dayObj.getJSONObject("day").getString("avgtemp_c");
                                String winds= dayObj.getJSONObject("day").getString("maxwind_kph");
                                String icon= dayObj.getJSONObject("day").getJSONObject("condition").getString("icon");
                                weatherRVModelArrayList.add(new WeatherRVModel(date, temp,icon, winds));

                            }
                            weatherRVAdapter.notifyDataSetChanged();
                            }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Toast.makeText(getContext(),"Please enter valid city name...",Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(jsObjRequest);
    }


}
