package br.com.airbnb.controller.auth.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.airbnb.domain.usuario.Usuario;

public class CadastroForm {
	
	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 255, min = 5)
	private String email;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 16, min = 8)
	private String senha;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 16, min = 8)
	private String matchSenha;

	@NotNull
	private String dataNascimento;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 255, min = 3)
	private String primeiroNome;

	@NotBlank
	@NotEmpty
	@NotNull
	@Length(max = 255, min = 3)
	private String segundoNome;

	@NotNull
	private boolean permiteEmailDeMarketing;

	public CadastroForm() { }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMatchSenha() {
		return matchSenha;
	}

	public void setMatchSenha(String matchSenha) {
		this.matchSenha = matchSenha;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getSegundoNome() {
		return segundoNome;
	}

	public void setSegundoNome(String segundoNome) {
		this.segundoNome = segundoNome;
	}

	public boolean isPermiteEmailDeMarketing() {
		return permiteEmailDeMarketing;
	}

	public void setPermiteEmailDeMarketing(boolean permiteEmailDeMarketing) {
		this.permiteEmailDeMarketing = permiteEmailDeMarketing;
	}
	
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Usuario converte() {

		return new Usuario(null, primeiroNome, segundoNome, null, email, LocalDate.parse(dataNascimento),
				permiteEmailDeMarketing, false, senha, null);
	}
}
