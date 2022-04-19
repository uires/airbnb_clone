package br.com.airbnb.domain.acomodacao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarMaisQueDoisDestaquesException;

@Entity
public class Acomodacao {
	@Id
	@GeneratedValue()
	private Long id;
	private TipoLugar tipoLugar;
	private Localizacao localizacao;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private Endereco endereco;
	private Hospedes hopedes;
	private List<Espaco> espacos;
	private PrecoPernoite precoPernoite;

	private String descricao;

	private List<Destaque> destaques;

	private String titulo;

	public Acomodacao() { }

	public Acomodacao(Long id, TipoLugar tipoLugar, Localizacao localizacao, Endereco endereco, Hospedes hopedes,
			List<Espaco> espacos, PrecoPernoite precoPernoite, String descricao, List<Destaque> destaques,
			String titulo) {
		if (destaques.size() > 2) {
			throw new NaoEhPossivelCadastrarMaisQueDoisDestaquesException();
		}

		this.id = id;
		this.tipoLugar = tipoLugar;
		this.localizacao = localizacao;
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

	public Localizacao getLocalizacao() {
		return localizacao;
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

}
