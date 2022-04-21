package br.com.airbnb.domain.acomodacao.reservas;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.domain.usuario.Usuario;

@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime inicioReserva;
	private LocalDateTime fimReserva;

	private LocalDateTime dataCriacaoReserva;

	private BigDecimal descontoSemanal;
	private BigDecimal valorTotal;

	private boolean reservaCancelada = false;

	private Integer quantidadeHospedes;

	@OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY)
	private List<Avaliacao> avaliacao;

	@OneToOne
	@JoinColumn(name = "hospede_id", referencedColumnName = "id")
	private Usuario hospede;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Acomodacao acomodacao;

	public Reserva() { }

	public Long getId() {
		return id;
	}

	public LocalDateTime getInicioReserva() {
		return inicioReserva;
	}

	public LocalDateTime getFimReserva() {
		return fimReserva;
	}

	public LocalDateTime getDataCriacaoReserva() {
		return dataCriacaoReserva;
	}

	public BigDecimal getDescontoSemanal() {
		return descontoSemanal;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public boolean isReservaCancelada() {
		return reservaCancelada;
	}

	public Integer getQuantidadeHospedes() {
		return quantidadeHospedes;
	}

	public List<Avaliacao> getAvaliacao() {
		return avaliacao;
	}

	public Usuario getHospede() {
		return hospede;
	}

	public Acomodacao getAcomodacao() {
		return acomodacao;
	}
	
}
