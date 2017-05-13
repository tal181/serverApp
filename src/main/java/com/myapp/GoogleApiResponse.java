package com.myapp;

/**
 * Created by Tal on 11/05/2017.
 */
public class GoogleApiResponse {

    String duration;
    String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "GoogleApiResponse{" +
                "duration='" + duration + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
