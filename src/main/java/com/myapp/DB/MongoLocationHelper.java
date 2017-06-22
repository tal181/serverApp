package com.myapp.DB;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.location.Location;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tal on 16/06/2017.
 */
@Component
public class MongoLocationHelper {

    @Autowired
    MongoTemplate mongoTemplate;

    public Location findById(String tableName, String locationId) throws Exception{
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        if(locationId!=null) {
            searchQuery.put("_id", new ObjectId(locationId));
        }

        DBCursor cursor = table.find(searchQuery);
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            Location location = gson.fromJson(dBObject.toString(),
                    Location.class);

            ObjectId id = (ObjectId) dBObject.get( "_id" );
            location.setLocationId(id.toString());

            return location;

        }
        throw  new Exception("locationId " + locationId +" not found ");
    }

    public void save(String tableName,String jsonObj){
        DBCollection table = mongoTemplate.getCollection(tableName);
        // convert JSON to DBObject directly
        List<DBObject> dbObject = (List<DBObject>) JSON
                .parse(jsonObj);

        table.insert(dbObject);

    }

    public List<Location> findByCountyId(String locationsTable, String countyId) {
        DBCollection table = mongoTemplate.getCollection(locationsTable);

        BasicDBObject searchQuery = new BasicDBObject();
        if(countyId!=null) {
            searchQuery.put("countryId", countyId);
        }

        DBCursor cursor = table.find(searchQuery);
        Gson gson = new Gson();
        List<Location> locations = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            Location location = gson.fromJson(dBObject.toString(),
                    Location.class);

            ObjectId id = (ObjectId) dBObject.get( "_id" );
            location.setLocationId(id.toString());

            locations.add(location);

        }

        return locations;
    }

    public Location findByName(String locationsTable, String locationName) throws Exception{
        DBCollection table = mongoTemplate.getCollection(locationsTable);

        BasicDBObject searchQuery = new BasicDBObject();
        if(locationName!=null) {
            searchQuery.put("locationName", locationName);
        }

        DBCursor cursor = table.find(searchQuery);
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            Location location = gson.fromJson(dBObject.toString(),
                    Location.class);

            ObjectId id = (ObjectId) dBObject.get( "_id" );
            location.setLocationId(id.toString());

            return location;

        }
        throw  new Exception("locationName " + locationName +" not found ");
    }
}
