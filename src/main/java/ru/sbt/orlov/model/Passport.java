package ru.sbt.orlov.model;

import java.io.Serializable;
import java.util.Date;

public class Passport implements Serializable{
    int series;
    int number;
    Date whenCreated;
    String whereCreated;
    private People mother;
    private People father;

    public Passport() {
    }

    public Passport(int number, int series, Date whenCreated, String whereCreated) {
        this.number = number;
        this.series = series;
        this.whenCreated = whenCreated;
        this.whereCreated = whereCreated;
    }

    public People getFather() {
        return father;
    }

    public void setFather(People father) {
        this.father = father;
    }

    public People getMother() {
        return mother;
    }

    public void setMother(People mother) {
        this.mother = mother;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getWhereCreated() {
        return whereCreated;
    }

    public void setWhereCreated(String whereCreated) {
        this.whereCreated = whereCreated;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "father=" + father +
                ", series=" + series +
                ", number=" + number +
                ", whenCreated=" + whenCreated +
                ", whereCreated='" + whereCreated + '\'' +
                ", mother=" + mother +
                '}';
    }
}
