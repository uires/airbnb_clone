package br.com.airbnb.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.repository.ReservaRepository;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository repository;

	@Transactional
	public Reserva cadastra(Reserva reserva, Acomodacao acomodacao) {
		acomodacao.adicionaReserva(reserva);
		return reserva;
	}

	public Optional<Reserva> busca(Long id) {
		return repository.findById(id);
	}

	@Transactional
	public Reserva cancelaReserva(Reserva reserva) {
		reserva.cancela();
		return reserva;
	}
}
