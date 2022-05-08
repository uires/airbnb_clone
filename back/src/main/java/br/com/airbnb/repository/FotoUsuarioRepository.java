package br.com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.airbnb.domain.usuario.Foto;

@Repository
public interface FotoUsuarioRepository extends JpaRepository<Foto, Long> { }
