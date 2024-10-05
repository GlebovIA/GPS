package com.example.gps;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onResume() {
        super.onResume();
        if (GetPermissionGPS() == false) {
            return;
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, _LocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, _LocationListener);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        locationManager.removeUpdates(_LocationListener);
    }

    public Boolean GetPermissionGPS() {
        ACCESS_FINE_LOCATION = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        ACCESS_COARSE_LOCATION = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        return ACCESS_FINE_LOCATION == PackageManager.PERMISSION_GRANTED || ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED;
    }

    public void OnGetGPS(View view) {
        if (GetPermissionGPS() == false) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
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