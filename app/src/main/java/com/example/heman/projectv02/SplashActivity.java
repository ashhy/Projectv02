package com.example.heman.projectv02;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private boolean gpsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsEnabled = false;
        if (checkPermissions()){
            Toast.makeText(this, "You have Permission", Toast.LENGTH_LONG).show();
        }else {
            setPermissions();
            checkPermissions();
        }
    }

    //Permissions
    protected boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    protected void setPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE){
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults){
            if (result != PackageManager.PERMISSION_GRANTED){
                isGranted = false;
                break;
            }
        }
        if (isGranted){
            isLocationServiceEnabled();
            checkLocationEnabled();
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "This app requires location permission to work properly", Toast.LENGTH_LONG).show();
        }
    }

    //Location Enabled
    public void isLocationServiceEnabled() {
        try {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){}
    }

    public void checkLocationEnabled(){
        if (gpsEnabled){
            Toast.makeText(this, "GPS Enabled", Toast.LENGTH_SHORT).show();
        }else {
             Toast.makeText(this, "Please Enable Location", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }


}