package com.myapp.domain.activity;

import java.util.List;

/**
 * Created by tal on 26/04/2017.
 */
public class EstimateTimeDistance {
    private String activityId;
    private Integer duration;
    private String distance;

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
