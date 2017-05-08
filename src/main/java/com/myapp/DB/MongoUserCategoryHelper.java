package com.myapp.DB;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.myapp.domain.user.UserCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class MongoUserCategoryHelper {

    @Autowired
    MongoTemplate mongoTemplate;

    public  List<UserCategory> find(String tableName, String loginName) throws Exception{
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
            categories.add(userCategory);

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
}
