package br.com.airbnb.service.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

@Service
public class ImageService {

	@Value("${imgur.api.client-id}")
	private String clientId;

	@Value("${imgur.api.url}")
	private String url;

	public List<ImageResponse> upload(MultipartFile[] fotos) {
		List<ImageResponse> listaFotos = new ArrayList<>();

		Arrays.asList(fotos).stream().forEach(foto -> {
			ImageResponse imageResponse = this.uploadImage(foto);
			listaFotos.add(imageResponse);
		});

		return listaFotos;
	}

	private ImageResponse uploadImage(MultipartFile foto) {
		ImageResponse imageResponse = null;
		try {
			String json = Unirest.post(this.url).header("Authorization", "Client-ID " + this.clientId)
					.multiPartContent()
					.field("image", Base64.getEncoder().withoutPadding().encodeToString(foto.getBytes())).asString()
					.getBody();
			JSONObject data = new JSONObject(json);

			imageResponse = new ImageResponse(data.getJSONObject("data").getString("id"),
					data.getJSONObject("data").getString("deletehash"), data.getJSONObject("data").getString("link"));

			return imageResponse;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageResponse;
	}
}
