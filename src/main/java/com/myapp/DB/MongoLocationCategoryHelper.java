package com.myapp.DB;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.myapp.domain.category.Category;
import com.myapp.domain.location.LocationCategory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class MongoLocationCategoryHelper {

    @Autowired
    MongoTemplate mongoTemplate;

    public  List<LocationCategory> find(String tableName,String categoryName, String location) throws Exception{
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        if(location!=null) {
            searchQuery.put("location", location);
        }
        if(categoryName!=null) {
            searchQuery.put("categoryName", categoryName);
        }


        DBCursor cursor = table.find(searchQuery);
        List<LocationCategory> categories = new ArrayList<>();
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            LocationCategory locationCategory = gson.fromJson(dBObject.toString(),
                    LocationCategory.class);

            ObjectId id = (ObjectId) dBObject.get( "_id" );
            locationCategory.setId(id.toString());

            categories.add(locationCategory);

        }
        return categories;
    }

    public void save(String tableName,String jsonObj){
        DBCollection table = mongoTemplate.getCollection(tableName);
        // convert JSON to DBObject directly
        List<DBObject> dbObject = (List<DBObject>) JSON
                .parse(jsonObj);

        table.insert(dbObject);

    }
    public void update(String tableName, Category key) {
        DBCollection table =mongoTemplate.getCollection(tableName);

        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set",
                new BasicDBObject().append("rating", key.getRating()));

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append("categoryName", key.getCategoryName());

        table.update(searchQuery, updateQuery);
    }
}
