package ru.sbt.orlov;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    private final Service delegateService;
    private final String cacheDir;
    private Map<String,MethodCache> cacheMap = new HashMap<>();

    public CacheProxy(Service delegateService,String cacheDir) {
        this.delegateService = delegateService;
        this.cacheDir = cacheDir;
    }

    public static Service cache(Service service,String cacheDir) {
        return (Service) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                service.getClass().getInterfaces(),
                new CacheProxy(service,cacheDir));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Cache.class)) {
            // ���� ��� ��������� - ������ ������� �����
            return invoke(method,args);
        } else {
            // ���� ����, - ���������� ����� �������� �� ����, � ���� ��� ��� ���, - �������� � ���
            String key = getMethodCacheKey(method);
            MethodCache cache;
            if (cacheMap.containsKey(key)) {
                cache =  cacheMap.get(getMethodCacheKey(method));
                // ���������, ��� ��� � ����� ������ ������������� ������ ����� ������
                if (!cache.isCacheValid(method)) {
                    throw new IllegalArgumentException("��� ������� ������ ������ ���� ���������� ���� ����������� (�� ������� ��� ��� ������)");
                }
            } else {
                cache = new MethodCache(method,key);
                cacheMap.put(key,cache);
            }
            Object cachedValue = cache.getFromCache(args);
            if (cachedValue!=null) {
                return cachedValue;
            } else {
                return cache.putInCache(invoke(method,args));
            }
        }
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegateService, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("���������� ������� ����� "+method.getName(),e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static String getMethodCacheKey(Method method) {
        String key = method.getAnnotation(Cache.class).cacheFileNameOrKey();
        return key.isEmpty() ? method.getName() : key;
    }
}