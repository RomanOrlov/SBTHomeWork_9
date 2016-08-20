package ru.sbt.orlov;

import ru.sbt.orlov.model.Patient;
import ru.sbt.orlov.model.People;

import java.util.Date;
import java.util.List;

public interface Service {

    int countPeopleMeanAgeFromDB();

    Date findBirthday(People people);

    List<Patient> patientsWithAge(Integer from, Integer to);

    Patient findPatient(int historyNum,String surname);

    Date whenPatientDied(Patient patient, boolean closeHistory);
}
