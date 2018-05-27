/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.config;

import com.psnpsn.MSClients.service.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ClientDetailsService customDetailsService;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

        @Bean
         public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(customDetailsService);
                authenticationProvider.setPasswordEncoder(encoder());
                return authenticationProvider;
        }
        
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customDetailsService)
                    .passwordEncoder(encoder())
                    .and()
                    .authenticationProvider(authenticationProvider())
                    .jdbcAuthentication();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
                        .cors().disable()
                        .csrf()
                        .disable()
                        .authorizeRequests()
                        .anyRequest()
                        .authenticated()
                        .antMatchers("/","/**","/login")
                        .permitAll()
                        .antMatchers(HttpMethod.OPTIONS,"/oauth/token").permitAll()
                        .and()
                        .httpBasic()
                        .and()
                        .sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.NEVER)
                        ;
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
        }
       
}