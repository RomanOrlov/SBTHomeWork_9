package ru.sbt.orlov.model;

import java.io.Serializable;

public class Adress implements Serializable{
    String city;
    String street;
    String index;

    public Adress() {
    }

    public Adress(String city, String index, String street) {
        this.city = city;
        this.index = index;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
