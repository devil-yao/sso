package org.yj.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class AuthConfig implements AuthorizationServerConfigurer {

    @Resource
    private DataSource dataSource;
    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder())
                .realm("sso")
        .checkTokenAccess("permitAll()")
        .tokenKeyAccess("isAuthenticated()");

//                .authenticationEntryPoint()

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(client())
            ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(accessTokenConverter())
                .reuseRefreshTokens(true)
                .tokenStore(tokenStore())
        .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
        .accessTokenConverter(converter())
//                .tokenGranter()
                ;
    }

    @Bean
    public AccessTokenConverter accessTokenConverter(){
        return new JwtAccessTokenConverter();
    }

    @Bean
    public TokenStore tokenStore(){
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix("sso_");
        return tokenStore;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientDetailsService client(){
        JdbcClientDetailsService clientDetailsService = new  JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder());
        return clientDetailsService;
    }

    @Bean
    public JwtAccessTokenConverter converter(){
        return new JwtAccessTokenConverter();
    }
}
