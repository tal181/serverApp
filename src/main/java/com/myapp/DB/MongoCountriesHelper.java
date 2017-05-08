package com.myapp.DB;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class MongoCountriesHelper {

    @Autowired
    MongoTemplate mongoTemplate;

    public  Map<String,List<String>>  find(String tableName) throws Exception{
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        DBCursor cursor = table.find(searchQuery);
        Map<String,List<String>> countries = new HashMap<>();

        while (cursor.hasNext()) {
            DBObject dbobj = cursor.next();
            dbobj.removeField("_id");

            String key=dbobj.keySet().iterator().next();
            countries.put(key,(ArrayList)dbobj.get(key));
            System.out.print("");
        }
        return countries;
    }
    public void save(String tableName,String jsonObj){
        DBCollection table = mongoTemplate.getCollection(tableName);

        DBObject dbObject = (DBObject)JSON.parse(jsonObj);
        for (String entry : dbObject.keySet())
        {
            HashMap map= new HashMap();
            map.put(entry,dbObject.get(entry));

            Gson gson = new Gson();
            String countryJson = gson.toJson(map);
            DBObject dbObjectMap = (DBObject)JSON.parse(countryJson);
            table.insert(dbObjectMap);
        }


    }


}
