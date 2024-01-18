package com.example.homework4.models;

/*
//Homework 2
//Khaled Mohamed Ali and Joseph Mauney
 */

import java.io.Serializable;

public class Profile implements Serializable {
   public int weight=0;
    public String gender;

    public Profile(int weight, String gender) {
        this.weight = weight;
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public String isGender() {
        return gender;
    }

    public Profile() {
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
