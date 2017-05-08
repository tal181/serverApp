package com.myapp.api.user;

import com.myapp.domain.user.UserCategory;
import com.myapp.domain.user.UserDetails;

import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
public interface UserApi {

    public UserDetails getUserDetails(String email) throws Exception;

    void setUserCategories(String email, List<UserCategory> userCategories);

    public void deleteUserCategories(String loginName, String tableName);

    List<UserCategory> getUserCategories(String loginName) throws Exception;

    void createNewUser(UserDetails user);

}
