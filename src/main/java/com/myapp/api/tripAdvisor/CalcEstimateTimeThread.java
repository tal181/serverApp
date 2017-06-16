package com.myapp.api.tripAdvisor;

import com.google.maps.model.TravelMode;
import com.myapp.GoogleApiHelper;
import com.myapp.api.activity.ActivityApi;
import com.myapp.api.estimateActivity.EstimateActivityApi;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.activity.ActivityEstimateTimeDistance;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * Created by Tal on 13/05/2017.
 */
@Component
public class CalcEstimateTimeThread {

    @Autowired
    GoogleApiHelper gogleApiHelper;

    @Autowired
    ActivityApi activityApi;

    @Autowired
    EstimateActivityApi estimateActivityApi;

    public void run() throws Exception{

        //todo change to id
        String locationId="New York City, New York";
        final List<Activity> activities;
        try {
            List<Activity> tempActivities = activityApi.getActivitiesByLocation(locationId);

            activities=tempActivities.stream().filter(item -> item.getAddress()!=null && !item.getAddress().isEmpty()).collect(Collectors.toList());
        }
        catch (Exception e){
            throw e;
        }
        activities.forEach(item ->{
            String[] destinations= getOtherActivitiesAddress(activities,item);
            ActivityEstimateTimeDistance activityEstimateTime = new ActivityEstimateTimeDistance();

            String[] origin=new String[]{item.getAddress()};

            CountDownLatch latch = new CountDownLatch(1);
            gogleApiHelper.setLatch(latch);
            gogleApiHelper.estimateTimeArrival(origin,destinations,TravelMode.DRIVING,activityEstimateTime,activities);


           try {
               latch.await();

               activityEstimateTime.setActivityId(item.getActivityId());
               estimateActivityApi.addEstimateActivities(activityEstimateTime);
           }
           catch (Exception e){
               LogManager.getRootLogger().error("error  " +e);
               System.out.println(e.getMessage());
           }



        });

    }


    private String[] getOtherActivitiesAddress(List<Activity> activities,Activity activity){
        List<String>  destinations=activities.stream().
                filter(item -> item.getAddress()!=activity.getAddress())
                .map(item-> item.getAddress())
                .collect(Collectors.toList());

        String[] destinationsArray = new String[destinations.size()];
        destinations.toArray(destinationsArray);
        return destinationsArray;
    }
}
