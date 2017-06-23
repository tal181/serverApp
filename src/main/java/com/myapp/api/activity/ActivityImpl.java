package com.myapp.api.activity;

import com.google.gson.Gson;
import com.myapp.DB.MongoActivityHelper;
import com.myapp.DB.MongoUserCategoryHelper;
import com.myapp.DB.MongoUserHelper;
import com.myapp.config.TablesScheme;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.user.UserCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tal on 26/04/2017.
 */
@Component
public class ActivityImpl implements ActivityApi{
    @Autowired
    MongoActivityHelper mongoActivityHelper;
    @Autowired
    MongoUserCategoryHelper mongoUserCategoryHelper;

    public List<Activity> getBestActivitiesByLocation(String location,Integer countResults) throws Exception{
        List<Activity> categoriesByLocation=mongoActivityHelper.
                getBestActivitiesByLocation(TablesScheme.ACTIVITIES_TABLE,location,countResults);

        return categoriesByLocation;
    }
    public List<Activity> getActivitiesByLocation(String locationId) throws Exception{

            List<Activity> categoriesByLocation=mongoActivityHelper.
                    findByLocation(TablesScheme.ACTIVITIES_TABLE,locationId);

            return categoriesByLocation;

        }

    @Override
    public void addActivities(List<Activity> activities) {

        Gson gson = new Gson();
        String activitiesDetails = gson.toJson(activities);
        activitiesDetails.toString();
        mongoActivityHelper.save(TablesScheme.ACTIVITIES_TABLE,activitiesDetails);

    }

    @Override
    public void deleteAllRecords() {
        mongoActivityHelper.remove(TablesScheme.ACTIVITIES_TABLE);
    }

    @Override
    public Activity getActivityById(String activityId)  throws Exception{
        Activity activity= mongoActivityHelper.findById(TablesScheme.ACTIVITIES_TABLE,activityId);
        return activity;
    }
    @Override
    public List<Activity> getActivitiesByLocationAndLoginName(String locationId,String loginName) throws Exception{
        List<UserCategory> cats=mongoUserCategoryHelper.find(TablesScheme.USER_CATEGORY_RATING_TABLE,loginName);

        List<Activity> activities= mongoActivityHelper.
                getActivitiesByLocationAndLoginName(TablesScheme.ACTIVITIES_TABLE,cats);

        return activities;


    }
}
