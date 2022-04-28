package br.com.airbnb.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;

@Service
public class ReservaService {

	@Transactional
	public Reserva cadastra(Reserva reserva, Acomodacao acomodacao) {
		acomodacao.adicionaReserva(reserva);
		return reserva;
	}
}
