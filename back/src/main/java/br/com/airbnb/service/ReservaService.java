package br.com.airbnb.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.ReservaRepository;
import br.com.airbnb.service.exception.NaoPossuiPermissaoAlterarException;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository repository;

	@Transactional
	public Reserva cadastra(Reserva reserva, Acomodacao acomodacao) {
		acomodacao.adicionaReserva(reserva);
		return reserva;
	}

	public Reserva busca(Long id) {
		Optional<Reserva> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException();
		}

		return optional.get();
	}

	@Transactional
	public Reserva cancelaReserva(Reserva reserva) {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!usuario.getId().equals(reserva.getHospede().getId())
				|| !usuario.getId().equals(reserva.getAcomodacao().getUsuario().getId())) {
			throw new NaoPossuiPermissaoAlterarException();
		}

		reserva.cancela();
		return reserva;
	}

	@Transactional
	public Reserva aprovaReserva(Reserva reserva) {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!usuario.getId().equals(reserva.getAcomodacao().getUsuario().getId())) {
			throw new NaoPossuiPermissaoAlterarException();
		}

		reserva.aprova();
		return reserva;
	}
}
