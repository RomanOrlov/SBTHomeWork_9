package ru.sbt.orlov.cache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy<T> implements InvocationHandler {
    private final T delegateService;
    private final String cacheDir;
    private Map<String,MethodCache> cacheMap = new HashMap<>();

    public CacheProxy(T delegateService,String cacheDir) {
        this.delegateService = delegateService;
        this.cacheDir = cacheDir;
    }

    public static<T> T cache(T service,String cacheDir) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                service.getClass().getInterfaces(),
                new CacheProxy(service,cacheDir));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Cache.class)) {
            return invoke(method,args);
        } else {
            String key = getMethodCacheKey(method);
            MethodCache cache;
            if (cacheMap.containsKey(key)) {
                cache =  cacheMap.get(getMethodCacheKey(method));
                if (!cache.isCacheValid(method)) {
                    throw new IllegalArgumentException("Для каждого метода должен быть уникальный ключ кеширования (По дефолту это имя метода)");
                }
            } else {
                cache = new MethodCache(method,cacheDir,key);
                cacheMap.put(key,cache);
            }
            Object cachedValue = cache.getFromCache(args);
            if (cachedValue!=null) {
                return cachedValue;
            } else {
                return cache.putInCache(args,invoke(method,args));
            }
        }
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegateService, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Невозможно вызвать метод "+method.getName(),e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static String getMethodCacheKey(Method method) {
        String key = method.getAnnotation(Cache.class).cacheFileNameOrKey();
        return key.isEmpty() ? method.getName() : key;
    }
}
