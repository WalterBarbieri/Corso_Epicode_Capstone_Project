package CaptsoneProject.EcommerceGioielleria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import CaptsoneProject.EcommerceGioielleria.exceptions.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	JWTAuthFilter jat;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(c -> c.disable());
		http.csrf(c -> c.disable());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/utenti/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/comuni/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/indirizzi/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/gioielli/**").authenticated());
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll());
		http.addFilterBefore(jat, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling(handling -> handling.accessDeniedHandler(new CustomAccessDeniedHandler()));
		return http.build();
	}

	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(12);
	}
}
