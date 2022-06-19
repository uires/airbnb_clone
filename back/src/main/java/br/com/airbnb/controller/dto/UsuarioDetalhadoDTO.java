package br.com.airbnb.controller.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.usuario.Foto;
import br.com.airbnb.domain.usuario.Usuario;
import br.com.airbnb.domain.usuario.pagamento.Cartao;
import lombok.Getter;

public class UsuarioDetalhadoDTO {

	@Getter
	private Long id;

	@Getter
	private String nome;

	@Getter
	private Foto foto;

	@Getter
	private String email;

	@Getter
	private LocalDate dataNascimento;

	@Getter
	private boolean permiteEmailDeMarketing;

	@Getter
	private List<Acomodacao> acomodacoes;

	@Getter
	private List<Cartao> cartoes;

	public UsuarioDetalhadoDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getPrimeiroNome() + " " + usuario.getSegundoNome();
		this.foto = usuario.getFoto();
		this.email = usuario.getEmail();
		this.dataNascimento = usuario.getDataNascimento();
		this.permiteEmailDeMarketing = usuario.isPermiteEmailDeMarketing();
		this.acomodacoes = usuario.getAcomodacoes();
		this.cartoes = usuario.getCartoes();
	}

}
