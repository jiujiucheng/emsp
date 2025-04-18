package com.edwin.emsp;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableAutoConfiguration
@MapperScan({"com.edwin.emsp.dao.mapper"})
@ComponentScan(basePackages = {"com.edwin.emsp"})
public class EmspApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmspApplication.class, args);
        System.out.println("Application started!");
    }

}
