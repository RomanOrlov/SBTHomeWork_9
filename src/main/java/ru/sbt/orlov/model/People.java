package ru.sbt.orlov.model;

import java.io.Serializable;

public class People implements Serializable {
    private String name;
    private String surname;
    private String patronymic;
    private boolean isMale;
    private Adress adress = new Adress();
    private transient Passport passport = new Passport();

    public People(String name, String surname, String patronymic, boolean isMale) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.isMale = isMale;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "People{" +
                "adress=" + adress +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
