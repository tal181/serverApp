package com.myapp.domain.activity;

/**
 * Created by tal on 26/04/2017.
 */
public class Activity {
    private Integer activityId;
    private String activityName;
    private String location;
    private Double rating;
    private int numbersOfReviews;


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


    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", location='" + location + '\'' +
                ", rating=" + rating +
                ", numbersOfReviews=" + numbersOfReviews +
                '}';
    }


}
