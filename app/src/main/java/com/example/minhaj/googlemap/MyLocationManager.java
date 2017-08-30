package com.example.minhaj.googlemap;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by minhaj on 17/08/2017.
 */

public class MyLocationManager extends Service implements LocationListener {

    private final int MAX_DISTANCE = 2;
    private final int MAX_TIME = 2000;

    private Context context;
    private MapsActivity mapsActivity;
    private LocationManager locationManager;
    private String locationProvider;

    public MyLocationManager(Context context) {
        this.context = context;
        mapsActivity = (MapsActivity) context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        setProvider();
        requestForLocation();
    }

    private void setProvider() {

        locationProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ? LocationManager.NETWORK_PROVIDER : null;
        locationProvider = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ? LocationManager.GPS_PROVIDER : null;
        Log.d("location","provider="+locationProvider);
    }

    private void requestForLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        if (locationProvider !=null) {
            Log.d("location","not null");
            locationManager.requestLocationUpdates(locationProvider, MAX_TIME, MAX_DISTANCE, this);
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.d("location","location");
        mapsActivity.placeMarker(location.getLatitude(),location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
