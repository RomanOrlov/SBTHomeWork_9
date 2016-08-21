package ru.sbt.orlov.cache;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.sbt.orlov.cache.CacheProxy;
import ru.sbt.orlov.model.Adress;
import ru.sbt.orlov.model.Passport;
import ru.sbt.orlov.model.Patient;
import ru.sbt.orlov.model.PatientsManager;
import ru.sbt.orlov.service.Service;
import ru.sbt.orlov.service.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CacheProxyTest {

    ServiceImpl service = new ServiceImpl();
    String cacheDir = "src/test/resources";
    PatientsManager patientsManager = new PatientsManager();
    List<Patient> patients;
    Service cache;
    
    @Before
    public void doBefore() {
        cache = CacheProxy.cache(service, cacheDir);
        patients = patientsManager.getPatients();
    }
    
    @Test
    public void testCache() throws Exception {
        try {
            cache.countPeopleMeanAgeFromDB();
            fail("Ќельз€ считать кеш не зна€ входных параметров");
        } catch (RuntimeException ex) {}
        try {
            cache.getSomeThing(1,1L);
            fail("Ќе имеет смысла ставить аннотацию на метод, который ничего не возвращает");
        } catch (RuntimeException ex) {}
    }

    @Ignore
    @Test
    public void testCacheInMemory() {
        System.out.println("Calculating " +cache.findBirthday(patients.get(0)));
        System.out.println("From cache "+cache.findBirthday(patients.get(0)));
        System.out.println("From cache "+cache.findBirthday(patients.get(0)));
    }

    @Test
    public void testCacheInFile() {
        System.out.println(cache.findPatient(14,"Petrov"));
        System.out.println(cache.findPatient(14,"Petrov"));
        System.out.println(cache.findPatient(14,"Petrov"));
        System.out.println(cache.findPatient(14,"Petrov"));
        System.out.println(cache.findPatient(14,"Petrov"));
    }
}