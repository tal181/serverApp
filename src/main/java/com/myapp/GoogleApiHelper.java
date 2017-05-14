package com.myapp;
import com.google.gson.*;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.TravelMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            ,List<GoogleApiResponse> googleApiResponses){
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
                    GoogleApiResponse googleApiResponse= new GoogleApiResponse();
                    if(result.rows[0].elements[index].status.name().equals("OK")){
                        Long duration=elements[index].duration.inSeconds/60;
                        googleApiResponse.setDuration(duration.intValue());
                        googleApiResponse.setDistance(result.rows[0].elements[0].distance.humanReadable);
                        googleApiResponses.add(googleApiResponse);
                    }
                    else{
                        //failed to get info
                        googleApiResponses.add(googleApiResponse);

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