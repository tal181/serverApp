package com.myapp.api.location;

import com.google.gson.Gson;
import com.myapp.DB.MongoCountriesHelper;
import com.myapp.config.TablesScheme;
import com.myapp.domain.country.Country;
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



    public   List<Country>  getCountries() throws Exception{
        List<Country>  countries =mongoCountriesHelper.find(TablesScheme.COUNTRIES_TABLE);

        return countries;
    }

    @Override
    public void addNewCountries( List<Country> countries) {
        Gson gson = new Gson();
        String countriesJson = gson.toJson(countries);
        mongoCountriesHelper.save(TablesScheme.COUNTRIES_TABLE,countriesJson);
    }

}

