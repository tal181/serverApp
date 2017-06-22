package com.myapp.domain.location;

import com.myapp.domain.category.Category;

/**
 * Created by Tal on 16/04/2017.
 */
public class LocationCategory extends Category {

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    private String locationId;
    private int defaultDuration;

    public LocationCategory() {
        super("",0.0);
    }

    public LocationCategory(String locationId, String category, double value) {
        super(category,value);
        this.locationId=locationId;
        this.defaultDuration = 180;
    }


    public String getLocationId() {
        return locationId;
    }

    public int getDefaultDuration() {
        return defaultDuration;
    }

    public void setDefaultDuration(int defaultDuration) {
        this.defaultDuration = defaultDuration;
    }


}
