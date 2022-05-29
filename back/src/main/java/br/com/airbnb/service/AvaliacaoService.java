package br.com.airbnb.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.AvaliacaoRepository;
import br.com.airbnb.service.exception.NaoPossuiPermissaoAlterarException;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;

	@Transactional
	public Avaliacao cadastra(Reserva reserva, Avaliacao avaliacao) {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!usuario.getId().equals(reserva.getHospede().getId())) {
			throw new NaoPossuiPermissaoAlterarException();
		}
		
		reserva.adicionaAvaliacao(avaliacao);
		return this.avaliacaoRepository.save(avaliacao);
	}
}
