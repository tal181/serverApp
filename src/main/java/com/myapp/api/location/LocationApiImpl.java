package com.myapp.api.location;

import com.google.gson.Gson;
import com.myapp.DB.MongoLocationHelper;
import com.myapp.config.TablesScheme;
import com.myapp.domain.location.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Tal on 16/06/2017.
 */
@Component
public class LocationApiImpl implements LocationApi{

    @Autowired
    MongoLocationHelper mongoLocationHelper;

    @Override
    public void addLocation(Location location) throws Exception {

        Gson gson = new Gson();
        String locationDetails = gson.toJson(location);

        mongoLocationHelper.save(TablesScheme.LOCATIONS_TABLE,locationDetails);
    }

    @Override
    public Location getLocation(String locationId) throws Exception {
        return mongoLocationHelper.findById(TablesScheme.LOCATIONS_TABLE,locationId);
    }

    @Override
    public Location getLocationByName(String locationName) throws Exception {
        return mongoLocationHelper.findByName(TablesScheme.LOCATIONS_TABLE,locationName);
    }

    @Override
    public List<Location> getAllLocationByCountyId(String countyId) throws Exception {
        return mongoLocationHelper.findByCountyId(TablesScheme.LOCATIONS_TABLE,countyId);
    }

}
