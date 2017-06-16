package com.tests;

import com.tests.db.AbstractLiquiBase;
import com.tests.db.TestContex;
import org.junit.Test;
import org.springframework.context.annotation.Import;

/**
 * Created by Tal on 14/06/2017.
 */
public class m2Test extends TestContex {
    @Test
    public void sayHello(){
        System.out.println("say hellp111111111111111111");
    }
}
