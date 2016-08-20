package ru.sbt.orlov.model;

import java.io.Serializable;

public class Patient extends People implements Serializable {
    private int historyNum;
    private String diagnosis;

    public Patient(String name, String surname, String patronymic, boolean isMale, String diagnosis) {
        super(name, surname, patronymic, isMale);
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getHistoryNum() {
        return historyNum;
    }

    public void setHistoryNum(int historyNum) {
        this.historyNum = historyNum;
    }
}
