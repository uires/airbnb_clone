package br.com.airbnb.domain.acomodacao.reservas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.exception.ImpossibilidadeCancelarException;
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
	private BigDecimal desconto;

	@Getter
	private BigDecimal valorTotal;

	private boolean reservaCancelada = false;

	@Getter
	@Embedded
	private Hospedes hospedes;

	@OneToOne
	@JoinColumn(name = "hospede_id", referencedColumnName = "id")
	@Getter
	private Usuario hospede;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Getter
	private Acomodacao acomodacao;

	@Getter
	private LocalDateTime dataCancelamento;

	@Getter
	@Column(nullable = true, length = 1000)
	private String informacoes;

	@Getter
	private boolean reservaConfirmada;

	@Getter
	private LocalDateTime dataConfirmacaoAnfitriao;

	@Getter
	private BigDecimal taxaServico;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "reembolso_id")
	private Reembolso reembolso;

	public boolean isReservaCancelada() {
		return reservaCancelada;
	}

	/**
	 * Calcula o total da reserva
	 */
	public void calculaTotal() {
		BigDecimal valorTotal = null;
		Acomodacao acomodacao = this.getAcomodacao();

		if (acomodacao.getPrecificacao().getValor() != null) {
			long quantidadeDiasReserva = this.getInicioReserva().until(this.getFimReserva(), ChronoUnit.DAYS);
			valorTotal = acomodacao.getPrecificacao().getValor().multiply(new BigDecimal(quantidadeDiasReserva));
		}

		if (acomodacao.getPrecificacao().getValorMensal() != null) {
			long quantidadeMeses = ChronoUnit.MONTHS.between(this.getInicioReserva(), this.getFimReserva());
			valorTotal = acomodacao.getPrecificacao().getValor().multiply(new BigDecimal(quantidadeMeses));
		}

		this.valorTotal = this.valorTotal.add(valorTotal).add(acomodacao.getTaxaDeLimpeza())
				.add(acomodacao.getTaxaDeServico());

		this.taxaServico = acomodacao.getTaxaDeServico();
	}

	/**
	 * Subtrai do valor total a taxa de desconto calculada
	 * 
	 * @param taxaDesconto
	 */
	public void aplicaDesconto(BigDecimal taxaDesconto) {
		this.desconto = taxaDesconto;
		this.valorTotal = this.getValorTotal().subtract(taxaDesconto).setScale(2, RoundingMode.HALF_UP);
	}

	/**
	 * Cancela a reserva conforme critérios
	 */
	public void cancela() {

		if (this.isReservaCancelada()) {
			throw new ImpossibilidadeCancelarException();
		}

		// Até 48 horas o reembolso é completo, caso intervalo de
		if (ChronoUnit.HOURS.between(this.getDataCriacaoReserva(), LocalDateTime.now()) < 48) {
			this.reembolso = new Reembolso(null, this.getValorTotal(), false, null, this);
		}

		// Após 48 horas o reembolso é 50% sem o valor da taxa de serviço
		if (ChronoUnit.HOURS.between(this.getDataCriacaoReserva(), LocalDateTime.now()) >= 48) {
			this.reembolso = new Reembolso(null,
					this.getValorTotal().subtract(this.getTaxaServico()).multiply(new BigDecimal("0.5")), false, null,
					this);
		}

		this.reservaCancelada = true;
		this.dataCancelamento = LocalDateTime.now();
	}

}
