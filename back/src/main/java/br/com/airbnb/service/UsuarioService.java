package br.com.airbnb.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public void cadastraUsuario(Usuario usuario) {
		this.usuarioRepository.save(usuario);
	}
}
