package br.com.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> { }
