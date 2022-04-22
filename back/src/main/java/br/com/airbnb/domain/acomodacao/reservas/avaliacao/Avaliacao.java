package br.com.airbnb.domain.acomodacao.reservas.avaliacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Column(precision = 1, scale = 1)
	@Getter
	private Double notaLimpeza;

	@Column(precision = 1, scale = 1)
	@Getter
	private Double notaComunicacao;

	@Column(precision = 1, scale = 1)
	@Getter
	private Double notaCheckIn;

	@Column(precision = 1, scale = 1)
	@Getter
	private Double notaExatidaoDoAnuncio;

	@Column(precision = 1, scale = 1)
	@Getter
	private Double notaLocalizacao;

	@Column(precision = 1, scale = 1)
	@Getter
	private Double notaCustoBenefico;

	@Lob
	@Getter
	private String comentario;

	@Getter
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reserva_id", referencedColumnName = "id")
	private Reserva reserva;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Acomodacao acomodacao;
}
