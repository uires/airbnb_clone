package br.com.airbnb.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.security.TokenService;

public class JWTFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	public JWTFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = this.getCabecalhoToken(request);
		boolean valido = this.tokenService.isTokenValido(token);

		if (valido) {
			this.autorizar(token);
		}

		filterChain.doFilter(request, response);
	}

	@Transactional
	private void autorizar(String token) {
		Long idUsuario = this.tokenService.getIdUsuario(token);
		Usuario usuario = this.usuarioRepository.findById(idUsuario).get();

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null,
				usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
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
