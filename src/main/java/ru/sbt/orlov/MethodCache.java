package ru.sbt.orlov;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class MethodCache {

    // Всё хуйня полюбас стринг херачить надо. Ибо думать надо как файл назвать с таким кешем, так что
    // не херач ебаный лист (сука, ну тупое же решение!)
    private final Map<String,Object> cache = new HashMap<>();
    private final Method method;
    private final String dir;
    private final CacheType cacheType;
    private final int[] identifyByArgNumbers;
    private final int maxListElementsCached;
    private final boolean zip;

    public MethodCache(Method method, String key) {
        this.method = method;
        this.dir = key;
        Cache cacheDescriptor = method.getAnnotation(Cache.class);
        this.cacheType = cacheDescriptor.cacheType();
        this.identifyByArgNumbers = cacheDescriptor.identifyByArgNumbers();
        this.maxListElementsCached = cacheDescriptor.maxListElementsCached();
        this.zip = cacheDescriptor.zip();
    }

    /**
     * Придумай стретегию перевода аргументов в уникальное имя
     * @param args
     * @return
     */
    public Object getFromCache(Object[] args) {
        String key = getCacheKeyByArgs(args);
        switch (cacheType) {
            case FILE: {
                // Тянем из файла
            }
            case IN_MEMORY: {
                return cache.get(key);
            }
            default:
                throw new IllegalArgumentException("Unreachable statement");
        }
    }

    public Object putInCache(Object[] args, Object value) {
        return null;
    }

    public boolean isCacheValid(Method method) {
        return this.method == method;
    }

    private String getCacheKeyByArgs(Object[] args) {
        // Here will be key String strategy
        return null;
    }

    private Object getCacheFromFile(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(new File(dir,fileName));
            // Если zip - оборачиваем в ZipInputStream
            if (zip) {
                inputStream = new ZipInputStream(inputStream);
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object result = objectInputStream.readObject();
            objectInputStream.close();
            return result;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка во время записи в файл в директории "+dir+" c именем "+fileName,e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Класс не был найден ",e);
        }
    }

    private void putCacheInFile(String fileName,Object result) {
        try {
            File file = new File(dir,fileName);
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            // Если zip - оборачиваем в ZipOutputStream
            if (zip) {
                outputStream = new ZipOutputStream(outputStream);
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(result);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл в директории "+dir+" c именем "+fileName+" не можем быть создан",e);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка во время записи в файл в директории "+dir+" c именем "+fileName,e);
        }
    }
}
