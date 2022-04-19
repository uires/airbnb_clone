package br.com.airbnb.domain.usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String url;
	
	@OneToOne(mappedBy = "foto")
	private Usuario usuario;
	
	public Foto() { }

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
}
