package br.com.airbnb.controller.form;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.airbnb.domain.usuario.pagamento.Cartao;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartaoForm {

	@NotNull
	@NotBlank
	@NotEmpty
	@Max(value = 16)
	@Min(value = 16)
	private String numero;

	@NotNull
	@NotBlank
	@NotEmpty
	@Max(value = 255)
	private String nomeImpresso;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataExpiracao;

	public Cartao converte() {
		return new Cartao(numero, nomeImpresso.toUpperCase(), dataExpiracao);
	}
}
