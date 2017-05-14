package com.myapp.domain.category;

import org.bson.types.ObjectId;

/**
 * Created by Tal on 16/04/2017.
 */
public class Category {

    private String categoryName;
    private double rating;
    private ObjectId id;
    public Category() {
    }
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category(String categoryName, double rating) {
        this.categoryName = categoryName;
        this.rating = rating;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (Double.compare(category.rating, rating) != 0) return false;
        if (category.id!=id) return false;
        return categoryName.equals(category.categoryName);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = categoryName.hashCode();
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = id.hashCode();
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryName='" + categoryName + '\'' +
                ", rating=" + rating +
                ", id=" + id +
                '}';
    }
}
