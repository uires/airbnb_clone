package br.com.airbnb.domain.usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.usuario.exception.ImpossibilidadeAprovarRegistroException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Column(nullable = false)
	@Getter
	private String primeiroNome;

	@Column(nullable = false)
	@Getter
	private String segundoNome;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "foto_id", referencedColumnName = "id")
	@Getter
	private Foto foto;

	@Column(unique = true, length = 125)
	@Getter
	private String email;

	@Column(nullable = false)
	@Getter
	private LocalDate dataNascimento;

	private boolean permiteEmailDeMarketing;

	// Flag para verificar se o e-mail do usuário foi confirmado
	private boolean enabled = false;

	@Column(nullable = false)
	@Getter
	@JsonIgnore
	private String senha;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@JsonBackReference
	private List<Acomodacao> acomodacoes;

	@Getter
	private String codigoVerificacao;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<GrantedAuthority>();
	}

	@Override
	@JsonIgnore
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

	public String getNome() {
		return this.primeiroNome + " " + this.segundoNome;
	}

	public List<Acomodacao> getAcomodacoes() {
		return new ArrayList<Acomodacao>(acomodacoes);
	}

	public boolean isPermiteEmailDeMarketing() {
		return permiteEmailDeMarketing;
	}

	public void adicionaFoto(Foto foto) {
		this.foto = foto;
	}

	public void aprovaRegistro() {
		if (this.enabled) {
			throw new ImpossibilidadeAprovarRegistroException();
		}

		this.enabled = true;
	}

}
