package com.mpss.oms.auth.confg.security;

import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.mpss.oms.auth.confg.security.utils.AuthTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class AuthSecurityConfig {
	
	@Value("${app.jwt.issuer}")
	private String issuer;

	@Value("${app.jwt.secretKey}")
	private String secretKey;

	@Bean
	PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/customer/**").hasRole("CUSTOMER")
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.authenticationManager(jwtAuthenticationManager(jwtDecoder(), jwtAuthenticationConverter()))))
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }
	
	@Bean
	AuthTokenProvider tokenProvider() {
		return new AuthTokenProvider(secretKey, issuer);
	}
	
	@Bean
	JwtDecoder jwtDecoder() {
		var secretKeySpes = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "HmacSHA512");
		
		return NimbusJwtDecoder
				.withSecretKey(secretKeySpes)
				.macAlgorithm(MacAlgorithm.HS512)
				.build();
	}   
	
	@Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        
        return jwtAuthenticationConverter;
    }    
	
	@Bean
    AuthenticationManager jwtAuthenticationManager(
            JwtDecoder jwtDecoder, 
            JwtAuthenticationConverter jwtAuthenticationConverter) {
        
        var provider = new JwtAuthenticationProvider(jwtDecoder);
        provider.setJwtAuthenticationConverter(jwtAuthenticationConverter);
        
        return new ProviderManager(provider);
    }
	
}
