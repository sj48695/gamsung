package com.gamsung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig 
	extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailServiceImpl;
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailServiceImpl)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.httpBasic()
			.and()
			
			.authorizeRequests()
			.antMatchers("/member/admin").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/member/mypage").access("hasAnyRole('ROLE_USER, ROLE_BLACKLIST')")
			.antMatchers("/product/write").access("hasRole('ROLE_USER')")
			.antMatchers("/deal").access("hasRole('ROLE_USER')")
			.anyRequest().permitAll()
			.and()
			
			.formLogin()
			.loginPage("/member/login")
			.usernameParameter("id")
			.passwordParameter("pwd")
			.loginProcessingUrl("/member/login")
			.and()
			
			.logout()
			.logoutUrl("/member/logout")
			.deleteCookies("JSESSIONIND")
			.invalidateHttpSession(true)
			.and().csrf().disable()
			
			
			
			.rememberMe()
			.key("myAppKey")
			.tokenValiditySeconds(86400)
			.and()
			
			.sessionManagement()
			.maximumSessions(3)
			.maxSessionsPreventsLogin(false)
			
			;
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	

}
