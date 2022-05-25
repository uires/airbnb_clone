package br.com.airbnb.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import br.com.airbnb.service.security.TokenService;

public class JWTFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	public JWTFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = this.getCabecalhoToken(request);
		this.tokenService.isTokenValido(token);
		filterChain.doFilter(request, response);
	}

	private String getCabecalhoToken(HttpServletRequest request) {
		String tokenHeader = request.getHeader("Authorization");

		if (tokenHeader == null || tokenHeader.isBlank() || tokenHeader.isEmpty()
				|| !tokenHeader.startsWith("Bearer ")) {
			return null;
		}

		return tokenHeader.substring(7, tokenHeader.length());
	}

}
