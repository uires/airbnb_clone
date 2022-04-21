package br.com.airbnb.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.controller.auth.form.CadastroForm;
import br.com.airbnb.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class CadastroController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@PostMapping
	public ResponseEntity<?> cadastro(@RequestBody @Valid CadastroForm form) {
		form.setSenha(this.encoder.encode(form.getSenha()));
		this.usuarioService.cadastraUsuario(form.converte());
		return ResponseEntity.ok().build();
	}

}
