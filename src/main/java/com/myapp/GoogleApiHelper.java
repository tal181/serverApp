package com.myapp;
import com.google.gson.*;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class GoogleApiHelper{
    private GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDH0fd-GkKJ1SdprUgNbacvN_4fEz-HnUM");
    final static CountDownLatch latch = new CountDownLatch(1);

    public  void estimateTimeArrival(String[] origin,String[] arrival,TravelMode mode
            ,GoogleApiResponse googleApiResponse){
        DistanceMatrixApiRequest req = DistanceMatrixApi.getDistanceMatrix(context,
                origin,
                arrival);

        req.mode(mode);

        req.setCallback(new PendingResult.Callback<DistanceMatrix>() {
            @Override
            public void onResult(DistanceMatrix result) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println(gson.toJson(result));

                googleApiResponse.setDuration(result.rows[0].elements[0].duration.humanReadable);
                googleApiResponse.setDistance(result.rows[0].elements[0].distance.humanReadable);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable e) {
                System.out.println("Exception thrown: "+e);
                latch.countDown();
            }
        });
    }
    public static void main(String[] args) throws InterruptedException {
        String[] origin =new String[]{"1025 Boardwalk W, New York City, NY"};
        String[] arrival= new String[]{"Avenue of the Americas & W 59th St, Wollman Rink, New York City, NY 10019"};
        GoogleApiHelper g= new GoogleApiHelper();
        GoogleApiResponse googleApiResponse= new GoogleApiResponse();
        g.estimateTimeArrival(origin,arrival,TravelMode.WALKING,googleApiResponse);

        // We have to hold the main thread open until callback is called by OkHTTP.
        latch.await();

        System.out.println(googleApiResponse.getDistance());
    }
}