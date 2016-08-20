package ru.sbt.orlov;

import java.lang.reflect.Method;

public class MethodCache {

    private final Method method;
    private final String fileName;

    public MethodCache(Method method, String key) {
        this.method = method;
        this.fileName = key;
    }


    public boolean isCached(Object[] args) {
        return false;
    }

    public Object getFromCache(Object[] args) {
        return null;
    }

    public Object putInCache(Object value) {
        return null;
    }

    public boolean isCacheValid(Method method) {
        return false;
    }
}
