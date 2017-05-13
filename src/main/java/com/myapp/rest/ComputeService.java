package com.myapp.rest;

import com.myapp.api.compute.ComputeApi;
import com.myapp.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 24/04/2017.
 */
@Component
@Path("/compute")
public class ComputeService {
    @Autowired
    ComputeApi computeApi;
    @GET
    @Path("/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response compute(@PathParam("loginName") String loginName,
                            @DefaultValue("3") @QueryParam("numberOfActivities") Integer numberOfActivities) {
        try {
            List<Category> computes =computeApi.computeUserLocationsCategories(loginName);
            int minSize=Math.min(numberOfActivities,computes.size());
            return Response
                    .ok(computes.subList(0, minSize))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }

}
