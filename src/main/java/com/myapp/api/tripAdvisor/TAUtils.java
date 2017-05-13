package com.myapp.api.tripAdvisor;

import com.myapp.Constants;
import org.springframework.stereotype.Component;

/**
 * Created by Tal on 13/05/2017.
 */
@Component
public class TAUtils {
    public Integer parseSuggestedDuration(String suggestedDuration){
        Integer ans= Constants.DEFAULT_SUGGESTED_DURATION;

        if(suggestedDuration.startsWith("<")){
            String temp=suggestedDuration.replace("<","");
            temp=temp.split(" ")[0];
            ans=Integer.parseInt(temp)*60;
        }
        else if(suggestedDuration.startsWith("More than ")){
            String temp=suggestedDuration.replace("More than ","");
            temp=temp.split("")[0];
            ans=Integer.parseInt(temp)*60;
        }
        else if(suggestedDuration.contains("-")){
            String temp=suggestedDuration.split(" ")[0]; //1-2
             String sideA=temp.split("-")[0];
             String sideB=temp.split("-")[1];
             Double res=((Double.parseDouble(sideA)+Double.parseDouble(sideB))/2)*60;
             ans=res.intValue();
        }

        return ans;

    }

    public static void main(String[] args){
        Integer ans=0;
        TAUtils utils = new TAUtils();


        ans=utils.parseSuggestedDuration("<1 hour");
        System.out.println("ans is" +ans);

        ans=utils.parseSuggestedDuration("1-2 hours");
        System.out.println("ans is" +ans);

        ans=utils.parseSuggestedDuration("More than 3 hours");
        System.out.println("ans is" +ans);

        ans=utils.parseSuggestedDuration("Yes");
        System.out.println("ans is" +ans);

    }
}
