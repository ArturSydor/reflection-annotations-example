package com.learning.basic.methodhandler_basic;

import lombok.*;

@Builder
public class City {

    private String title;
    private int population;
    private String postCode;

    public City() {
        System.out.println("Default constructor was called");
    }

    public City(String title, int population, String postCode) {
        this.title = title;
        this.population = population;
        this.postCode = postCode;
        System.out.println("All args constructor was called for " + this);
    }

    public String getTitle() {
        System.out.println("Get title was called for " + this.toString());
        return title;
    }

    public void setTitle(String title) {
        System.out.println("Set title was called for " + this);
        this.title = title;
    }

    public Integer getPopulation() {
        System.out.println("Get population was called for " + this);
        return population;
    }

    public void setPopulation(int population) {
        System.out.println("Set population was called for " + this);
        this.population = population;
    }

    public String getPostCode() {
        System.out.println("Get post code was called for " + this);
        return postCode;
    }

    public void setPostCode(String postCode) {
        System.out.println("Set post code was called for " + this);
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "City{" +
                "title='" + title + '\'' +
                ", population=" + population +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
