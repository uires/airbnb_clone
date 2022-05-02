package br.com.airbnb.domain.email;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {
	private String assunto;
	private String para;
	private String templeteHtml;
	Map<String, Object> valores;
}
