package de.deutschestheater.welchezukunft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	      .httpBasic().and()
	      .authorizeRequests()
		    .antMatchers("/confirmregistration/").hasIpAddress("127.0.0.1/32")
	        .antMatchers("/index.html", "/login.html", "/", "/adduser/", "/confirmregistration/", "/removeuser/", "/isfull/").permitAll()
	        .antMatchers("/js/**").permitAll()
	        .antMatchers("/css/**").permitAll()
	        .anyRequest().authenticated()
	        .and()
		.formLogin()
			.loginPage("/login.html")
			.and()
		 .csrf()
		     .disable();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("zukunft").password("password").roles("USER");
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
	    SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
	    handler.setUseReferer(true);
	    return handler;
	}
}
