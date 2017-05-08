package com.myapp.api.location;

import com.google.gson.Gson;
import com.myapp.DB.MongoCountriesHelper;
import com.myapp.config.TablesScheme;
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
public class CountriesApiImpl implements CountriesApi{
    @Autowired
    MongoCountriesHelper mongoCountriesHelper;



    public   Map<String,List<String>> getCountries() throws Exception{
        Map<String,List<String>> countries =mongoCountriesHelper.find(TablesScheme.COUNTRIES_TABLE);

        return countries;
    }

    @Override
    public void addNewCountries(HashMap<String, ArrayList<String>> countries) {
        Gson gson = new Gson();
        String countriesJson = gson.toJson(countries);
        mongoCountriesHelper.save(TablesScheme.COUNTRIES_TABLE,countriesJson);
    }

}

