package br.com.airbnb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.airbnb.domain.acomodacao.reservas.Reembolso;

@Repository
public interface ReembolsoRepository extends JpaRepository<Reembolso, Long> {

	public List<Reembolso> findByProcessadoFalse();

}
