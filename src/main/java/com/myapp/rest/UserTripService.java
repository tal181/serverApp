package com.myapp.rest;


import com.myapp.api.activity.ActivityApi;
import com.myapp.api.userTrip.UserTripApi;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.user.UserTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by Tal.
 * <p>
 */
@Component
@Path("/tripPlan")
public class UserTripService {
    @Autowired
    UserTripApi userTripApi;
    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUserPlan(UserTrip userTrip) {
        try {
            userTripApi.saveUserTrip(userTrip);
            return Response
                    .ok("")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }

//    @GET
//    @Path("{tripId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getUserPlan(@PathParam("tripId") Integer tripId) {
//        try {
//            UserTrip userTrip=userTripApi.getUserTrip(tripId);
//            return Response
//                    .ok(userTrip)
//                    .type(MediaType.APPLICATION_JSON)
//                    .build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
//                    entity("error").build();
//        }
//
//
//    }

    @GET
    @Path("{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPlan(@PathParam("loginName") String loginName) {
        try {
            List<UserTrip> userTrips=userTripApi.getUserTripByLoginNme(loginName);
            return Response
                    .ok(userTrips)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }


}

