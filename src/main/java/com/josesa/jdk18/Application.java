package com.josesa.jdk18;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages={"com.josesa.jdk18.service" , "com.josesa.jdk18.dao"})
@EntityScan(basePackages="com.josesa.jdk18.entity")
public class Application {

}
