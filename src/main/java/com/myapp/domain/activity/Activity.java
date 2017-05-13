package com.myapp.domain.activity;

import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.List;

/**
 * Created by tal on 26/04/2017.
 */
public class Activity {
    private Integer activityId;
    private String activityName;
    private String location;
    private String suggestedDuration;
    private Double rating;
    private int numbersOfReviews;
    private List<DayPart> dayParts;
    private List<String> openHours;
    public Activity(){

    }
    public Activity(Integer activityId, String activityName, String location,
                    String suggestedDuration, Double rating, int numbersOfReviews,
                    List<DayPart> dayParts, List<String> openHours) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.location = location;
        this.suggestedDuration = suggestedDuration;
        this.rating = rating;
        this.numbersOfReviews = numbersOfReviews;
        this.dayParts = dayParts;
        this.openHours = openHours;
    }

    public Activity(Activity otherActivity) {
        this.activityId = otherActivity.activityId;
        this.activityName = otherActivity.activityName;
        this.location = otherActivity.location;
        this.suggestedDuration = otherActivity.suggestedDuration;
        this.rating = otherActivity.rating;
        this.numbersOfReviews = otherActivity.numbersOfReviews;
        this.dayParts = otherActivity.dayParts;
        this.openHours = otherActivity.openHours;
    }

    public String getSuggestedDuration() {
        return suggestedDuration;
    }

    public void setSuggestedDuration(String suggestedDuration) {
        this.suggestedDuration = suggestedDuration;
    }
    public int getNumbersOfReviews() {
        return numbersOfReviews;
    }

    public void setNumbersOfReviews(int numbersOfReviews) {
        this.numbersOfReviews = numbersOfReviews;
    }


    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", location='" + location + '\'' +
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
