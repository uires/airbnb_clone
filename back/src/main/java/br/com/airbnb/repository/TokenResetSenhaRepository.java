package br.com.airbnb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.airbnb.domain.usuario.TokenResetSenha;

public interface TokenResetSenhaRepository extends JpaRepository<TokenResetSenha, Long> {
	public Optional<TokenResetSenha> findByToken(String token);
}
