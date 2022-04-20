package br.com.airbnb.domain.acomodacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "foto_acomodacao")
@Table(name = "foto_acomodacao")
public class Foto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String urlFotografia;

	@ManyToOne
	@JoinColumn(name = "acomodacao_id")
	private Acomodacao acomodacao;

	public Foto() { }

	public Foto(Long id, String urlFotografia) {
		this.id = id;
		this.urlFotografia = urlFotografia;
	}

	public Long getId() {
		return id;
	}

	public String getUrlFotografia() {
		return urlFotografia;
	}

}
