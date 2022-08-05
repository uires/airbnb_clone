package br.com.airbnb.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PagamentoBoletoDTO {
	private String numeroBoleto;
}
