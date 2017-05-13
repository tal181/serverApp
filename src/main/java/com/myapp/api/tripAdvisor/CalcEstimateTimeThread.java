package com.myapp.api.tripAdvisor;

import com.google.maps.model.TravelMode;
import com.myapp.GoogleApiHelper;
import com.myapp.GoogleApiResponse;
import com.myapp.api.activity.ActivityApi;
import com.myapp.api.locationCategory.LocationCategoryApi;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.location.LocationCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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


    public void run(){
        String location="New York City, New York";
        List<Activity> activities=null;
        try {
            activities = activityApi.getActivitiesByLocation(location);

            activities=activities.stream().filter(item -> item.getAddress()!=null).collect(Collectors.toList());
        }
        catch (Exception e){

        }
        //activities.forEach(item ->{
            Activity item=activities.get(0);
           String[] destinations= getOtherActivitiesAddress(activities,item);
            GoogleApiResponse googleApiResponse = new GoogleApiResponse();
            String[] origin=new String[]{item.getAddress()};
            gogleApiHelper.estimateTimeArrival(origin,destinations,TravelMode.DRIVING,googleApiResponse);

            System.out.println("item is " +item +" googleApiResponse" );
        //});

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
