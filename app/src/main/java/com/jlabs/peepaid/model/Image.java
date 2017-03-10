package com.jlabs.peepaid.model;

import java.io.Serializable;

/**
 * Created by Lincoln on 04/04/16.
 */
public class Image implements Serializable {
    private String name;
    private String title;
    private String review;

    private String small, medium, large;
    private String timestamp;
    Double dis, rating, lat, longi;

    public Image() {
    }

    public Image(String name, String small, String medium, String large, String timestamp) {
        this.name = name;
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.timestamp = timestamp;
    }
    public Image(String title, String review, Double rating, Double dis, Double lat, Double longi) {
        this.title = title;
        this.review = review;
        this.rating = rating;
        this.dis = dis;
        this.lat = lat;
        this.longi = longi;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLo() {
        return longi;
    }

    public void setLo(Double longi) {
        this.longi = longi;
    }


    //My Changes


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    public Double getDis() {
        return dis;
    }

    public void setDis(Double dis) {
        this.dis = dis;
    }


    //



    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
