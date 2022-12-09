package com.example.wearos_app;

public class DataSnap {
    final String timestamp;
    final float heartrate;
    final double latitude;
    final double longitude;
    final double altitude;
    final float speed;

    public DataSnap(String timestamp, float heartrate, double lat, double lng, double alt, float speed) {
        this.timestamp = timestamp;
        this.heartrate = heartrate;
        this.latitude = lat;
        this.longitude = lng;
        this.altitude = alt;
        this.speed = speed;
    }
}
