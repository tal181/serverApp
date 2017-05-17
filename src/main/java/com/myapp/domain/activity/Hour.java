package com.myapp.domain.activity;

/**
 * Created by Tal on 16/05/2017.
 */
public class Hour {


    Integer hour;
    Integer min;
    Boolean am;

    public Boolean getAm() {
        return am;
    }

    public void setAm(Boolean am) {
        this.am = am;
    }



    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Hour{" +
                "hour=" + hour +
                ", min=" + min +
                '}';
    }
}
