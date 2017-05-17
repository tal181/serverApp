package com.myapp.api.algorithm;

import org.springframework.stereotype.Component;

/**
 * Created by Tal on 17/05/2017.
 */
@Component
public class Hamdan {
    private Double rating;
    private Integer duration;

    public Hamdan(){

    }
    public Hamdan(Double rating,Integer duration){
        this.rating=rating;
        this.duration=duration;

    }
}
