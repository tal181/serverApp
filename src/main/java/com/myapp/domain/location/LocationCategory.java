package com.myapp.domain.location;

import com.myapp.domain.category.Category;

/**
 * Created by Tal on 16/04/2017.
 */
public class LocationCategory extends Category {

    private String location;
    private int defaultDuration;

    public LocationCategory() {
        super("",0.0);
    }

    public LocationCategory(String location, String category, double value) {
        super(category,value);
        this.location=location;
        this.defaultDuration = 180;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
