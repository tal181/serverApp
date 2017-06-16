package com; /**
 * Created by Tal on 14/06/2017.
 */

//import com.tests.db.AbstractLiquiBase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.myapp"})
public class AppConfig {



}