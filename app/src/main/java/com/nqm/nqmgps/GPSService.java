package com.nqm.nqmgps;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;

/**
 * Created by Ivan on 05/08/2016.
 */
public class GPSService  extends Service {
    LocationManager locationManager;
    GPSSensor gps;

    @Override
    public void onCreate() {

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //Ridiculous hacky check for current and previous version of android gps availability
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    gps = new GPSSensor(this, intent.getStringExtra("name") + ":" + intent.getStringExtra("secret"), intent.getStringExtra("asset"));
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, gps);
                }
            }
        }
        else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                gps = new GPSSensor(this, intent.getStringExtra("name") + ":" + intent.getStringExtra("secret"), intent.getStringExtra("asset"));
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 5000, 10, gps);

            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (gps != null) locationManager.removeUpdates(gps);
            }
        } else if(gps != null) locationManager.removeUpdates(gps);
    }
}
