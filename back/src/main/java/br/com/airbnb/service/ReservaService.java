package br.com.airbnb.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.repository.AcomodacaoRepository;

@Service
public class ReservaService {

	@Autowired
	private AcomodacaoRepository acomodacaoRepository;

	@Transactional
	public Reserva cadastra(Reserva reserva, Long idAcomodacao) {
		Optional<Acomodacao> optionalAcomodacao = this.acomodacaoRepository.findById(idAcomodacao);
		if (!optionalAcomodacao.isPresent()) {
			throw new IllegalArgumentException();
		}

		Acomodacao acomodacao = optionalAcomodacao.get();

		acomodacao.adicionaReserva(reserva);
		return reserva;
	}
}
