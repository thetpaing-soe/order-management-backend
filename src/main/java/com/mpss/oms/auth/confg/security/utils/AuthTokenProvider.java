package com.mpss.oms.auth.confg.security.utils;

import java.util.Base64;
import java.util.Date;

import org.springframework.security.core.Authentication;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthTokenProvider {
	
	private final String secretKey;
	private final String issuer;
	
	public String generate(Authentication auth) throws JOSEException {
		
		var roles = auth.getAuthorities().stream().map(a -> a.getAuthority()).toList();
		
		var jwtClaimsSet = new JWTClaimsSet.Builder()
				.issuer(issuer)
				.subject(auth.getName())
				.claim("roles", roles)
				.issueTime(new Date())
				.expirationTime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
				.build();
		
		var signedJwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), jwtClaimsSet);
		var signer = new MACSigner(Base64.getDecoder().decode(secretKey));
		
		signedJwt.sign(signer);
		
		return signedJwt.serialize();
	}
}
