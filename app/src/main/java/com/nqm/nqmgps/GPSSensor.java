package com.nqm.nqmgps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 05/08/2016.
 */
public class GPSSensor implements LocationListener {
    Context context;
    private List<GPSReading> gps;
    DataExporter exporter;

    public GPSSensor(Context _context, String access, String asset) {
        this.context = _context;
        this.exporter = new DataExporter(access, _context, asset);
        exporter.getAccessToken();
        gps = new ArrayList<GPSReading>();
    }

    @Override
    public void onLocationChanged(Location loc) {
        gps.add(new GPSReading(loc.getLatitude(), loc.getLongitude(), loc.getTime()));

        if (gps.size() >  1) {
            JSONArray json = new JSONArray();
            try {
                for(int i = 0; i < gps.size(); i++) {
                    JSONObject data = new JSONObject();
                    data.put("timestamp", gps.get(i).getTime());
                    data.put("lat", gps.get(i).getLat());
                    data.put("lon", gps.get(i).getLon());
                    json.put(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(exporter.exportData(json)) gps.clear();

        }
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
