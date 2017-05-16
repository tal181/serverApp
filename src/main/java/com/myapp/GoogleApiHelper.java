package com.myapp;
import com.google.gson.*;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.TravelMode;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.activity.ActivityEstimateTimeDistance;
import com.myapp.domain.activity.EstimateTimeDistance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class GoogleApiHelper{
    public CountDownLatch latch;
    GoogleApiHelper(){

    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    private GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDH0fd-GkKJ1SdprUgNbacvN_4fEz-HnUM");


    public  void estimateTimeArrival(String[] origin, String[] arrival, TravelMode mode
            , ActivityEstimateTimeDistance activityEstimateTimeDistance, List<Activity> activities){
        DistanceMatrixApiRequest req = DistanceMatrixApi.getDistanceMatrix(context,
                origin,
                arrival);

        req.mode(mode);

        req.setCallback(new PendingResult.Callback<DistanceMatrix>() {
            @Override
            public void onResult(DistanceMatrix result) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println(gson.toJson(result));
                DistanceMatrixElement[] elements=result.rows[0].elements;
                for(int index=0;index<elements.length;index++){

                    EstimateTimeDistance estimateTimeDistance= new EstimateTimeDistance();
                    String activityId=activities.get(index).getActivityId();
                    estimateTimeDistance.setActivityId(activityId);
                    if(result.rows[0].elements[index].status.name().equals("OK")){
                        Long duration=elements[index].duration.inSeconds/60;
                        estimateTimeDistance.setDuration(duration.intValue());
                        estimateTimeDistance.setDistance(result.rows[0].elements[0].distance.humanReadable);
                        activityEstimateTimeDistance.getActivities().add(estimateTimeDistance);
                    }
                    else{
                        //failed to get info
                        activityEstimateTimeDistance.getActivities().add(estimateTimeDistance);

                    }
                }


                latch.countDown();
            }

            @Override
            public void onFailure(Throwable e) {
                System.out.println("Exception thrown: "+e);
                latch.countDown();
            }
        });
    }

}