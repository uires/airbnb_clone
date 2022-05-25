package br.com.airbnb.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.airbnb.controller.auth.form.CadastroForm;
import br.com.airbnb.controller.auth.form.LoginForm;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@PostMapping("/cadastro")
	public ResponseEntity<Usuario> cadastro(@RequestBody @Valid CadastroForm form) {
		form.setSenha(this.encoder.encode(form.getSenha()));
		Usuario usuario = this.usuarioService.cadastraUsuario(form.converte());
		return ResponseEntity.created(null).body(usuario);
	}

	@PutMapping("/upload/{id}")
	public ResponseEntity<Usuario> upload(@PathVariable(required = true) Long id,
			@RequestParam(required = true) MultipartFile file) {

		Usuario usuario = this.usuarioService.uploadImagem(id, file);
		return ResponseEntity.ok(usuario);
	}

	@PostMapping
	public ResponseEntity<?> login(@RequestBody @Valid LoginForm loginForm) {

		return null;
	}

}
