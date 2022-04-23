package br.com.airbnb.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.airbnb.controller.form.AcomodacaoForm;
import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.AcomodacaoService;

@RestController
@RequestMapping("/acomodacao")
public class AcomodacaoController {

	@Autowired
	private AcomodacaoService service;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<Acomodacao> cadastra(@RequestBody AcomodacaoForm form,
			UriComponentsBuilder uriComponentsBuilder) {
		Acomodacao acomodacao = form.converte(usuarioRepository);
		acomodacao = this.service.cadastraAcomodacao(acomodacao);
		URI uri = uriComponentsBuilder.path("/acomodacao/{id}").buildAndExpand(acomodacao.getId()).toUri();
		return ResponseEntity.created(uri).body(acomodacao);
	}

	@PostMapping("/upload/{id}")
	public ResponseEntity<?> upload(@RequestParam("arquivos") MultipartFile[] arquivos) {
		return null;
	}
}
