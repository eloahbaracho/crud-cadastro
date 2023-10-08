package com.nodebounty.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/* Classe de configuração do Spring Security. Por padrão ele realiza autenticação tradicional, com sessões e etc.
 * Como nosso projeto é uma API REST, precisamos modificar esse padrão e criar uma autenticação JWT
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	private SecurityFilter securityFilter;

	// Nesse metódo eu configuro a aplicação para ser STATELESS (Padrão REST) e
	// defino as rotas públicas e privadas
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		return http
				.headers(headers -> headers.frameOptions(frame -> frame.disable()))
				.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(req -> {
					req.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/clientes/login")).permitAll(); //REST
					req.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/clientes")).permitAll();
					req.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
					req.anyRequest().authenticated();
				})
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	// Método necessário para o spring ser capaz de instanciar sozinho depois a classe de autenticação
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	// Método para falar qual a criptografia usada na senha, no nosso caso nenhuma então null
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
