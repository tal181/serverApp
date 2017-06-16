package com.myapp.api.location;

import com.myapp.domain.location.Location;

import java.util.List;

/**
 * Created by Tal on 16/06/2017.
 */
public interface LocationApi {

     void addLocation(Location location) throws Exception;

     Location getLocation(String locationId) throws Exception;

    Location getLocationByName(String locationName) throws Exception;

    List<Location> getAllLocationByCountyId(String countyId) throws Exception;
}
