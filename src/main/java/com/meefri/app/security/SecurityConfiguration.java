package com.meefri.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/index";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/";


	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception { //
		return super.authenticationManagerBean();
	}

	@Bean
	public CustomRequestCache requestCache() { //
		return new CustomRequestCache();
	}


	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Not using Spring CSRF here to be able to use plain HTML for the login page
		http.csrf().disable()

				// Register our CustomRequestCache, that saves unauthorized access attempts, so
				// the user is redirected after login.
				.requestCache().requestCache(new CustomRequestCache())

				// Restrict access to our application.
				.and().authorizeRequests()

				.antMatchers("/","/index","/register").permitAll()


				// Allow all flow internal requests.
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				// Allow all requests by logged in users.
				.anyRequest().authenticated()

				// Configure the login page.
				.and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
				.failureUrl(LOGIN_FAILURE_URL)

				// Configure logout
				.and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		super.configure(auth);
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
				"/VAADIN/**",
				"/favicon.ico",
				"/robots.txt",
				"/manifest.webmanifest",
				"/sw.js",
				"/offline-page.html",
				"/icons/**",
				"/images/**",
				"/frontend/**",
				"/webjars/**",
				"/h2-console/**",
				"/frontend-es5/**", "/frontend-es6/**");
	}
}
