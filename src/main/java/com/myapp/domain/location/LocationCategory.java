package com.myapp.domain.location;

import com.myapp.domain.category.Category;

/**
 * Created by Tal on 16/04/2017.
 */
public class LocationCategory extends Category {

    private String location;

    public LocationCategory() {
        super("",0.0);
    }

    public LocationCategory(String location, String category, double value) {
        super(category,value);
        this.location=location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
