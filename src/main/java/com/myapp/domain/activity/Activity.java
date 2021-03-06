package com.myapp.domain.activity;

import javafx.util.Pair;
import org.bson.types.ObjectId;
import org.joda.time.Hours;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.List;

/**
 * Created by tal on 26/04/2017.
 */
public class Activity {
    private String activityId;

    private String activityName;
    private String locationId;
    private String address;
    private Integer suggestedDuration;
    private Double rating;
    private int numbersOfReviews;
    private List<DayPart> dayParts;
    private List<Pair<Hour,Hour>> openHours;
    private String categoryId;
    public Activity(){

    }
    public Activity(String activityId, String activityName, String locationId,
                    Integer suggestedDuration, Double rating, int numbersOfReviews,
                    List<DayPart> dayParts, List<Pair<Hour,Hour>> openHours,String categoryId) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.locationId = locationId;
        this.suggestedDuration = suggestedDuration;
        this.rating = rating;
        this.numbersOfReviews = numbersOfReviews;
        this.dayParts = dayParts;
        this.openHours = openHours;
        this.categoryId=categoryId;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Activity(Activity otherActivity) {
        this.address=otherActivity.address;
        this.activityId = otherActivity.activityId;
        this.activityName = otherActivity.activityName;
        this.locationId = otherActivity.locationId;
        this.suggestedDuration = otherActivity.suggestedDuration;
        this.rating = otherActivity.rating;
        this.numbersOfReviews = otherActivity.numbersOfReviews;
        this.dayParts = otherActivity.dayParts;
        this.openHours = otherActivity.openHours;
        this.categoryId=otherActivity.categoryId;
    }

    public Integer getSuggestedDuration() {
        return suggestedDuration;
    }

    public void setSuggestedDuration(Integer suggestedDuration) {
        this.suggestedDuration = suggestedDuration;
    }
    public int getNumbersOfReviews() {
        return numbersOfReviews;
    }

    public void setNumbersOfReviews(int numbersOfReviews) {
        this.numbersOfReviews = numbersOfReviews;
    }


    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<DayPart> getDayParts() {
        return dayParts;
    }

    public void setDayParts(List<DayPart> dayParts) {
        this.dayParts = dayParts;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", address='" + address + '\'' +
                ", location='" + locationId + '\'' +
                ", suggestedDuration='" + suggestedDuration + '\'' +
                ", rating=" + rating +
                ", numbersOfReviews=" + numbersOfReviews +
                '}';
    }

    public enum   DayPart{
        MORNING(0),
        AFTERNOON(1),
        EVENING(2);

        private Integer partOfDay;

        DayPart(Integer partOfDay) {
            this.partOfDay = partOfDay;
        }

        public Integer partOfDay() {
            return partOfDay;
        }

    }


}
