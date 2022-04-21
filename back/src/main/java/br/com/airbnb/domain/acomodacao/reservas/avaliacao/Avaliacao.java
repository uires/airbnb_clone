package br.com.airbnb.domain.acomodacao.reservas.avaliacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import br.com.airbnb.domain.acomodacao.reservas.Reserva;

@Entity
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(precision = 1, scale = 1)
	private Double notaLimpeza;

	@Column(precision = 1, scale = 1)
	private Double notaComunicacao;

	@Column(precision = 1, scale = 1)
	private Double notaCheckIn;

	@Column(precision = 1, scale = 1)
	private Double notaExatidaoDoAnuncio;

	@Column(precision = 1, scale = 1)
	private Double notaLocalizacao;

	@Column(precision = 1, scale = 1)
	private Double notaCustoBenefico;

	@Lob
	private String comentario;

	@ManyToOne
	private Reserva reserva;
	
	public Avaliacao() { }

	public Long getId() {
		return id;
	}

	public Double getNotaLimpeza() {
		return notaLimpeza;
	}

	public Double getNotaComunicacao() {
		return notaComunicacao;
	}

	public Double getNotaCheckIn() {
		return notaCheckIn;
	}

	public Double getNotaExatidaoDoAnuncio() {
		return notaExatidaoDoAnuncio;
	}

	public Double getNotaLocalizacao() {
		return notaLocalizacao;
	}

	public Double getNotaCustoBenefico() {
		return notaCustoBenefico;
	}

	public String getComentario() {
		return comentario;
	}

	public Reserva getReserva() {
		return reserva;
	}

}
