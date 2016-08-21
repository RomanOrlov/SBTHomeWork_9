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
        Patient patient2 = new Patient("Anya","Petrova","Vasilyda",false,"Wooman");
        patient1.setAdress(new Adress("Moscow","115511","Shkuleva11/22"));
        patient1.setPassport(new Passport(2222,222_222,new Date(),"SomewhereForSarcastic"));
        Patient patient3 = new Patient("Petya","Kolzkov","Matveevich",true,"Alcoholic");
        patient1.setAdress(new Adress("Moscow","?????","Homeless"));
        patient1.setPassport(new Passport(2134,235_234,new Date(),"Jupiter"));
        patients.add(patient1);
        patients.add(patient2);
        patients.add(patient3);
        return patients;
    }
}
