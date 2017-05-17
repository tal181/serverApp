package com.myapp.api.tripAdvisor;

import com.myapp.SpringBootJerseyApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by tal on 26/04/2017.
 */
@Component
public class TAImpl implements TAApi{
    
    public  void getData() throws Exception{
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBootJerseyApplication.class);
        ManageTA manageTA1 =(ManageTA) ctx.getBean("manageTA");
        manageTA1.setLocation("London,United Kingdom");
        Thread thread = new Thread(manageTA1);
        thread.start();

        ManageTA manageTA2 = (ManageTA)ctx.getBean("manageTA");
        manageTA2.setLocation("Tel Aviv, Israel");
        Thread thread2 = new Thread(manageTA2);
        thread2.start();

        ManageTA manageTA3 = (ManageTA)ctx.getBean("manageTA");
        manageTA3.setLocation("New York City, New York");
        Thread thread3 = new Thread(manageTA3);
        thread3.start();


    }

    @Override
    public void syncEstimateTime() throws Exception{
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringBootJerseyApplication.class);
        CalcEstimateTimeThread  calcEstimateTimeThread = (CalcEstimateTimeThread)ctx.getBean("calcEstimateTimeThread");
        calcEstimateTimeThread.run();
    }

}
