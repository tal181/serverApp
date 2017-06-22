package com.myapp.rest;

import com.myapp.api.location.CountriesApi;
import com.myapp.api.location.LocationApi;
import com.myapp.domain.country.Country;
import com.myapp.domain.location.Location;
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
@Path("/city")
public class CityService {

    @Autowired
    LocationApi locationApi;

    @GET
    @Path("/{countryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCitiesByCountryId(@PathParam("countryId") String countryId) {
        try {
            List<Location> locations=locationApi.getAllLocationByCountyId(countryId);
            return Response
                    .ok(locations)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }


}
