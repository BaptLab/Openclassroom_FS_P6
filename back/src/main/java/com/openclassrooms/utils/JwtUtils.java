package com.openclassrooms.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.openclassrooms.models.User;
import com.openclassrooms.payload.request.LoginRequest;
import com.openclassrooms.repository.UserRepository;
import com.openclassrooms.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtils {

	public static JwtEncoder jwtEncoder;
	public static JwtDecoder jwtDecoder;
	public static PasswordEncoder passwordEncoder;
	public static UserRepository userRepository;

	public JwtUtils(PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, UserService userService,
			JwtDecoder jwtDecoder, UserRepository userRepository) {
		JwtUtils.passwordEncoder = passwordEncoder;
		JwtUtils.jwtEncoder = jwtEncoder;
		JwtUtils.jwtDecoder = jwtDecoder;
		JwtUtils.userRepository = userRepository;
	}

	public static String generateToken(Authentication authentication) {
		Instant now = Instant.now();
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now)
				.expiresAt(now.plus(1, ChronoUnit.DAYS)).subject(authentication.getName()).build();
		JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
				.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
		return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
	}

	public static String extractToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.substring(7);
			return token;
		}
		return null;
	}

	public static boolean isTokenValid(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			// Assuming authentication is based on JwtAuthenticationToken
			if (authentication instanceof JwtAuthenticationToken) {
				JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
				Jwt jwt = jwtAuthenticationToken.getToken();
				if (isTokenNotExpired(jwt)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	private static boolean isTokenNotExpired(Jwt jwt) {
		Instant expirationTime = jwt.getExpiresAt();
		Instant now = Instant.now();
		return expirationTime != null && !now.isAfter(expirationTime);
	}

	public static String getUserMailFromAuthentication(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			if (authentication instanceof JwtAuthenticationToken) {
				JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
				Jwt jwt = jwtAuthenticationToken.getToken();
				String userEmail = jwt.getClaim("sub").toString(); // Use "sub" claim for email
				try {
					return userEmail;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "User not found !";
	}

	public static boolean isPwdMatching(LoginRequest loginRequest, User user) {
		System.out.print(loginRequest.getPassword());
		System.out.print(user.getPassword());
		return passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
	}

	public static Authentication getAuthentication(String token) {
		if (token != null) {
			Jwt jwt = jwtDecoder().decode(token);
			if (isTokenNotExpired(jwt)) {
				return new JwtAuthenticationToken(jwt, new ArrayList<>());
			}
		}
		return null;
	}

	public static JwtDecoder jwtDecoder() {
		return jwtDecoder;
	}

	public static String pwdEncoder(String password) {
		password = passwordEncoder.encode(password);
		return password;
	}

	public static User getUserByEmail(String email, UserRepository userRepository) {
		return userRepository.findByEmail(email);
	}

}
