package com.myapp;

/**
 * Created by Tal on 11/05/2017.
 */
public class GoogleApiResponse {

    Integer duration;
    String distance;

    public GoogleApiResponse(){
        duration=Constants.DEFAULT_SUGGESTED_DURATION;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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
