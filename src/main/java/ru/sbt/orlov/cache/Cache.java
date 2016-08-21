package ru.sbt.orlov.cache;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    /**
     * Хранение результатов кеширования в памяти или на диске
     * @return способ кеширования
     */
    CacheType cacheType() default CacheType.IN_MEMORY;

    /**
     * Название файла, в котором будет хранится кеш, или ключа в Map
     * (по дефолту будет использовано имя метода)
     * @return
     */
    String cacheFileNameOrKey() default "";

    /**
     * Аргументы, по которым будет происходить кеширование (Начиная с нуля)
     * (В случае, если в метод попадают 3 одинаковых типа параметра, а
     * кешировать надо по второму и третьему)
     * @return
     */
    int[] identifyByArgNumbers() default {};

    /**
     * В случае, если метод возвращает List, сохранится лишь указанное
     * количество первых элементов
     * @return
     */
    int maxListElementsCached() default 100_000;

    /**
     * Выводить ли результат кеширования в файл в zip формате (java.util.zip)
     * @return
     */
    boolean zip() default false;

}
