package br.com.airbnb.domain.acomodacao.reservas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	private LocalDateTime inicioReserva;

	@Getter
	private LocalDateTime fimReserva;

	@Getter
	private LocalDateTime dataCriacaoReserva;

	@Getter
	private BigDecimal descontoSemanal;

	@Getter
	private BigDecimal valorTotal;

	private boolean reservaCancelada = false;

	@Getter
	private Integer quantidadeHospedes;

	@OneToOne
	@JoinColumn(name = "hospede_id", referencedColumnName = "id")
	@Getter
	private Usuario hospede;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Getter
	private Acomodacao acomodacao;

	public boolean isReservaCancelada() {
		return reservaCancelada;
	}

}
