package com.example.wearos_app;

import java.util.Date;

public class DataSnap {
    final String timestamp;
    final float heartrate;

    public DataSnap(String timestamp, float heartrate) {
        this.timestamp = timestamp;
        this.heartrate = heartrate;
    }
}
