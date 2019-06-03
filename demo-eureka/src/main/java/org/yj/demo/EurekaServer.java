package org.yj.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EurekaServer.class).run(args);

    }

}
