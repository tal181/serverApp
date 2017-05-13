package com.myapp.api.userTrip;

import com.myapp.domain.user.UserCategory;
import com.myapp.domain.user.UserDetails;
import com.myapp.domain.user.UserTrip;

import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
public interface UserTripApi {

    public List<UserTrip>getUserTripByLoginNme(String loginName) throws Exception;

    public UserTrip getUserTrip(Integer tripId) throws Exception;

    public void saveUserTrip(UserTrip userTrip) throws Exception;
}
