package br.com.airbnb.domain.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@Column(nullable = false)
	@NonNull
	private String url;

	@Getter
	@Column(nullable = false)
	@NonNull
	private String deletehash;

	@OneToOne(mappedBy = "foto")
	@Getter
	@NonNull
	@JsonBackReference
	private Usuario usuario;

}
