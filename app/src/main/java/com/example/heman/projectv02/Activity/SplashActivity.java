package com.example.heman.projectv02.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.heman.projectv02.LocationFinder;
import com.example.heman.projectv02.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final String[] permissionRequired={android.Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean gpsEnabled;
    private List<String> pList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gpsEnabled = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pList=new ArrayList<String>();
            for(String s:permissionRequired){
                if(checkPermission(s))
                    pList.add(s);
            }
            setPermissions();
        }

        else {

            doThings();
        }
    }

    /*
     *This functions does all the Loading Work in SplashActivity
     */
    private void doThings(){
        LocationFinder.init(this, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location l = locationResult.getLastLocation();
                Toast.makeText(SplashActivity.this, "Location Result:\nLatitude: " + String.valueOf(l.getLatitude()) + "\nLongitude: " + String.valueOf(l.getLongitude() + "\nAltitude: " + String.valueOf(l.getAltitude())), Toast.LENGTH_LONG).show();
            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationFinder.startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationFinder.stopLocationUpdates();
    }

    //Permissions
    protected boolean checkPermission(String permission) {
        return ActivityCompat.checkSelfPermission(this,
                permission) == PackageManager.PERMISSION_GRANTED;
    }
    protected void setPermissions() {
        if(!pList.isEmpty()){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(R.string.saAlertTitle);
            builder.setMessage(R.string.saAlertMessage);
            builder.setPositiveButton(R.string.saPositiveButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(SplashActivity.this,pList.toArray
                            (new String[pList.size()]),PERMISSIONS_REQUEST_CODE);
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(SplashActivity.this,getString(R.string.saAlertCancelled),Toast.LENGTH_LONG).show();
                }
            });
            builder.setNeutralButton(R.string.saNeutralButtonText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSIONS_REQUEST_CODE){
            return;
        }

        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,getString(R.string.saPermissionDenied),Toast.LENGTH_LONG).show();
            }
        }

        doThings();

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