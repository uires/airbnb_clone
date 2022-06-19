package br.com.airbnb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.controller.form.CartaoForm;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/cadastra-cartao")
	public ResponseEntity<?> cadastraCartao(@RequestBody(required = true) CartaoForm form) {
		Usuario usuario = this.usuarioService.adicionaCartao(form.converte());

		return ResponseEntity.ok(usuario);
	}

}
