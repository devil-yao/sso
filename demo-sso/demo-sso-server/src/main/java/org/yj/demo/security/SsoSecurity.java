package org.yj.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.yj.demo.login.AuthFilter;
import org.yj.demo.login.AuthProvide;
import org.yj.demo.login.LoginFailHandle;
import org.yj.demo.login.LoginSuccessHandle;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SsoSecurity extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthPasswordConfig authPasswordConfig;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
//                .loginPage("/authentication/login")
                .loginProcessingUrl("/authentication/login")
        .and().cors().configurationSource(corsConfigurationSource())
        .and().csrf().disable()
        .authorizeRequests().anyRequest().authenticated()

        ;
        http.apply(authPasswordConfig);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/css/**","/js/**","/images/**","/webjars/**","/**/favicon.ico","/**/*.html");
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        return corsConfigurationSource;
    }

    @Component
    class AuthPasswordConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {


        @Resource
        private AuthorizationServerTokenServices tokenServices;
        @Resource
        private ClientDetailsService clientDetailsService;

        @Override
        public void configure(HttpSecurity http){
            AuthFilter filter = new AuthFilter();
            LoginSuccessHandle successHandle = new LoginSuccessHandle();
            successHandle.setTokenServices(tokenServices);
            successHandle.setClientDetailsService(clientDetailsService);
            filter.setAuthenticationSuccessHandler(successHandle);
            filter.setAuthenticationFailureHandler(new LoginFailHandle());
            Map map = http.getSharedObjects();
            filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
            filter.afterPropertiesSet();


            AuthProvide authProvide = new AuthProvide();
            authProvide.setPasswordEncoder(passwordEncoder);
            authProvide.setUserDetailsService(http.getSharedObject(UserDetailsService.class));

            http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                    .authenticationProvider(authProvide)
            ;
        }

    }

}
