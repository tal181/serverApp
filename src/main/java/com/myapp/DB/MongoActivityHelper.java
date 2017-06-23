package com.myapp.DB;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.user.UserCategory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tal on 16/04/2017.
 */
@Component
public class MongoActivityHelper {

    @Autowired
    MongoTemplate mongoTemplate;

    public  Activity findById(String tableName, String activityId) throws Exception{
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        if(activityId!=null) {
            searchQuery.put("_id", new ObjectId(activityId));
        }

        DBCursor cursor = table.find(searchQuery);
        Activity activities = new Activity();
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            Activity activity = gson.fromJson(dBObject.toString(),
                    Activity.class);

            ObjectId id = (ObjectId) dBObject.get( "_id" );
            activity.setActivityId(id.toString());

          return activity;

        }
        throw  new Exception("activityId " + activityId +" not found ");
    }
    public  List<Activity> findByLocation(String tableName, String locationId) throws Exception{
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        if(locationId!=null) {
            searchQuery.put("locationId", locationId);
        }

        DBCursor cursor = table.find(searchQuery);
        List<Activity> activities = new ArrayList<>();
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            Activity activity = gson.fromJson(dBObject.toString(),
                    Activity.class);

            ObjectId id = (ObjectId) dBObject.get( "_id" );
            activity.setActivityId(id.toString());

            activities.add(activity);

        }
        return activities;
    }

    public void save(String tableName,String jsonObj){
        DBCollection table = mongoTemplate.getCollection(tableName);
        // convert JSON to DBObject directly
        List<DBObject> dbObject = (List<DBObject>) JSON
                .parse(jsonObj);

        table.insert(dbObject);

    }
    public  void remove(String tableName)  {

        DBCollection table =mongoTemplate.getCollection(tableName);

        table.remove(new BasicDBObject());


    }

    public List<Activity> getBestActivitiesByLocation(String tableName, String locationId,int countResults) {
        DBCollection table = mongoTemplate.getCollection(tableName);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("locationId", locationId);


        DBCursor cursor = table.find(searchQuery);
        cursor.sort(new BasicDBObject("rating", -1)).limit(countResults);

        List<Activity> activities = new ArrayList<>();
        Gson gson = new Gson();

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            Activity activity = gson.fromJson(dBObject.toString(),
                    Activity.class);
            activities.add(activity);

        }
        return activities;
    }

    public List<Activity> getActivitiesByLocationAndLoginName(String activitiesTable, List<UserCategory> cats) {
        DBCollection table = mongoTemplate.getCollection(activitiesTable);

        BasicDBObject inQuery = new BasicDBObject();
        Gson gson = new Gson();
//        list.add(2);
//        list.add(4);
//        list.add(5);
        List<Activity> activities = new ArrayList<>();
        List<String> activitiesIds=cats.stream().map(UserCategory::getId).collect(Collectors.toList());

        inQuery.put("categoryId", new BasicDBObject("$in", activitiesIds));
        DBCursor cursor = table.find(inQuery);

        while (cursor.hasNext()) {
            DBObject dBObject = cursor.next();
            Activity activity = gson.fromJson(dBObject.toString(),
                    Activity.class);

            ObjectId id = (ObjectId) dBObject.get( "_id" );
            activity.setActivityId(id.toString());

            activities.add(activity);

        }
        return activities;
    }
}
