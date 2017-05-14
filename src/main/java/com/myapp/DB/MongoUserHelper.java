package com.myapp.DB;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.myapp.domain.user.UserCategory;
import com.myapp.domain.user.UserDetails;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class MongoUserHelper {

    @Autowired
    MongoTemplate mongoTemplate;

    public void save(String tableName,String jsonObj){
        DBCollection table = mongoTemplate.getCollection(tableName);

        // convert JSON to DBObject directly
        List<DBObject> dbObject = (List<DBObject>) JSON
                .parse(jsonObj);

        table.insert(dbObject);

    }
    public UserDetails find(String tableName, String query) throws Exception{
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        if(query!=null) {
            searchQuery.put("loginName", query);
        }

        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            UserDetails userDetails = new UserDetails();
            DBObject dbobj = cursor.next();
            ObjectId id = (ObjectId)dbobj.get( "_id" );
            userDetails.setId(id);

            return userDetails;
        }
        throw new NotFoundException("User wasn't found");
    }

    public List<UserCategory> findCategories(String tableName, String loginName) {
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        if(loginName!=null) {
            searchQuery.put("loginName", loginName);
        }

        DBCursor cursor = table.find(searchQuery);
        List<UserCategory> categories = new ArrayList<>();
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            UserCategory userCategory = gson.fromJson(dBObject.toString(),
                    UserCategory.class);

            ObjectId id = (ObjectId)dBObject.get( "_id" );
            userCategory.setId(id);

            categories.add(userCategory);

        }
        return categories;
    }

    public void delete(String tableName, String loginName) {
        DBCollection table = mongoTemplate.getCollection(tableName);
        BasicDBObject query = new BasicDBObject();
        query.append("loginName", loginName);

        table.remove(query);
    }
}
