package com.mpss.oms.auth.confg.meta;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record AppJWTProperties(
		String issuer,
		String secretKey
		) {
	
}
