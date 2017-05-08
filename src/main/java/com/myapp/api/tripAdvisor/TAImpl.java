package com.myapp.api.tripAdvisor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tal on 26/04/2017.
 */
@Component
public class TAImpl implements TAApi{
    @Autowired
    ManageTA manageTA;

    public  void getData() throws Exception{

//        manageTA.setLocation("London,United Kingdom");
//        Thread thread = new Thread(manageTA);
//        thread.start();
//
//        manageTA.setLocation("Tel Aviv, Israel");
//        Thread thread2 = new Thread(manageTA);
//        thread2.start();

        manageTA.setLocation("New York City, New York");
        Thread thread3 = new Thread(manageTA);
        thread3.start();


    }

}
