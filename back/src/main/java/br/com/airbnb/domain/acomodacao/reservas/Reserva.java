package br.com.airbnb.domain.acomodacao.reservas;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.exception.ImpossibilidadeCancelarException;
import br.com.airbnb.domain.acomodacao.exception.ImpossibilidadeConfirmarException;
import br.com.airbnb.domain.acomodacao.exception.NaoPodeAvaliarException;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.domain.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	@NonNull
	private LocalDateTime inicioReserva;

	@Getter
	@NonNull
	private LocalDateTime fimReserva;

	@Getter
	@NonNull
	private LocalDateTime dataCriacaoReserva;
	@Getter
	private BigDecimal desconto;

	@Getter
	private BigDecimal valorTotal = BigDecimal.ZERO;

	private boolean reservaCancelada = false;

	@Getter
	@Embedded
	@NonNull
	private Hospedes hospedes;

	@OneToOne
	@JoinColumn(name = "hospede_id", referencedColumnName = "id")
	@Getter
	@NonNull
	private Usuario hospede;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Getter
	@NonNull
	private Acomodacao acomodacao;

	@Getter
	@Column(nullable = true, length = 1000)
	private String informacoes;

	@Getter
	private boolean reservaConfirmada;

	@Getter
	private LocalDateTime dataConfirmacaoAnfitriao;

	@Getter
	private BigDecimal taxaServico;

	@Getter
	private LocalDateTime dataCancelamento;

	@Getter
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id", name = "reembolso_id")
	@JsonIgnore
	private Reembolso reembolso;

	@Getter
	@OneToOne(mappedBy = "reserva")
	private Avaliacao avaliacao;

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

		long horas = ChronoUnit.HOURS.between(this.getDataCriacaoReserva(), LocalDateTime.now());

		// Até 48 horas o reembolso é completo, caso intervalo de
		if (horas < 48 && horas > 0) {
			this.reembolso = new Reembolso(null, this.getValorTotal(), false, null, this);
		}

		// Após 48 horas o reembolso é 50% sem o valor da taxa de serviço
		if (horas >= 48) {
			this.reembolso = new Reembolso(null, this.getValorTotal().multiply(new BigDecimal("0.5"))
					.subtract(this.getTaxaServico()).setScale(2, RoundingMode.HALF_UP), false, null, this);
		}

		this.reservaCancelada = true;
		this.dataCancelamento = LocalDateTime.now();
	}

	/**
	 * Aprova a reserva conforme definição do anfitrião
	 */
	public void aprova() {
		if (this.isReservaCancelada()) {
			throw new ImpossibilidadeConfirmarException();
		}

		if (this.reservaConfirmada) {
			throw new ImpossibilidadeConfirmarException();
		}

		this.dataConfirmacaoAnfitriao = LocalDateTime.now();
		this.reservaConfirmada = true;
	}

	public void adicionaAvaliacao(Avaliacao avaliacao) {
		if (this.getAvaliacao() != null) {
			throw new NaoPodeAvaliarException();
		}

		this.avaliacao = avaliacao;
	}

	public void adicionaHorarioCheckInOut(LocalTime horarioCheckIn, LocalTime horarioCheckOut) {
		this.inicioReserva = this.inicioReserva.with(horarioCheckIn);
		this.fimReserva = this.fimReserva.with(horarioCheckOut);
	}

}
