package br.com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.airbnb.domain.acomodacao.Acomodacao;

@Repository
public interface AcomodacaoRepository extends JpaRepository<Acomodacao, Long> { }
