package br.com.airbnb.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.controller.form.AcomodacaoForm;
import br.com.airbnb.controller.form.ConsultaForm;
import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.service.AcomodacaoService;

@RestController
@RequestMapping("/acomodacao")
public class AcomodacaoController {

	@Autowired
	private AcomodacaoService service;

	@PostMapping
	public ResponseEntity<Acomodacao> cadastra(@Valid @RequestBody AcomodacaoForm form,
			UriComponentsBuilder uriComponentsBuilder) {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Acomodacao acomodacao = form.converte(usuario);

		acomodacao = this.service.cadastraAcomodacao(acomodacao);

		URI uri = uriComponentsBuilder.path("/acomodacao/{id}").buildAndExpand(acomodacao.getId()).toUri();
		return ResponseEntity.created(uri).body(acomodacao);
	}

	@PutMapping("/upload/{id}")
	public ResponseEntity<Acomodacao> upload(@RequestParam("arquivos") MultipartFile[] arquivos,
			@PathVariable(required = true) Long id) {
		Acomodacao acomodacao = this.service.cadastraFotos(id, arquivos);
		return ResponseEntity.ok(acomodacao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Acomodacao> busca(@PathVariable(required = true) Long id) {
		Optional<Acomodacao> optional = this.service.busca(id);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException();
		}

		return ResponseEntity.ok(optional.get());
	}

	@PostMapping("/consulta-acomodacao")
	public ResponseEntity<Page<Acomodacao>> consultaAcomodacao(@RequestBody ConsultaForm form, Pageable pageable) {
		Page<Acomodacao> acomodacoes = this.service.consultaAcomodacoes(pageable, form);
		return ResponseEntity.ok(acomodacoes);
	}

	@PostMapping("/consulta-sumarizada")
	public ResponseEntity<Long> consultaSumarizada(@RequestBody ConsultaForm form) {
		long quantidade = this.service.consultaSumarizada(form);
		return ResponseEntity.ok(quantidade);
	}

}
