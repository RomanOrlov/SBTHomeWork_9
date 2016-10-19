package ru.sbt.orlov.service;

import ru.sbt.orlov.cache.Cache;
import ru.sbt.orlov.model.Patient;
import ru.sbt.orlov.model.PatientsManager;
import ru.sbt.orlov.model.People;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    PatientsManager patientsManager = new PatientsManager();

    @Override
    public int countPeopleMeanAgeFromDB() {
        return (int) (1000 * Math.random());
    }

    @Override
    public void getSomeThing(int one, Long two) {

    }


    @Override
    public Date findBirthday(People people) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    @Override
    public List<Patient> patientsWithAge(Integer from, Integer to) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ((from + to) % 2 == 0) {
            return patientsManager.getPatients();
        } else {
            return new ArrayList<>(patientsManager.getPatients().subList(0, 2));
        }
    }

    @Override
    public Patient findPatient(int historyNum, String surname) {
        try {
            // Go to Database
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return patientsManager.getPatients()
                .stream()
                .filter(patient -> patient.getSurname().equals(surname))
                .findAny().orElse(null);
    }

    @Cache
    @Override
    public Date whenPatientDied(Patient patient, boolean closeHistory) {
        return new Date();
    }
}
