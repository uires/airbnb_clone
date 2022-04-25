package br.com.airbnb.domain.acomodacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acomodacao_id")
	@Getter
	@JsonBackReference
	private Acomodacao acomodacao;

	@Getter
	private String deletehash;
}
