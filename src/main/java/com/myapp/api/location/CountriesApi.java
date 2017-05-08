package com.myapp.api.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 16/04/2017.
 */
public interface CountriesApi {
    public Map<String,List<String>> getCountries() throws Exception;

    void addNewCountries(HashMap<String, ArrayList<String>> countries);

}
