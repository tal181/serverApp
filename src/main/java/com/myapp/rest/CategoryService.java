package com.myapp.rest;

import com.myapp.api.category.CategoryApi;
import com.myapp.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
@Path("/categories")
public class CategoryService {

    @Autowired
    CategoryApi categoriesApi;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        try {
            List<Category>  categories=categoriesApi.getCategories();
            return Response
                    .ok(categories)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewCategories(ArrayList<Category> categories) {
        try {
            categoriesApi.addNewCategories(categories);
            return Response
                    .ok("")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("error").build();
        }


    }
}
