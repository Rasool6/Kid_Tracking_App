package com.example.kid_tracking_app1;

public class HistoryModel {
    String time;
    String lat;
    String lon;
    String pin_id;

    public HistoryModel(String time, String lat, String lon, String pin_id) {
        this.time = time;
        this.lat = lat;
        this.lon = lon;
        this.pin_id = pin_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPin_id() {
        return pin_id;
    }

    public void setPin_id(String pin_id) {
        this.pin_id = pin_id;
    }
}
