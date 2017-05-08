package com.myapp.rest;

import com.myapp.api.locationCategory.LocationCategoryApi;
import com.myapp.domain.location.LocationCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
@Path("/locationCategories")
public class LocationCategoriesService {

    @Autowired
    LocationCategoryApi locationCategoryApi;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLocationCategories(
                                      List<LocationCategory> locationCategory) {
        try {
            locationCategoryApi.addLocationCategories(locationCategory);
            return Response
                    .ok("")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }
    }

    @GET
    @Path("/{location}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoriesByLocation(@PathParam("location") String location) {
        try {
            List<LocationCategory> locationCategories= locationCategoryApi.getCategoriesByLocation(null,location);
            return Response
                    .ok(locationCategories)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }
}
