package fr.com.nfa019.restaurant.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationService authenticationService;

	// Configurations of Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder(4));
	}

	// Configurations of Authorization: urls, etc
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic();

		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/historiques").permitAll()
				.antMatchers(HttpMethod.GET, "/refrigerateurs/**").permitAll()
				.antMatchers(HttpMethod.POST, "/refrigerateurs/**").permitAll()
				.antMatchers(HttpMethod.GET, "/stocks").permitAll()
				.antMatchers(HttpMethod.POST, "/stocks/**").permitAll()
				.antMatchers(HttpMethod.PUT, "/stocks/**").permitAll()
				.antMatchers(HttpMethod.GET, "/utilisateurs/verify/**").permitAll()
				.antMatchers(HttpMethod.GET, "/produits/**").permitAll() // we need that to creating stock
				.antMatchers(HttpMethod.GET, "/**").permitAll()
				.anyRequest().hasAnyAuthority("ADMIN");
	}

	// Configurations for static files (js, css, image, etc)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}

//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
}
