package com.myapp.rest;

import com.myapp.api.user.UserApi;
import com.myapp.domain.user.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
@Path("/user")
public class UserService {

    @Autowired
    UserApi userApi;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewUser(UserDetails user) {
        try {
           userApi.createNewUser(user);
            return Response
                    .ok("")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(e.getMessage()).build();
        }


    }

    @GET
    @Path("/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserByEmail(@PathParam("loginName") String loginName) {
        try {
            UserDetails userDetails =userApi.getUserDetails(loginName);
            return Response
                    .ok(userDetails)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }

}
