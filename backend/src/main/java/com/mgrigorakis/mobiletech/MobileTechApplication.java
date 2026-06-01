package com.mgrigorakis.mobiletech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MobileTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileTechApplication.class, args);
    }

}
