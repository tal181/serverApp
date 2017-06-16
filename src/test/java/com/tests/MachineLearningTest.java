package com.tests;

import com.myapp.api.activity.ActivityApi;
import com.tests.db.AbstractLiquiBase;
import com.tests.db.TestContex;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Tal on 14/06/2017.
 */

public class MachineLearningTest  extends TestContex {


    @Autowired
    ActivityApi activityApi;
    @Test
    public void sayHello(){
        System.out.println("say hellp");
    }
}
