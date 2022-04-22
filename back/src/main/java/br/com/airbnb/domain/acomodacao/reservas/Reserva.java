package br.com.airbnb.domain.acomodacao.reservas;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

	@OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY)
	private List<Avaliacao> avaliacao;

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

	public List<Avaliacao> getAvaliacao() {
		return new ArrayList<Avaliacao>(avaliacao);
	}

}
