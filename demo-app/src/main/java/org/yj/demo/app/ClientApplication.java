package org.yj.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Sso
@EnableHystrix
@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "org.yj.demo.app.feign")
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class,args);
    }

}
