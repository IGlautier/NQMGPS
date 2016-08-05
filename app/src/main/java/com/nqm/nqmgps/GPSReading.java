package com.nqm.nqmgps;

/**
 * Created by Ivan on 05/08/2016.
 */
public class GPSReading extends SensorReading {
    double lat, lon;

    public GPSReading(double _lat, double _lon, long _timestamp) {
        super((float) _lat, _timestamp);
        this.lat = _lat;
        this.lon = _lon;
    }

    public double getLat() { return this.lat;}

    public double getLon() { return this.lon;}
}
