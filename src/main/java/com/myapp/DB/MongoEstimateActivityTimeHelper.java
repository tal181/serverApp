package com.myapp.DB;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.activity.ActivityEstimateTimeDistance;
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
public class MongoEstimateActivityTimeHelper {

    @Autowired
    MongoTemplate mongoTemplate;


    public  ActivityEstimateTimeDistance find(String tableName, String activityId) throws Exception{
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        if(activityId!=null) {
            searchQuery.put("activityId", activityId);
        }

        DBCursor cursor = table.find(searchQuery);
        ActivityEstimateTimeDistance activityEstimateTimeDistance =null;
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            activityEstimateTimeDistance = gson.fromJson(dBObject.toString(),
                    ActivityEstimateTimeDistance.class);

            return activityEstimateTimeDistance;
//            ObjectId id = (ObjectId) dBObject.get( "_id" );
//            activity.setActivityId(id.toString());
//
//            activities.add(activity);

        }
        return activityEstimateTimeDistance;
    }

    public void save(String tableName,String jsonObj){
        DBCollection table = mongoTemplate.getCollection(tableName);
        // convert JSON to DBObject directly
        DBObject dbObject = (DBObject) JSON
                .parse(jsonObj);

        table.insert(dbObject);

    }
//    public  void remove(String tableName)  {
//
//        DBCollection table =mongoTemplate.getCollection(tableName);
//
//        table.remove(new BasicDBObject());
//
//
//    }

}
