package com.myapp.api.category;

import com.myapp.domain.category.Category;

import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 16/04/2017.
 */
public interface CategoryApi {
    public List<Category> getCategories() throws Exception;

    public Map<String,Category> getCategoriesMap() throws Exception;

    void addNewCategories(List<Category> categories);

    void deleteAllRecords();

}
