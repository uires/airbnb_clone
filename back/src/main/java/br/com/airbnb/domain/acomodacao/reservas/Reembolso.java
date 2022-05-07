package br.com.airbnb.domain.acomodacao.reservas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reembolso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	private BigDecimal valor;

	@Getter
	private boolean processado;

	@Getter
	private LocalDateTime dataProcessamento;

	@OneToOne(mappedBy = "reembolso")
	@Getter
	private Reserva reserva;

	public void processa() {
		this.processado = true;
		this.dataProcessamento = LocalDateTime.now();
	}

}
