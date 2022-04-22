package br.com.airbnb.domain.acomodacao;

import java.awt.Point;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelAdicionaReserva90DiasAFrenteException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarMaisQueDoisDestaquesException;
import br.com.airbnb.domain.acomodacao.exception.QuantidadesDeHospedesNaoBateComAcomodacaoException;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.domain.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Acomodacao {
	@Id
	@GeneratedValue()
	@Getter
	private Long id;

	@Enumerated(EnumType.STRING)
	@Getter
	private TipoLugar tipoLugar;

	@Column(name = "localizacao", columnDefinition = "POINT")
	@Getter
	private Point localizacao;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	@Getter
	private Endereco endereco;

	@Embedded
	@Getter
	private Hospedes hopedes;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Espaco> espacos;

	@Embedded
	@Column(nullable = false)
	@Getter
	private PrecoPernoite precoPernoite;

	@Column(nullable = false)
	@Getter
	private BigDecimal taxaDeServico;

	@Column(nullable = false)
	private BigDecimal taxaDeLimpeza;

	@Column(length = 500, nullable = false)
	private String descricao;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Destaque> destaques;

	@Column(length = 50, nullable = false)
	@Getter
	private String titulo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Foto> fotos;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	@Getter
	private Usuario usuario;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "acomodacao")
	private List<Reserva> reservas;

	@Getter
	private LocalTime horarioCheckIn;

	@Getter
	private LocalTime horarioCheckOut;

	@OneToMany(mappedBy = "acomodacao", fetch = FetchType.LAZY)
	private List<Avaliacao> avaliacoes;

	public Acomodacao(Long id, TipoLugar tipoLugar, Point localizacao, Endereco endereco, Hospedes hopedes,
			List<Espaco> espacos, PrecoPernoite precoPernoite, BigDecimal taxaDeServico, BigDecimal taxaDeLimpeza,
			String descricao, List<Destaque> destaques, String titulo, List<Foto> fotos, Usuario usuario,
			List<Reserva> reservas, LocalTime horarioCheckIn, LocalTime horarioCheckOut, List<Avaliacao> avaliacoes) {
		if (destaques.size() > 2) {
			throw new NaoEhPossivelCadastrarMaisQueDoisDestaquesException();
		}
		this.id = id;
		this.tipoLugar = tipoLugar;
		this.localizacao = localizacao;
		this.endereco = endereco;
		this.hopedes = hopedes;
		this.espacos = espacos;
		this.precoPernoite = precoPernoite;
		this.taxaDeServico = taxaDeServico;
		this.taxaDeLimpeza = taxaDeLimpeza;
		this.descricao = descricao;
		this.destaques = destaques;
		this.titulo = titulo;
		this.fotos = fotos;
		this.usuario = usuario;
		this.reservas = reservas;
		this.horarioCheckIn = horarioCheckIn;
		this.horarioCheckOut = horarioCheckOut;
		this.avaliacoes = avaliacoes;
	}

	public List<Destaque> getDestaques() {
		return new ArrayList<Destaque>(destaques);
	}

	public List<Espaco> getEspacos() {
		return new ArrayList<Espaco>(espacos);
	}

	public List<Foto> getFotos() {
		return new ArrayList<Foto>(fotos);
	}

	public List<Reserva> getReservas() {
		return new ArrayList<Reserva>(reservas);
	}

	public void adicionaReserva(Reserva reserva) {
		if (this.verificaSeDataEhMaiorQueNoventaDias(reserva.getInicioReserva())) {
			throw new NaoEhPossivelAdicionaReserva90DiasAFrenteException();
		}

		if (reserva.getQuantidadeHospedes() >= this.getHopedes().getHospedes()) {
			throw new QuantidadesDeHospedesNaoBateComAcomodacaoException();
		}

		this.reservas.add(reserva);
	}

	/**
	 * Verifica se a data da reserva é maior incrementando em 90 dias a data atual
	 * 
	 * @param data
	 * @return Retorna o resultado da verificação
	 */
	private boolean verificaSeDataEhMaiorQueNoventaDias(LocalDateTime data) {
		return data.isAfter(LocalDateTime.now().plusDays(90));
	}

}
