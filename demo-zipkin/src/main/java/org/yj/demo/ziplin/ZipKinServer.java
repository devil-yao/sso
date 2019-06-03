package org.yj.demo.ziplin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class ZipKinServer {

    public static void main(String[] args) {
        SpringApplication.run(ZipKinServer.class,args);
    }

}
