package br.com.airbnb.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	@Transactional
	public Avaliacao cadastra(Reserva reserva, Avaliacao avaliacao) {
		reserva.adicionaAvaliacao(avaliacao);
		return this.avaliacaoRepository.save(avaliacao);
	}
}
