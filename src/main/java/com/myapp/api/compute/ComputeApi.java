package com.myapp.api.compute;

import com.myapp.domain.category.Category;
import com.myapp.domain.location.LocationCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 24/04/2017.
 */
public interface ComputeApi {
    List<LocationCategory> computeUserLocationsCategories(String loginName) throws Exception;
}
