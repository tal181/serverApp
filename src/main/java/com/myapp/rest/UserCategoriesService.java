package com.myapp.rest;

import com.myapp.api.user.UserApi;
import com.myapp.domain.user.UserCategory;
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
@Path("/userCategories")
public class UserCategoriesService {

    @Autowired
    UserApi userApi;

    @POST
    @Path("/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setUserCategories(@PathParam("loginName") String loginName,
                                      List<UserCategory> userCategories) {
        try {
            userApi.setUserCategories(loginName,userCategories);
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
    @Path("/{loginName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCategories(@PathParam("loginName") String loginName) {
        try {
            List<UserCategory> userCategories= userApi.getUserCategories(loginName);
            return Response
                    .ok(userCategories)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }
}
