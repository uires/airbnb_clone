package br.com.airbnb.domain.acomodacao.reservas.avaliacao;

import java.math.BigDecimal;

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
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Column(precision = 5, scale = 1)
	@Getter
	private BigDecimal  notaLimpeza;

	@Column(precision = 5, scale = 1)
	@Getter
	private BigDecimal  notaComunicacao;

	@Column(precision = 5, scale = 1)
	@Getter
	private BigDecimal  notaCheckIn;

	@Column(precision = 1, scale = 1)
	@Getter
	private BigDecimal  notaExatidaoDoAnuncio;

	@Column(precision = 1, scale = 1)
	@Getter
	private BigDecimal  notaLocalizacao;

	@Column(precision = 1, scale = 1)
	@Getter
	private BigDecimal  notaCustoBenefico;

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
