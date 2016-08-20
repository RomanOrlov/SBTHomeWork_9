package ru.sbt.orlov;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheProxyTest {

    ServiceImpl service = new ServiceImpl();

    @Test
    public void testCache() throws Exception {
        CacheProxy.cache(service);
    }
}