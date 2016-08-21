package ru.sbt.orlov.cache;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.sbt.orlov.model.Patient;
import ru.sbt.orlov.model.PatientsManager;
import ru.sbt.orlov.service.Service;
import ru.sbt.orlov.service.ServiceImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        // Must be very fast
        System.out.println(cache.findPatient(14,"Petrov"));
        System.out.println(cache.findPatient(24,"Petrov"));
        System.out.println(cache.findPatient(34,"Petrov"));
        System.out.println(cache.findPatient(54,"Petrov"));
        // Not Exist
        assertEquals(cache.findPatient(14,"UnknownCreature"),null);
        // For list
        cache.patientsWithAge(0,1);
        cache.patientsWithAge(214,344);
    }
}