package ru.sbt.orlov.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientsManager {
    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();
        Patient patient1 = new Patient("Vova","Petrov","Andreevich",true,"Meningioma");
        patient1.setAdress(new Adress("Moscow","115511","Shkuleva11/22"));
        patient1.setPassport(new Passport(1111,111_111,new Date(),"Somewhere"));
        patients.add(patient1);
        return patients;
    }
}
