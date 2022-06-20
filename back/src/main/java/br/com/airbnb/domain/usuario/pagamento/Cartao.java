package br.com.airbnb.domain.usuario.pagamento;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.airbnb.domain.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Column(nullable = false, length = 16)
	private String numero;

	@NonNull
	private String nomeImpresso;

	@NonNull
	private LocalDate dataExpiracao;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	@JsonBackReference
	private Usuario usuario;

	public Bandeira getBandeira() {
		System.out.println(this.numero);
		return switch (this.numero.substring(0, 1)) {
		case "3" -> Bandeira.AMERICAN_EXPRESS;
		case "4" -> Bandeira.VISA;
		case "5" -> Bandeira.MASTERCARD;
		case "6" -> Bandeira.ELO;
		default -> Bandeira.INDEFINIDO;
		};
	}

	public void associaUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
