package com.myapp.rest.TA;

import com.myapp.api.tripAdvisor.TAApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//todo make it sigelton
@Component
//@org.springframework.context.annotation.Scope("prototype")
@Path("/fetchTaData")
public class TAService {
    @Autowired
    TAApi taApi;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        try {
            taApi.getData();

            return Response
                    .ok()
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }
    }


}
