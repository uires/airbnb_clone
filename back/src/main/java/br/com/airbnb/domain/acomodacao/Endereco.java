package br.com.airbnb.domain.acomodacao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	private String rua;

	@Getter
	private String estado;

	@Getter
	private String cidade;

	@OneToOne(mappedBy = "endereco")
	private Acomodacao acomodacao;

	@Getter
	private String codigoPostal;
}
