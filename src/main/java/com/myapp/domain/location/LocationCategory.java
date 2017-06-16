package com.myapp.domain.location;

import com.myapp.domain.category.Category;

/**
 * Created by Tal on 16/04/2017.
 */
public class LocationCategory extends Category {

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

    public String geLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }


}
