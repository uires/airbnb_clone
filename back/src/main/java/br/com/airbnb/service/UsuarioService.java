package br.com.airbnb.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.airbnb.controller.exception.EntityNotFoundException;
import br.com.airbnb.domain.email.Email;
import br.com.airbnb.domain.usuario.Foto;
import br.com.airbnb.domain.usuario.TokenResetSenha;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.FotoUsuarioRepository;
import br.com.airbnb.repository.TokenResetSenhaRepository;
import br.com.airbnb.repository.UsuarioRepository;
import br.com.airbnb.service.email.EmailService;
import br.com.airbnb.service.exception.NaoPossuiPermissaoAlterarException;
import br.com.airbnb.service.exception.auth.SenhaNaoCoincidemException;
import br.com.airbnb.service.exception.auth.TokenExpiradoException;
import br.com.airbnb.service.image.ImageResponse;
import br.com.airbnb.service.image.ImageService;
import net.bytebuddy.utility.RandomString;

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

	@Autowired
	private TokenResetSenhaRepository tokenSenhaRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

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

	@Transactional
	public void geraTokenRecuperacao() {
		Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TokenResetSenha token = new TokenResetSenha(RandomString.make(155), LocalDateTime.now(), usuario);
		token = this.tokenSenhaRepository.save(token);

		try {
			this.emailService.sendHtmlMessage(this.getEstruturaEmailRecuperacaoSenha(usuario, token));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
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

	/**
	 * Devolve a estrutura de e-mail para resete de senha
	 * 
	 * @param usuario
	 * @return estrutura do e-mail com as informações do usuário, token e template
	 *         html
	 */
	private Email getEstruturaEmailRecuperacaoSenha(Usuario usuario, TokenResetSenha token) {
		HashMap<String, Object> valores = new LinkedHashMap<String, Object>();
		valores.put("usuario", usuario);
		valores.put("token", token);
		return new Email("Recupere sua senha", usuario.getEmail(), "recuperacao-senha.html", valores);
	}

	@Transactional
	public void recuperaSenha(String token, @NotNull String senha, @NotNull String matchSenha) {
		if (!senha.equals(matchSenha)) {
			throw new SenhaNaoCoincidemException();
		}

		Optional<TokenResetSenha> optional = this.tokenSenhaRepository.findByToken(token);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException();
		}

		TokenResetSenha tokenResetSenha = optional.get();

		long tempoEmHorasCriacaoToken = ChronoUnit.HOURS.between(tokenResetSenha.getDataExpiracao(),
				LocalDateTime.now());

		if (tempoEmHorasCriacaoToken >= 24L) {
			throw new TokenExpiradoException();
		}

		Usuario usuario = tokenResetSenha.getUsuario();
		usuario.alteraSenha(this.encoder.encode(senha));
	}

}
