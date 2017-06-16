package com.myapp.DB.changeSet;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

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


    }


}