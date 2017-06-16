package com.myapp; /**
 * Created by Tal on 29/04/2017.
 */
import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import com.myapp.api.algorithm.HamdanUtils;
import com.myapp.api.tripAdvisor.CalcEstimateTimeThread;
import com.myapp.api.tripAdvisor.ManageTA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class SpringBootJerseyApplication {

    @Autowired
    MongoClient mongoConfig;

    @Autowired
    MongoTemplate mongoClient;

    @Bean
    @Scope("prototype")
    public ManageTA manageTA() {
        return new ManageTA();
    }

    @Bean
    @Scope("prototype")
    public CalcEstimateTimeThread calcEstimateTimeThread() {
        return new CalcEstimateTimeThread();
    }

//    @Bean
//    @Scope("prototype")
//    public HamdanUtils hamdanUtils() {
//        return new HamdanUtils();
//    }

    @Bean
    public Mongobee mongobee(){
       // String mongoUrl=mongoClient.get
        Mongobee runner = new Mongobee(mongoConfig);
        runner.setDbName(mongoClient.getDb().getName());
        runner.setChangeLogsScanPackage("com.myapp.DB.changeSet"); // package to scan for changesets
        runner.setEnabled(true);         // optional: default is true
        runner.setMongoTemplate(mongoClient);
        return runner;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJerseyApplication.class, args);
    }
}