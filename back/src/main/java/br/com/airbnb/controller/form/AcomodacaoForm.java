package br.com.airbnb.controller.form;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Destaque;
import br.com.airbnb.domain.acomodacao.Endereco;
import br.com.airbnb.domain.acomodacao.Espaco;
import br.com.airbnb.domain.acomodacao.Hospedes;
import br.com.airbnb.domain.acomodacao.Precificacao;
import br.com.airbnb.domain.acomodacao.TipoLugar;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcomodacaoForm {

	@NotNull
	private TipoLugar lugar;

	@NotNull
	private Integer hospedes;

	@NotNull
	private Integer camas;
	@NotNull
	private Integer quartos;
	@NotNull
	private Integer banheiros;
	@NotNull
	private Integer criancas;
	@NotNull
	private Integer bebes;
	@NotNull
	private Integer animais;

	@NotNull
	private BigDecimal precoNoite;

	@NotNull
	private BigDecimal precoMensal;

	@NotNull
	private boolean permiteDescontoTresPrimeirosHospedes;

	@NotNull
	private BigDecimal taxaDeServico;

	@NotNull
	private BigDecimal taxaDeLimpeza;

	private String descricao;

	@NotNull
	private List<Espaco> espacos;

	@NotNull
	private List<Destaque> destaques;

	@NotNull
	private String titulo;

	@NotNull
	private LocalTime horarioCheckIn;

	@NotNull
	private LocalTime horarioCheckOut;

	@NotNull
	private String rua;
	private String estado;
	private String cidade;
	private String codigoPostal;

	private boolean permiteAnimais;

	public Acomodacao converte(UsuarioRepository usuarioRepository) {
		Optional<Usuario> usuario = usuarioRepository.findById(1L);

		if (!usuario.isPresent()) {
			throw new IllegalArgumentException("");
		}

		Endereco endereco = new Endereco(null, rua, estado, cidade, null, codigoPostal);
		
		Hospedes hospedes = new Hospedes(this.hospedes, this.camas, this.quartos, this.banheiros, this.criancas,
				this.bebes, this.animais);

		Precificacao precoPernoite = new Precificacao(precoNoite, precoMensal, permiteDescontoTresPrimeirosHospedes);
		return new Acomodacao(null, lugar, null, endereco, hospedes, espacos, precoPernoite, taxaDeServico,
				taxaDeLimpeza, descricao, destaques, titulo, null, usuario.get(), null, horarioCheckIn, horarioCheckOut,
				null, this.permiteAnimais);
	}

}
