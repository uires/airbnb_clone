package br.com.airbnb.domain.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.airbnb.domain.acomodacao.Acomodacao;

@Entity
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String primeiroNome;
	
	@Column(nullable = false)
	private String segundoNome;

	@OneToOne
	@JoinColumn(name = "foto_id", referencedColumnName = "id")
	private Foto foto;

	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private LocalDate dataNascimento;

	private boolean permiteEmailDeMarketing;

	// Flag para verificar se o e-mail do usuário foi confirmado
	private boolean enabled = false;

	@JsonIgnore
	@Column(nullable = false)
	private String senha;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	private List<Acomodacao> acomodacoes;
	
	
	public Usuario() { }

	public Usuario(Long id, String primeiroNome, String segundoNome, Foto foto, String email, LocalDate dataNascimento,
			boolean permiteEmailDeMarketing, boolean enabled, String senha, List<Acomodacao> acomodacoes) {
		this.id = id;
		this.primeiroNome = primeiroNome;
		this.segundoNome = segundoNome;
		this.foto = foto;
		this.email = email;
		this.dataNascimento = dataNascimento;
		this.permiteEmailDeMarketing = permiteEmailDeMarketing;
		this.enabled = enabled;
		this.senha = senha;
		this.acomodacoes = acomodacoes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return this.primeiroNome + " " + this.segundoNome;
	}

	public Foto getFoto() {
		return foto;
	}

	public List<Acomodacao> getAcomodacoes() {
		return acomodacoes;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public String getSegundoNome() {
		return segundoNome;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public boolean isPermiteEmailDeMarketing() {
		return permiteEmailDeMarketing;
	}

	public String getSenha() {
		return senha;
	}

}
