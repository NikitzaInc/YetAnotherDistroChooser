package com.nikitzainc.distrochooser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DistroChooserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistroChooserApplication.class, args);
    }

}
