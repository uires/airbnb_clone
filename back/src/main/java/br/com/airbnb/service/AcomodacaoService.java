package br.com.airbnb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.repository.AcomodacaoRepository;

@Service
public class AcomodacaoService {
	
	@Autowired
	private AcomodacaoRepository repository;
	
	public void cadastraAcomodacao(Acomodacao acomodacao) {
		this.repository.save(acomodacao);
	}
}
