package br.com.airbnb.domain.acomodacao.reservas.pagamento;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.pagamento.exception.PgtJaProcessadoException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Long id;
	
	@Setter
	protected TipoPagamento tipoPagamento;

	protected boolean processado;

	protected LocalDateTime dataProcessamento;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "reserva_id", referencedColumnName = "id", nullable = false)
	protected Reserva reserva;
	
	public void processaPagamento() {
		if (this.processado) {
			throw new PgtJaProcessadoException();
		}

		this.processado = true;
		this.dataProcessamento = LocalDateTime.now();
	}

}
