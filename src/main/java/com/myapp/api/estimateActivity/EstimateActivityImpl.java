package com.myapp.api.estimateActivity;

import com.google.gson.Gson;
import com.myapp.DB.MongoEstimateActivityTimeHelper;
import com.myapp.config.TablesScheme;
import com.myapp.domain.activity.ActivityEstimateTimeDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tal on 26/04/2017.
 */
@Component
public class EstimateActivityImpl implements EstimateActivityApi {
    @Autowired
    MongoEstimateActivityTimeHelper mongoEstimateActivityTimeHelper;


    @Override
    public void addEstimateActivities(ActivityEstimateTimeDistance activityEstimateTime) throws Exception {
        Gson gson = new Gson();
        String activityEstimateTimeJson = gson.toJson(activityEstimateTime);
        mongoEstimateActivityTimeHelper.save(TablesScheme.ACTIVITIES_ESTIMATE_TIME_TABLE,activityEstimateTimeJson);
    }

    @Override
    public ActivityEstimateTimeDistance getEstimateActivities(String activityId) throws Exception {
        return mongoEstimateActivityTimeHelper.find(TablesScheme.ACTIVITIES_ESTIMATE_TIME_TABLE, activityId);
    }
}
