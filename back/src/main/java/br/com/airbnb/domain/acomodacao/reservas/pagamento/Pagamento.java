package br.com.airbnb.domain.acomodacao.reservas.pagamento;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.exception.PgtJaProcessadoException;
import br.com.airbnb.domain.usuario.pagamento.Cartao;
import lombok.Getter;

@Entity
@Getter
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private TipoPagamento tipoPagamento;

	private boolean processado;

	private LocalDateTime dataProcessamento;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "cartao_id", referencedColumnName = "id", nullable = true)
	private Cartao cartao;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "reserva_id", referencedColumnName = "id", nullable = false)
	private Reserva reserva;

	public void processaPagamento() {
		if (this.processado) {
			throw new PgtJaProcessadoException();
		}

		this.processado = true;
		this.dataProcessamento = LocalDateTime.now();
	}

}
