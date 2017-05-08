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
    private Double rating;
    private int numbersOfReviews;
    private int duration;
    private List<DayPart> dayParts;

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


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public List<DayPart> getDayParts() {
        return dayParts;
    }

    public void setDayParts(List<DayPart> dayParts) {
        this.dayParts = dayParts;
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
