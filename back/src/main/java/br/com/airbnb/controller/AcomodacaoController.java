package br.com.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.service.AcomodacaoService;

@RestController
@RequestMapping("/acomodacao")
public class AcomodacaoController {

	@Autowired
	private AcomodacaoService service;

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody boolean valor) {
		return null;
	}
}
