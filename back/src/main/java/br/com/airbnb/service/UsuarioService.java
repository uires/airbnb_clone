package br.com.airbnb.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.domain.email.Email;
import br.com.airbnb.domain.usuario.Foto;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.FotoUsuarioRepository;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.email.EmailService;
import br.com.airbnb.service.exception.NaoPossuiPermissaoAlterarException;
import br.com.airbnb.service.image.ImageResponse;
import br.com.airbnb.service.image.ImageService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private FotoUsuarioRepository fotoUsuarioRepository;

	@Autowired
	private EmailService emailService;

	@Transactional
	public Usuario cadastraUsuario(Usuario usuario) {
		usuario = this.usuarioRepository.save(usuario);

		try {
			emailService.sendHtmlMessage(this.getEstruturaEmailConfirmacaoRegistro(usuario));
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return usuario;
	}

	@Transactional
	public Usuario uploadImagem(Long id, MultipartFile file) {

		Optional<Usuario> optional = this.usuarioRepository.findById(id);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException();
		}

		Usuario usuario = optional.get();

		Usuario usuarioContext = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!usuarioContext.getId().equals(usuario.getId())) {
			throw new NaoPossuiPermissaoAlterarException();
		}

		if (usuario.getFoto() != null) {
			Foto foto = usuario.getFoto();
			this.fotoUsuarioRepository.delete(foto);
			this.imageService.deleteImage(foto.getDeletehash());
		}

		ImageResponse imageResponse = this.imageService.uploadOne(file);
		usuario.adicionaFoto(new Foto(imageResponse.getLink(), imageResponse.getDeletehash(), usuario));
		return usuario;
	}

	/**
	 * Aprova o usuário conforme código verificação enviado
	 * 
	 * @throws EntityNotFoundException
	 * @param codigoVerificacao
	 * @return retornar o usuário aprovado
	 */
	@Transactional
	public Usuario aprovaRegistro(String codigoVerificacao) {
		Optional<Usuario> optional = this.usuarioRepository.findByCodigoVerificacao(codigoVerificacao);

		if (!optional.isPresent()) {
			throw new EntityNotFoundException();
		}

		Usuario usuario = optional.get();
		usuario.aprovaRegistro();
		return usuario;
	}

	/**
	 * Devolve a estrutura de e-mail para confirmação de registro
	 * 
	 * @param usuario
	 * @return estrutura do e-mail com as informações do usuário e template html
	 */
	private Email getEstruturaEmailConfirmacaoRegistro(Usuario usuario) {
		HashMap<String, Object> valores = new LinkedHashMap<String, Object>();
		valores.put("usuario", usuario);
		return new Email("Confirme sua conta", usuario.getEmail(), "confirmacao-registro.html", valores);
	}
}
