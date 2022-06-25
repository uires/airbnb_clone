package br.com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.airbnb.domain.usuario.pagamento.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> { }
