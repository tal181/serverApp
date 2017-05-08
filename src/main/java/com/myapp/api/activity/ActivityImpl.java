package com.myapp.api.activity;

import com.google.gson.Gson;
import com.myapp.DB.MongoActivityHelper;
import com.myapp.config.TablesScheme;
import com.myapp.domain.activity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tal on 26/04/2017.
 */
@Component
public class ActivityImpl implements ActivityApi{
    @Autowired
    MongoActivityHelper mongoActivityHelper;

    public List<Activity> getBestActivitiesByLocation(String location,Integer countResults) throws Exception{
        List<Activity> categoriesByLocation=mongoActivityHelper.
                getBestActivitiesByLocation(TablesScheme.ACTIVITIES_TABLE,location,countResults);

        return categoriesByLocation;
    }
    public List<Activity> getActivitiesByLocation(String location) throws Exception{

            List<Activity> categoriesByLocation=mongoActivityHelper.
                    find(TablesScheme.ACTIVITIES_TABLE,location);

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
}
