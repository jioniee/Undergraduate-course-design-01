package com.example.comp2100groupproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DepartNearby extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationtext;
    TextView Nearest;
    String nearplace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_nearby);

        locationtext = (TextView) findViewById(R.id.locationtext);
        Nearest = (TextView) findViewById(R.id.nearest);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationtext.setText("Latitude  : " + location.getLatitude() + ", " + "Longitude : " + location.getLongitude());
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
        }
    }

    public void getLocation(View v) {
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
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        longitude = longitude * 10;
        double latitude = location.getLatitude();
        latitude = latitude * 10;

        nearplace = compare(longitude,latitude);

        Nearest.setText("Nearest: " + nearplace);
    }

    public void letsgo(View v) {
        String depa = "From = " + nearplace;
        Intent intent = new Intent(getApplicationContext(), resultTicket.class);
        intent.putExtra("QUERY", depa);
        intent.putExtra("RevisedQuery", depa);
        String check = "false";
        intent.putExtra("Check", check);
        startActivity(intent);
    }

    public static String compare(double longitude, double latitude) {
        double canberra_long = 1491.2;
        double canberra_lati = -352.8;
        double sydney_long = 1512.1;
        double sydney_lati = -338.7;
        double melbourne_long = 1449.6;
        double melbourne_lati = -378.1;
        double brisbane_long = 1530.2;
        double brisbane_lati = -274.7;
        double perth_long = 1158.6;
        double perth_lati = -319.5;

        double distance_canberra = Math.round(Math.sqrt(Math.pow(longitude - canberra_long, 2) + Math.pow(latitude - canberra_lati, 2)));
        double distance_sydney = Math.round(Math.sqrt(Math.pow(longitude - sydney_long, 2) + Math.pow(latitude - sydney_lati, 2)));
        double distance_melbourne = Math.round(Math.sqrt(Math.pow(longitude - melbourne_long, 2) + Math.pow(latitude - melbourne_lati, 2)));
        double distance_brisbane = Math.round(Math.sqrt(Math.pow(longitude - brisbane_long, 2) + Math.pow(latitude - brisbane_lati, 2)));
        double distance_perth = Math.round(Math.sqrt(Math.pow(longitude - perth_long, 2) + Math.pow(latitude - perth_lati, 2)));

        List<Double> alldis = Arrays.asList(distance_canberra,distance_sydney,distance_melbourne,distance_brisbane,distance_perth);
        double distance = 10000000000000.0;
        for (double dis : alldis) {
            if (dis < distance) {
                distance = dis;
            }
        }
        if (distance == distance_canberra)
            return "Canberra";
        if (distance == distance_sydney)
            return "Sydney";
        if (distance == distance_melbourne)
            return "Melbourne";
        if (distance == distance_brisbane)
            return "Brisbane";
        if (distance == distance_perth)
            return "Perth";

        return "hahahahaha";
    }

}
