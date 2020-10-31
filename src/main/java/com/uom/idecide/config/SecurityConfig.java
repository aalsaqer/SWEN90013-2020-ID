package com.uom.idecide.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This is a configuration class which is the used to configure Spring Security.
 * we want to cancel the function of authentication provided by Spring Security
 * because we use JWT as our authentication tool instead.
 */
@Configuration
@EnableWebSecurity
//WebSecurityConfigurerAdapter有很多空方法，需要的時候拿來重载即可
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}