package com.devfolks.minorplantapp.registration;

import static android.location.LocationManager.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devfolks.minorplantapp.R;
import com.devfolks.minorplantapp.databinding.ActivityAddAddressBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddAddressActivity extends AppCompatActivity implements LocationListener {
    private ActivityAddAddressBinding binding;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        binding= ActivityAddAddressBinding.inflate(getLayoutInflater());
        View v= binding.getRoot();
        setContentView(v);
        if(ContextCompat.checkSelfPermission(AddAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddAddressActivity.this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
        binding.addressSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AddAddressActivity.this, AddCropActivity.class);
                startActivity(i);
            }
        });
        binding.enterAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });


    }
    @SuppressLint("MissingPermission")
    private void getLocation(){
        try{
            locationManager=(LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(GPS_PROVIDER,5000,5, (LocationListener) AddAddressActivity.this);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(AddAddressActivity.this,location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_SHORT).show();

        try{
            Geocoder geocoder=new Geocoder(AddAddressActivity.this, Locale.getDefault());
            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address=addresses.get(0).getAddressLine(0);
            binding.enterAddress.setText(address);
            Toast.makeText(AddAddressActivity.this,address,Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}