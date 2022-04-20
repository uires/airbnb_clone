package br.com.airbnb.domain.acomodacao;

import java.awt.Point;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarMaisQueDoisDestaquesException;
import br.com.airbnb.domain.usuario.Usuario;

@Entity
public class Acomodacao {
	@Id
	@GeneratedValue()
	private Long id;

	@Enumerated(EnumType.STRING)
	private TipoLugar tipoLugar;

	@Column(name = "localizacao", columnDefinition = "POINT")
	private Point localizacao;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private Endereco endereco;

	@Embedded
	private Hospedes hopedes;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Espaco> espacos;

	@Embedded
	private PrecoPernoite precoPernoite;

	@Column(length = 500)
	private String descricao;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Destaque> destaques;

	@Column(length = 50)
	private String titulo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "foto_id")
	private List<Foto> fotos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Acomodacao() { }

	public Acomodacao(Long id, TipoLugar tipoLugar, Endereco endereco, Hospedes hopedes, List<Espaco> espacos,
			PrecoPernoite precoPernoite, String descricao, List<Destaque> destaques, String titulo) {
		if (destaques.size() > 2) {
			throw new NaoEhPossivelCadastrarMaisQueDoisDestaquesException();
		}

		this.id = id;
		this.tipoLugar = tipoLugar;
		this.endereco = endereco;
		this.hopedes = hopedes;
		this.espacos = espacos;
		this.precoPernoite = precoPernoite;
		this.descricao = descricao;

		this.destaques = destaques;
		this.titulo = titulo;
	}

	public Long getId() {
		return id;
	}

	public TipoLugar getTipoLugar() {
		return tipoLugar;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Hospedes getHopedes() {
		return hopedes;
	}

	public List<Espaco> getEspacos() {
		return espacos;
	}

	public PrecoPernoite getPrecoPernoite() {
		return precoPernoite;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Destaque> getDestaques() {
		return destaques;
	}

	public String getTitulo() {
		return titulo;
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public Point getLocalizacao() {
		return localizacao;
	}

}
