package ru.sbt.orlov.cache;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class MethodCache {

    private final Map<String, Object> cache = new HashMap<>();
    private final Method method;
    private final String dir;
    private final String header;
    private final CacheType cacheType;
    private final int[] identifyByArgNumbers;
    private final int maxListElementsCached;
    private final boolean zip;

    public MethodCache(Method method, String dir, String key) {
        this.method = method;
        this.dir = dir;
        this.header = key;
        Cache cacheDescriptor = method.getAnnotation(Cache.class);
        this.cacheType = cacheDescriptor.cacheType();
        this.maxListElementsCached = cacheDescriptor.maxListElementsCached();
        this.zip = cacheDescriptor.zip();
        if (method.getReturnType() == void.class || method.getParameterCount() == 0) {
            throw new IllegalArgumentException("Нельзя использовать кеширование на методах без аргументов и без возвращаемого типпа");
        }
        if (cacheDescriptor.identifyByArgNumbers().length == 0) {
            identifyByArgNumbers = new int[method.getParameterCount()];
            for (int i = 0; i < identifyByArgNumbers.length; i++) {
                identifyByArgNumbers[i] = i;
            }
        } else {
            identifyByArgNumbers = cacheDescriptor.identifyByArgNumbers();
        }
    }

    public Object getFromCache(Object[] args) {
        String key = getCacheKeyByArgs(args);
        switch (cacheType) {
            case FILE: {
                return getCacheFromFile(key);
            }
            case IN_MEMORY: {
                return cache.get(key);
            }
            default:
                throw new IllegalArgumentException("Unreachable statement");
        }
    }

    public Object putInCache(Object[] args, Object value) {
        if (value==null) {
            return value;
        }
        String key = getCacheKeyByArgs(args);
        switch (cacheType) {
            case FILE: {
                putCacheInFile(key, value);
                return value;
            }
            case IN_MEMORY: {
                cache.put(key, value);
                return value;
            }
            default:
                throw new IllegalArgumentException("Unreachable statment");
        }
    }

    public boolean isCacheValid(Method method) {
        return this.method == method;
    }

    private Object getCacheFromFile(String fileName) {
        try {
            ObjectInputStream objectInputStream;
            if (zip) {
                ZipInputStream inputStream = new ZipInputStream(new FileInputStream(new File(dir, fileName+".zip")));
                inputStream.getNextEntry();
                objectInputStream = new ObjectInputStream(inputStream);
            } else {
                FileInputStream inputStream = new FileInputStream(new File(dir, fileName));
                objectInputStream = new ObjectInputStream(inputStream);
            }
            Object result = objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (FileNotFoundException e) {
            return null;
        } catch (EOFException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка во время записи в файл в директории " + dir + " c именем " + fileName + " "+e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Класс не был найден "+ e.getMessage(), e);
        }
    }

    private void putCacheInFile(String fileName, Object result) {
        try {
            if (result instanceof List) {
                List resultList = (List) result;
                if (resultList.size() > maxListElementsCached) {
                    // нельзя брать sublist, - иначе будет NotSerializibleException
                    result = new ArrayList<>(resultList.subList(0, maxListElementsCached));
                }
            }
            if (zip) {
                ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(new File(dir, fileName+".zip")));
                ZipEntry zipEntry = new ZipEntry(fileName);
                outputStream.putNextEntry(zipEntry);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(result);
                outputStream.closeEntry();
                objectOutputStream.close();
            } else {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File(dir, fileName)));
                objectOutputStream.writeObject(result);
                objectOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл в директории " + dir + " c именем " + fileName + " не можем быть создан", e);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка во время записи в файл в директории " + dir + " c именем " + fileName, e);
        }
    }

    private String getCacheKeyByArgs(Object[] args) {
        StringBuilder stringBuilder = new StringBuilder(header);
        for (int i=0;i<identifyByArgNumbers.length;i++) {
            stringBuilder.append(args[identifyByArgNumbers[i]].toString());
        }
        return stringBuilder.toString();
    }
}