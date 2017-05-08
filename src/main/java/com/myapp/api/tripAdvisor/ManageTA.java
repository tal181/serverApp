package com.myapp.api.tripAdvisor;

import com.myapp.domain.activity.Activity;
import com.myapp.domain.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Tal on 27/04/2017.
 */
@Component
@Scope("prototype")
public class ManageTA implements Runnable {
    @Autowired
    TAInsertDBThread taDBThread;

    @Autowired
    TAFetchDataThread taFetchDataThread;

    String location;

    public ManageTA(){

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void run(){
        //get TA data
        ExecutorService pool = Executors.newFixedThreadPool(3);
        taFetchDataThread.setLocation(location);

        Future<HashMap<Category,List<Activity> >> future = pool.submit(taFetchDataThread);
        try {
          HashMap<Category,List<Activity> > res= future.get();

            taDBThread.insert(res,location);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
