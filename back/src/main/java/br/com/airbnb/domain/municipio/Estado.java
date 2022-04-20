package br.com.airbnb.domain.municipio;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String uf;

	@OneToMany(mappedBy = "estado")
	private List<Municipio> municipios;

	public Estado() { }

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public List<Municipio> getMunicipios() {
		return municipios;
	}

}
