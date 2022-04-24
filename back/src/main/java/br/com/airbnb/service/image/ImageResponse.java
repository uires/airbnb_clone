package br.com.airbnb.service.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ImageResponse {
	private String id;
	private String deletehash;
	private String link;
}
