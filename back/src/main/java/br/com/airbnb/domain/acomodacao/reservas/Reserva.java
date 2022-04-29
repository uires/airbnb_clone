package br.com.airbnb.domain.acomodacao.reservas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

	/**
	 * Calcula o total da reserva
	 */
	public void calculaTotal() {
		BigDecimal valorTotal = null;

		if (this.getAcomodacao().getPrecificacao().getValor() != null) {
			long quantidadeDiasReserva = this.getInicioReserva().until(this.getFimReserva(), ChronoUnit.DAYS);
			valorTotal = this.getAcomodacao().getPrecificacao().getValor()
					.multiply(new BigDecimal(quantidadeDiasReserva));
		}

		if (this.getAcomodacao().getPrecificacao().getValorMensal() != null) {
			long quantidadeMeses = ChronoUnit.MONTHS.between(this.getInicioReserva(), this.getFimReserva());
			valorTotal = this.getAcomodacao().getPrecificacao().getValor().multiply(new BigDecimal(quantidadeMeses));
		}

		this.valorTotal = this.valorTotal.add(valorTotal);
	}

	/**
	 * Subtrai do valor total a taxa de desconto calculada
	 * 
	 * @param taxaDesconto
	 */
	public void aplicaDesconto(BigDecimal taxaDesconto) {
		this.valorTotal = this.getValorTotal().subtract(taxaDesconto).setScale(2, RoundingMode.HALF_UP);
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", inicioReserva=" + inicioReserva + ", fimReserva=" + fimReserva
				+ ", dataCriacaoReserva=" + dataCriacaoReserva + ", descontoSemanal=" + descontoSemanal
				+ ", valorTotal=" + valorTotal + ", reservaCancelada=" + reservaCancelada + ", quantidadeHospedes="
				+ quantidadeHospedes + ", hospede=" + hospede + ", acomodacao=" + acomodacao + "]";
	}

}
