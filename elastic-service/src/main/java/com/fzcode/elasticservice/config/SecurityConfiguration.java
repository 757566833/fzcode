package com.fzcode.elasticservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity//开启权限验证
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
//                .formLogin()
//                .loginProcessingUrl("auth/login")
//                .usernameParameter("email")
//                .and()
                .authorizeRequests()
                .antMatchers("/elastic/*").permitAll()
//                .anyRequest().authenticated()
                .and()
        ;

    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
