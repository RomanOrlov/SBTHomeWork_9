package ru.sbt.orlov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

@SpringBootApplication
@ImportResource({"classpath*:config.xml"})
public class App {
    private final ru.sbt.orlov.service.Service service;

    @Autowired
    public App(ru.sbt.orlov.service.Service service) {
        this.service = service;
    }


    public static void main(String[] args) {

    }
}
