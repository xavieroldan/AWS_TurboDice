/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
@SpringBootApplication
public class WebappApplication extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
//        SpringApplication.run(WebappApplication.class, args);
        SpringApplication.run(WebappApplication.class, args);
        System.out.println("Running............");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        return builder.sources(WebappApplication.class);
    }

}
