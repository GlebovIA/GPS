package com.example.gps;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    TextView tvResult;
    int ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvResult = findViewById(R.id.tvResult);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    LocationListener _LocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if(location == null) return;
            else{
                String message = "";
                if(location.getProvider().equals(LocationManager.GPS_PROVIDER)){
                    message += "Местоположение определено с помощью GPS: долгота - " + location.getLatitude() + " широта - " + location.getLongitude();
                }
                if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
                    message += "Местоположение определено с помощью интернета: долгота - " + location.getLatitude() + " широта - " + location.getLongitude();
                }
                tvResult.setText(message);
            }
        }
    };
}