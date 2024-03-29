package br.com.airbnb.domain.acomodacao;

import java.awt.Point;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.airbnb.domain.acomodacao.exception.IntervaloDeReservaInvalidoException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelAdicionaReserva90DiasAFrenteException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarMaisQueDoisDestaquesException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelCadastrarUmaReservaNoPassadoException;
import br.com.airbnb.domain.acomodacao.exception.NaoEhPossivelReservaSobreporOutraException;
import br.com.airbnb.domain.acomodacao.exception.QuantidadesDeHospedesNaoBateComAcomodacaoException;
import br.com.airbnb.domain.acomodacao.reservas.Reserva;
import br.com.airbnb.domain.acomodacao.reservas.avaliacao.Avaliacao;
import br.com.airbnb.domain.acomodacao.reservas.desconto.CalculadoraDesconto;
import br.com.airbnb.domain.usuario.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Acomodacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Enumerated(EnumType.STRING)
	@Getter
	@Column(nullable = false)
	private TipoLugar tipoLugar;

	@Column(name = "localizacao", columnDefinition = "POINT", nullable = true)
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
	@Getter
	private Precificacao precificacao;

	@Column(nullable = false)
	@Getter
	private BigDecimal taxaDeServico;

	@Column(nullable = false)
	@Getter
	private BigDecimal taxaDeLimpeza;

	@Column(length = 500, nullable = false)
	@Getter
	private String descricao;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Destaque> destaques;

	@Column(length = 50, nullable = false)
	@Getter
	private String titulo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "acomodacao")
	private List<Foto> fotos = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id")
	@Getter
	private Usuario usuario;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "acomodacao")
	@JsonBackReference
	private List<Reserva> reservas;

	@Getter
	@Column(nullable = false)
	private LocalTime horarioCheckIn;

	@Getter
	@Column(nullable = false)
	private LocalTime horarioCheckOut;

	@OneToMany(mappedBy = "acomodacao", fetch = FetchType.LAZY)
	private List<Avaliacao> avaliacoes;

	@Getter
	private boolean permiteAnimais;

	@Embedded
	@Getter
	private QuantidadesComodos quantidadesComodos;

	@Column(nullable = false)
	private Integer camas;

	public Acomodacao(TipoLugar tipoLugar, Endereco endereco, Hospedes hopedes, List<Espaco> espacos,
			Precificacao precificacao, BigDecimal taxaDeServico, BigDecimal taxaDeLimpeza, String descricao,
			List<Destaque> destaques, String titulo, Usuario usuario, LocalTime horarioCheckIn,
			LocalTime horarioCheckOut, boolean permiteAnimais, QuantidadesComodos quantidadesComodos, Integer camas) {
		if (destaques.size() > 2) {
			throw new NaoEhPossivelCadastrarMaisQueDoisDestaquesException();
		}
		this.tipoLugar = tipoLugar;
		this.endereco = endereco;
		this.hopedes = hopedes;
		this.espacos = espacos;
		this.precificacao = precificacao;
		this.taxaDeServico = taxaDeServico;
		this.taxaDeLimpeza = taxaDeLimpeza;
		this.descricao = descricao;
		this.destaques = destaques;
		this.titulo = titulo;
		this.usuario = usuario;
		this.reservas = new ArrayList<>();
		this.horarioCheckIn = horarioCheckIn;
		this.horarioCheckOut = horarioCheckOut;
		this.avaliacoes = new ArrayList<>();
		this.permiteAnimais = permiteAnimais;
		this.quantidadesComodos = quantidadesComodos;
		this.camas = camas;
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

	/**
	 * Adiciona uma reserva a acomodação
	 * 
	 * @param reserva
	 */
	public void adicionaReserva(Reserva reserva) {
		reserva.adicionaHorarioCheckInOut(this.getHorarioCheckIn(), this.getHorarioCheckOut());

		if (this.verificaSeDataEhMaiorQueNoventaDias(reserva.getInicioReserva())) {
			throw new NaoEhPossivelAdicionaReserva90DiasAFrenteException();
		}

		if (reserva.getHospedes().getAdultos() > this.getHopedes().getAdultos()) {
			throw new QuantidadesDeHospedesNaoBateComAcomodacaoException();
		}

		List<Reserva> listaReserva = this.getReservas().stream().filter(reservaAcomodacao -> {
			return !reservaAcomodacao.isReservaCancelada();
		}).collect(Collectors.toList());

		for (Reserva reservaAcomodacao : listaReserva) {
			if (this.verificaSeAReservaSobrepoemDataOutra(reserva, reservaAcomodacao)) {
				throw new NaoEhPossivelReservaSobreporOutraException();
			}
		}

		if (reserva.getInicioReserva().isBefore(LocalDateTime.now())) {
			throw new NaoEhPossivelCadastrarUmaReservaNoPassadoException();
		}

		if (reserva.getFimReserva().isBefore(reserva.getInicioReserva())) {
			throw new IntervaloDeReservaInvalidoException();
		}

		reserva.calculaTotal();
		// Executa lógica de desconto para reserva
		CalculadoraDesconto calculadoraDesconto = new CalculadoraDesconto();
		reserva = calculadoraDesconto.calculaDesconto(reserva);

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

	/**
	 * Verifica se a data da reserva para ser cadastra sobrepõem a data de alguma
	 * reserva já cadastrada
	 * 
	 * @param reserva
	 * @param reservaCadastrada
	 * @return retorno da verificação
	 */
	private boolean verificaSeAReservaSobrepoemDataOutra(Reserva reserva, Reserva reservaCadastrada) {
		return reserva.getInicioReserva().isBefore(reservaCadastrada.getFimReserva())
				&& reservaCadastrada.getInicioReserva().isBefore(reserva.getFimReserva());
	}

	public void adicionaImagens(List<Foto> fotos) {
		this.fotos.addAll(fotos);
	}

	public void atualizaAcomodacao(Precificacao precificacao) {
		this.precificacao = precificacao;
	}

}
