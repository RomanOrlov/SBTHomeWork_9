package ru.sbt.orlov;

import ru.sbt.orlov.model.Patient;
import ru.sbt.orlov.model.People;

import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service {

    @Cache
    @Override
    public int countPeopleMeanAgeFromDB() {
        return 0;
    }

    @Cache(cacheType = CacheType.IN_MEMORY,cacheFileNameOrKey = "BirthdayData")
    @Override
    public Date findBirthday(People people) {
        return null;
    }

    @Cache(cacheType = CacheType.FILE,identifyByArgNumbers = {0,1},maxListElementsCached = 10)
    @Override
    public List<Patient> patientsWithAge(Integer from, Integer to) {
        return null;
    }

    @Cache(cacheType = CacheType.FILE,cacheFileNameOrKey = "PatientsCache",identifyByArgNumbers = {1},zip = true)
    @Override
    public Patient findPatient(int historyNum,String surname) {
        return null;
    }

    @Override
    public Date whenPatientDied(Patient patient, boolean closeHistory) {
        return null;
    }

}
