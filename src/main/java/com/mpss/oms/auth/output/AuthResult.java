package com.mpss.oms.auth.output;

public record AuthResult(
		Long id,
		String userName,
		String authToken
		) {

}
