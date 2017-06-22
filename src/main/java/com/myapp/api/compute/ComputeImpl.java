package com.myapp.api.compute;

import com.myapp.api.location.CountriesApi;
import com.myapp.api.location.LocationApi;
import com.myapp.api.locationCategory.LocationCategoryApi;
import com.myapp.api.user.UserApi;
import com.myapp.domain.category.Category;
import com.myapp.domain.country.Country;
import com.myapp.domain.location.Location;
import com.myapp.domain.location.LocationCategory;
import com.myapp.domain.user.UserCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Tal on 24/04/2017.
 */
@Component
public class ComputeImpl implements ComputeApi {

    @Autowired
    CountriesApi countriesApi;

    @Autowired
    LocationApi locationApi;

    @Autowired
    UserApi userApi;

    @Autowired
    LocationCategoryApi locationCategoryApi;
    @Override
    public List<LocationCategory> computeUserLocationsCategories(String loginName) throws Exception{
        List<UserCategory> userCategories=userApi.getUserCategories(loginName);

       List<Country> countries= countriesApi.getCountries();

        List<LocationCategory> resalts= new ArrayList<>();

        for (Country country:countries) {
            List<Location> locations = locationApi.getAllLocationByCountyId(country.getCountryId());
            for (Location location : locations) {
                List<LocationCategory> locationCategories = locationCategoryApi.getCategoriesByLocation
                        (null, location.getLocationId());
                double result = computeUserLocationRating(userCategories, locationCategories);

                LocationCategory locationCategory = new LocationCategory();
                locationCategory.setLocationId(location.getLocationId());
                locationCategory.setRating(result);
                resalts.add(locationCategory);
            }
        }
        //sort
        Collections.sort(resalts, (a, b) -> b.getRating().compareTo(a.getRating()));
        return resalts;
    }

    private double computeUserLocationRating(List<UserCategory> userCategories,
                                             List<LocationCategory> locationCategories) {

        double sum=0;
        int common=0;
        for(UserCategory userCategory: userCategories){
            for(LocationCategory locationCategory: locationCategories){
                if(userCategory.getCategoryName().equals(locationCategory.getCategoryName())){
                    sum+=locationCategory.getRating();
                    common++;
                }
            }
        }
        if(common==0){
            return common;
        }
        double result=sum/common;
        return result;
    }
}
