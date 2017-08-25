package common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import common.authentication.LoginAuthenticationProvider;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	LoginAuthenticationProvider loginProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// about CSRF protection
		// https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html#csrf-configure
		http.csrf().disable();// disable CSRF

		http.authorizeRequests().antMatchers("/", "/home", "/user/registration", "/css/**", "/images/**").permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
				.permitAll();

		// https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#jc-logout
		http.logout().logoutSuccessUrl("/home");

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// https://stackoverflow.com/questions/31826233/custom-authentication-manager-with-spring-security-and-java-configuration

		auth.authenticationProvider(loginProvider);
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");

	}
}