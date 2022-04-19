package br.com.airbnb.domain.acomodacao;

public class Localizacao {
	private String latitude;
	private String longitude;

	public Localizacao() {
	}

	public Localizacao(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

}
