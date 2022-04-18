package br.com.airbnb.domain.municipio;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cidades")
public class Municipio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	private Estado estado;

	public Municipio() { }

	public String getNome() {
		return nome;
	}

	public Long getId() {
		return id;
	}

	public Estado getEstado() {
		return estado;
	}

}
