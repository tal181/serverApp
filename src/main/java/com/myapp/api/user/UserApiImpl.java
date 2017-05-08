package com.myapp.api.user;

import com.google.gson.Gson;
import com.myapp.DB.MongoUserHelper;
import com.myapp.api.category.CategoryApi;
import com.myapp.api.location.CountriesApi;
import com.myapp.api.locationCategory.LocationCategoryApi;
import com.myapp.config.TablesScheme;
import com.myapp.domain.user.UserCategory;
import com.myapp.domain.user.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class UserApiImpl implements UserApi {
    @Autowired
    CountriesApi countriesApi;

    @Autowired
    CategoryApi categoriesApi;

    @Autowired
    MongoUserHelper mongoUserHelper;

    @Autowired
    LocationCategoryApi locationCategoryApi;

    @Override
    public UserDetails getUserDetails(String loginName) throws Exception {

        Gson gson = new Gson();
        String userJson=mongoUserHelper.find(TablesScheme.USERS_TABLE,loginName);
        UserDetails user = gson.fromJson(userJson, UserDetails.class);
        return user;
    }

    @Override
    public void createNewUser(UserDetails user) {
        Gson gson = new Gson();
        String userDetailsJson = gson.toJson(user);
        mongoUserHelper.save(TablesScheme.USERS_TABLE,userDetailsJson);
    }

    @Override
    public void deleteUserCategories(String loginName,String tableName) {

        mongoUserHelper.delete(tableName,
                loginName);
    }

    @Override
    public void setUserCategories(String loginName, List<UserCategory> userCategories) {
        deleteUserCategories(loginName, TablesScheme.USER_CATEGORY_RATING_TABLE);

        Gson gson = new Gson();
        String userCategoriesJson = gson.toJson(userCategories);

        mongoUserHelper.save(TablesScheme.USER_CATEGORY_RATING_TABLE,
                userCategoriesJson);
    }

    @Override
    public  List<UserCategory> getUserCategories(String loginName) throws Exception{
        List<UserCategory> categories=mongoUserHelper.
                findCategories(TablesScheme.USER_CATEGORY_RATING_TABLE,loginName);

        return categories;

    }


}
