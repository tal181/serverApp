package com.myapp.api.location;

import com.myapp.domain.country.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 16/04/2017.
 */
public interface CountriesApi {
    List<Country> getCountries() throws Exception;

    void addNewCountries( List<Country>  countries);

}
