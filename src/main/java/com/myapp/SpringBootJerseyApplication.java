package com.myapp; /**
 * Created by Tal on 29/04/2017.
 */
import com.myapp.api.tripAdvisor.CalcEstimateTimeThread;
import com.myapp.api.tripAdvisor.ManageTA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringBootJerseyApplication {


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

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJerseyApplication.class, args);
    }
}