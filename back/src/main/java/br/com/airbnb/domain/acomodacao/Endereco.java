package br.com.airbnb.domain.acomodacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String rua;
	private String estado;
	private String cidade;

	@OneToOne(mappedBy = "endereco")
	private Acomodacao acomodacao;

	private String codigoPostal;

	public Endereco() { }

	public Long getId() {
		return id;
	}

	public String getRua() {
		return rua;
	}

	public String getEstado() {
		return estado;
	}

	public String getCidade() {
		return cidade;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

}
