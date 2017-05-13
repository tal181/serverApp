package com.myapp.api.userTrip;

import com.google.gson.Gson;
import com.myapp.DB.MongoUserHelper;
import com.myapp.api.category.CategoryApi;
import com.myapp.api.location.CountriesApi;
import com.myapp.api.locationCategory.LocationCategoryApi;
import com.myapp.config.TablesScheme;
import com.myapp.domain.user.UserCategory;
import com.myapp.domain.user.UserDetails;
import com.myapp.domain.user.UserTrip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class UserTripImpl implements UserTripApi {

    @Override
    public UserTrip getUserTrip(Integer tripId) throws Exception {
        return null;
    }

    @Override
    public void saveUserTrip(UserTrip userTrip) throws Exception {

    }
    @Override
    public List<UserTrip> getUserTripByLoginNme(String loginName) throws Exception{
        List<UserTrip> userTrips=null;
        return userTrips;
    }
}
