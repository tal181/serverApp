package com.myapp.api.category;

import com.google.gson.Gson;
import com.myapp.DB.MongoCategoryHelper;
import com.myapp.api.location.CountriesApi;
import com.myapp.config.TablesScheme;
import com.myapp.domain.category.Category;
import com.myapp.domain.location.LocationCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class CategoryApiImpl implements CategoryApi {

    @Autowired
    MongoCategoryHelper mongoCategoryHelper;

    @Autowired
    CountriesApi countriesApi;
    public  List<Category> getCategories() throws Exception{

        List<Category> categories=mongoCategoryHelper.find(TablesScheme.CATEGORY_TABLE);

        return categories;
    }

    @Override
    public Map<String, Category> getCategoriesMap() throws Exception {
        List<Category> allCategories= getCategories();

        Map<String, Category> allCategoriesMap= new HashMap<>();
        allCategories.forEach(category->{
            allCategoriesMap.put(category.getCategoryName(),category);
        });
        return allCategoriesMap;
    }

    public  List<LocationCategory> getCategoriesByLocation(String location) throws Exception{
        List<Category> categories= getCategories();

        List<LocationCategory> categoriesByLocation= new ArrayList<>();

        categories.forEach(category -> {
            LocationCategory locationCategory = new LocationCategory(location,
                    category.getCategoryName(),2.0);
            categoriesByLocation.add(locationCategory);
        });

        return categoriesByLocation;

    }

    @Override
    public void addNewCategories(List<Category> categories) {

        Gson gson = new Gson();
        String categoriesDetails = gson.toJson(categories);

        mongoCategoryHelper.save(TablesScheme.CATEGORY_TABLE,categoriesDetails);
    }

    @Override
    public void deleteAllRecords() {
        mongoCategoryHelper.remove(TablesScheme.CATEGORY_TABLE);
    }



}
