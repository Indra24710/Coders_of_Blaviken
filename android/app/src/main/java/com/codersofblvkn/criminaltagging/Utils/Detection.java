package com.codersofblvkn.criminaltagging.Utils;

import java.io.Serializable;

public class Detection implements Serializable {

    double latitude;
    double longitude;
    long timestamp;
    String img;
    int id;
    int cid;

    public Detection(double latitude, double longitude, long timestamp, String img, int id, int cid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.img = img;
        this.id = id;
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Detection{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                ", img='" + img + '\'' +
                ", id=" + id +
                ", cid=" + cid +
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
