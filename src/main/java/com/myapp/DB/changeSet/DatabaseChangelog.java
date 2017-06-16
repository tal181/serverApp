package com.myapp.DB.changeSet;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.bson.types.ObjectId;

/**
 * Created by Tal on 16/06/2017.
 */

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "createAllTablesInit", author = "tal")
    public void createTables(DB db){

        BasicDBObject arg = new BasicDBObject();
        DBCollection activitiesEstimateTime=db.createCollection("activitiesEstimateTime",arg);

        DBCollection activities=db.createCollection("activities",arg);

        DBCollection categories=db.createCollection("categories",arg);

        DBCollection activitiesMapping=db.createCollection("activitiesMapping",arg);

        DBCollection countries=db.createCollection("countries",arg);

        DBCollection locationCategoryRating=db.createCollection("locationCategoryRating",arg);

        DBCollection userCategoryRating=db.createCollection("userCategoryRating",arg);

        DBCollection users=db.createCollection("users",arg);

        BasicDBObject document = new BasicDBObject();
        document.put("loginName", "admin");
        document.put("password", "admin1");
        users.insert(document);
    }

    @ChangeSet(order = "002", id = "addLocationTable", author = "tal")
    public void addLocationTable(DB db){
        DBCollection locations=db.getCollection("locations");
        DBCollection countries=db.getCollection("countries");


        //country
        BasicDBObject document = new BasicDBObject();
        document.put("countryName", "USA");
        countries.insert(document);
        ObjectId usaId = document.getObjectId("_id");

        document = new BasicDBObject();
        document.put("countryName", "Israel");
        countries.insert(document);
        ObjectId israelId = document.getObjectId("_id");

        //locations
        document = new BasicDBObject();
        document.put("locationName", "New York City, New York");
        document.put("countryId", usaId.toString());
        locations.insert(document);

        document = new BasicDBObject();
        document.put("locationName", "LA");
        document.put("countryId", usaId.toString());
        locations.insert(document);


        document = new BasicDBObject();
        document.put("locationName", "Tel Aviv");
        document.put("countryId", israelId.toString());
        locations.insert(document);

        document = new BasicDBObject();
        document.put("locationName", "Tel Aviv");
        document.put("countryId", israelId.toString());
        locations.insert(document);

    }


}