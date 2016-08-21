package ru.sbt.orlov.cache;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    /**
     * �������� ����������� ����������� � ������ ��� �� �����
     * @return ������ �����������
     */
    CacheType cacheType() default CacheType.IN_MEMORY;

    /**
     * �������� �����, � ������� ����� �������� ���, ��� ����� � Map
     * (�� ������� ����� ������������ ��� ������)
     * @return
     */
    String cacheFileNameOrKey() default "";

    /**
     * ���������, �� ������� ����� ����������� ����������� (������� � ����)
     * (� ������, ���� � ����� �������� 3 ���������� ���� ���������, �
     * ���������� ���� �� ������� � ��������)
     * @return
     */
    int[] identifyByArgNumbers() default {};

    /**
     * � ������, ���� ����� ���������� List, ���������� ���� ���������
     * ���������� ������ ���������
     * @return
     */
    int maxListElementsCached() default 100_000;

    /**
     * �������� �� ��������� ����������� � ���� � zip ������� (java.util.zip)
     * @return
     */
    boolean zip() default false;

}
