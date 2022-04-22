package br.com.airbnb.domain.acomodacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "foto_acomodacao")
@Table(name = "foto_acomodacao")
@NoArgsConstructor
@AllArgsConstructor
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Getter
	@Column(nullable = false)
	private String urlFotografia;

	@ManyToOne
	@JoinColumn(name = "acomodacao_id")
	@Getter
	private Acomodacao acomodacao;

}
