package com.yj.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.yj.demo.repositories")
public class ElasApp {

    public static void main(String[] args) {
        SpringApplication.run(ElasApp.class,args);
    }

}
