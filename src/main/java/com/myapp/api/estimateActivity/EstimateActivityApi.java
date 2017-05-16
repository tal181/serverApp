package com.myapp.api.estimateActivity;

import com.myapp.domain.activity.ActivityEstimateTimeDistance;

/**
 * Created by tal on 26/04/2017.
 */
public interface EstimateActivityApi {


    void addEstimateActivities(ActivityEstimateTimeDistance activityEstimateTime)  throws Exception;

    ActivityEstimateTimeDistance getEstimateActivities(String activityId) throws Exception;

}
