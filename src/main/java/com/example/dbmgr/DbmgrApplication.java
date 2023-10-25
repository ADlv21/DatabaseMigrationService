package com.example.dbmgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DbmgrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbmgrApplication.class, args);
    }

}
