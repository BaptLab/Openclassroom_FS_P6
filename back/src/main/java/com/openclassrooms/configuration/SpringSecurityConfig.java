package com.openclassrooms.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SpringSecurityConfig {

	private String jwtKey = "xfDRBRiykW9MjYl0cuKxcPhI1A5CKRpPsYVENbPZna+l2Y2k4bK6n5thFXJsULmE";

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter();

		return http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((auth) -> auth.requestMatchers("/api/auth/register", "/api/auth/login",
						// -- Swagger UI v2
						"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
						"/configuration/security", "/swagger-ui.html", "/webjars/**",
						// -- Swagger UI v3 (OpenAPI)
						"/v3/api-docs/**", "/v3/api-docs.yaml", "/swagger-ui.html", "/swagger-ui/**", "/images/**",
						"static/images/**").permitAll().anyRequest().authenticated())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtDecoder jwtdecoder() {
		SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "RSA");
		return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
	}
}
