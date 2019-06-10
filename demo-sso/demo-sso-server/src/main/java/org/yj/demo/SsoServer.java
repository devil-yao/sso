package org.yj.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;


@EnableAuthorizationServer
@SpringBootApplication
public class SsoServer {

    public static void main(String[] args) {
        SpringApplication.run(SsoServer.class,args);
    }

    // @Bean
    // public RedisConnectionFactory connectionFactory(RedisProperties properties){

    //     RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(properties.getHost(),properties.getPort());
    //     JedisConnectionFactory factory = new JedisConnectionFactory(configuration);
    //     factory.setUsePool(true);

    //     JedisPoolConfig poolConfig = new JedisPoolConfig();
    //     poolConfig.setTestOnBorrow(true);
    //     poolConfig.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
    //     poolConfig.setMaxTotal(properties.getJedis().getPool().getMaxActive());
    //     poolConfig.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
    //     factory.setPoolConfig(poolConfig);
    //     return factory;
    // }
}
