package ru.sbt.orlov.service;

import ru.sbt.orlov.cache.Cache;
import ru.sbt.orlov.cache.CacheType;
import ru.sbt.orlov.model.Patient;
import ru.sbt.orlov.model.People;

import java.util.Date;
import java.util.List;

public interface Service {

    @Cache
    int countPeopleMeanAgeFromDB();

    @Cache(cacheType = CacheType.FILE)
    void getSomeThing(int one,Long two);

    @Cache(cacheType = CacheType.IN_MEMORY,cacheFileNameOrKey = "BirthdayData")
    Date findBirthday(People people);

    @Cache(cacheType = CacheType.FILE,identifyByArgNumbers = {0,1},maxListElementsCached = 10)
    List<Patient> patientsWithAge(Integer from, Integer to);

    @Cache(cacheType = CacheType.FILE,cacheFileNameOrKey = "PatientsCache",identifyByArgNumbers = {1},zip = true)
    Patient findPatient(int historyNum,String surname);

    @Cache
    Date whenPatientDied(Patient patient, boolean closeHistory);
}
