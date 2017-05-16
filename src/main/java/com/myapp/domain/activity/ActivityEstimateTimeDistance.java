package com.myapp.domain.activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tal on 16/05/2017.
 */
public class ActivityEstimateTimeDistance {
    private String activityId;
    private List<EstimateTimeDistance> activities;

    public ActivityEstimateTimeDistance(){
        activities =new ArrayList<>();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public List<EstimateTimeDistance> getActivities() {
        return activities;
    }

    public void setActivities(List<EstimateTimeDistance> activities) {
        this.activities = activities;
    }

}
