/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.config;

import static com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author jberroteran
 */
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;
        
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.apply(stormpath());
    }
}
