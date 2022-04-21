package br.com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.airbnb.domain.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{ }
