package com.ch.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ch.warehouse.mapper")
public class WarehouseApplication {

    public static void main(String[] args) {

        SpringApplication.run(WarehouseApplication.class, args);
    }

}
