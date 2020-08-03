package com.codersofblvkn.criminaltagging.Utils;

import android.os.Parcel;

import java.io.Serializable;

public class Violence implements Serializable {
    double latitude;
    double longitude;
    int id;
    String timestamp;
    boolean valid;
    String videoPath;

    protected Violence(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        id = in.readInt();
        timestamp = in.readString();
        valid = in.readByte() != 0;
        videoPath = in.readString();
    }

    @Override
    public String toString() {
        return "Violence{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", valid=" + valid +
                ", videoPath='" + videoPath + '\'' +
                '}';
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public Violence(double latitude, double longitude, int id, String timestamp, boolean valid, String videoPath) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.timestamp = timestamp;
        this.valid = valid;
        this.videoPath = videoPath;
    }


}
