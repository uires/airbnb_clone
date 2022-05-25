package br.com.airbnb.service.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.airbnb.domain.usuario.Usuario;

@Service
public class TokenService {

	@Value("${jwt.expiration-ms}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	public String geraToken(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		Date agora = new Date();

		Date expiracao = new Date(agora.getTime() + Long.parseLong(this.expiration));

		Algorithm algorithm = Algorithm.HMAC256(this.secret);
		String sign = JWT.create().withIssuer("API Airbnb").withSubject(usuario.getId().toString())
				.withIssuedAt(new Date()).withExpiresAt(expiracao).sign(algorithm);

		return sign;
	}

}
