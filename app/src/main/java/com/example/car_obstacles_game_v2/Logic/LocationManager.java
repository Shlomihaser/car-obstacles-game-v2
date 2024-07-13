package com.example.car_obstacles_game_v2.Logic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.car_obstacles_game_v2.Interfaces.UpdateLocationCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationManager {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static volatile LocationManager instance = null;
    private LocationCallback locationCallback;
    private UpdateLocationCallback updateLocationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private Context context;
    private double lat;
    private double lon;

    public LocationManager setUpdateLocationCallback(UpdateLocationCallback updateLocationCallback) {
        this.updateLocationCallback = updateLocationCallback;
        return this;
    }

    public LocationManager(Context context){
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        this.context = context;
    }
    public static LocationManager init (Context context){
        if(instance == null)
        {
            synchronized (LocationManager.class){
                if(instance == null)
                {
                    instance = new LocationManager(context);

                }
            }
        }
        return getInstance();
    }
    public static LocationManager getInstance(){
        return instance;
    }



    public static boolean checkLocationPermission(Context context) {
        return (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED);
    }

    public void requestLocationPermission(Context context){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }

    public void getDeviceLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(10000); // 10 seconds
                locationRequest.setFastestInterval(5000); // 5 seconds
                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int index = locationResult.getLocations().size() - 1;
                            lat = locationResult.getLocations().get(index).getLatitude();
                            lon = locationResult.getLocations().get(index).getLongitude();
                            Log.d("latlon:",lat + "," + lon);
                            if (updateLocationCallback != null) {
                                updateLocationCallback.updateLocation(lat, lon);
                            }
                            stopLocation();
                        }
                    }
                };
                fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper());
            } else {
                requestLocationPermission(context);
            }
        }
    }


    public void stopLocation(){
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }





}
