package com.myapp.api.compute;

import com.myapp.domain.category.Category;

import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 24/04/2017.
 */
public interface ComputeApi {
    List<Category> computeUserLocationsCategories(String loginName) throws Exception;
}
