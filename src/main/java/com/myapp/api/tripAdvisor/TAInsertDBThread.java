package com.myapp.api.tripAdvisor;

import com.myapp.api.activity.ActivityApi;
import com.myapp.api.category.CategoryApi;
import com.myapp.api.locationCategory.LocationCategoryApi;
import com.myapp.domain.activity.Activity;
import com.myapp.domain.category.Category;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Tal on 27/04/2017.
 */
@Component
@Scope("prototype")
public class TAInsertDBThread {

    @Autowired
    CategoryApi categoryApi;

    @Autowired
    LocationCategoryApi locationCategoryApi;

    @Autowired
    ActivityApi activityApi;

    public   void insert(HashMap<Category,List<Activity> > categoryAggregation, String location) throws Exception {
        System.out.println( "Starting adding "+ location + " to DB  !!!!!!!!!!");

        categoryApi.deleteAllRecords();

        activityApi.deleteAllRecords();

        //get all categories
        Map<String,Category> allCategoriesMap=categoryApi.getCategoriesMap();
        Set<Category> categories=categoryAggregation.keySet();

        //create diff categories
        handleCategoriesCreate(allCategoriesMap,categories);

        //insert activities
        for (Map.Entry<Category, List<Activity>> entry : categoryAggregation.entrySet())
        {
            try{
                System.out.println("Category is " +  entry.getKey());

                //set cat rating
                List<Activity> activites=entry.getValue();

                //calc category sum
                Double sumOfCategory=computeSumOfCategory(activites);

                Double catRating=computerCategoryRating(activites,sumOfCategory);
                Category key=entry.getKey();
                key.setRating(catRating);

                locationCategoryApi.updateCategory(key,location);

                //recalc category rating
                List<Activity> recalcActivitesRating=recalcActivitesRating(activites,sumOfCategory);
                activityApi.addActivities(recalcActivitesRating);
                System.out.println("activities are " +  recalcActivitesRating.toString());
            }
            catch (Exception e){
                LogManager.getRootLogger().error("error  " +e);
                System.out.println(e.getMessage());
            }


        }

        System.out.println( "finish adding "+ location + " to DB  !!!!!!!!!!");

    }
    private List<Activity> recalcActivitesRating(List<Activity> activites,Double sum){
        List<Activity> recalcActivites= new ArrayList<>();


        for(Activity activity:activites) {
            Activity recalcActiviy = new Activity(activity);
            recalcActiviy.setRating(recalcActiviy.getRating()/sum);
            recalcActivites.add(recalcActiviy);
        }
        return recalcActivites;
    }

    private Double computeSumOfCategory(List<Activity> activites){
        Double sumOfPeople=0D;
        for(Activity activity:activites) {
            sumOfPeople += activity.getNumbersOfReviews();
        }
        return sumOfPeople;
    }

    private Double computerCategoryRating(List<Activity> activites,Double sumOfPeople) {
        Double activityGrades=0D;
        for(Activity activity:activites) {
            Double activityGrade=activity.getNumbersOfReviews()*activity.getRating();
            activityGrades+=activityGrade;
        }
        return activityGrades/(sumOfPeople*10);
    }

    private void handleCategoriesCreate(Map<String,Category> allCategoriesMap, Set<Category> categories) throws Exception{
        //get all categories
        List<Category> catListToAdd=new ArrayList<>();
        categories.forEach(category->{
            if(allCategoriesMap.get(category.getCategoryName())==null) {
                catListToAdd.add(category);
            }
        });

        //add all categories
        categoryApi.addNewCategories(catListToAdd);
    }






}
