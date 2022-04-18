package br.com.airbnb.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.airbnb.domain.municipio.Municipio;

public class MunicioDTO {
	
	private Long idMunicipio;
	private String nomeMunicipio;

	public MunicioDTO(Long idMunicipio, String nomeMunicipio) {
		this.idMunicipio = idMunicipio;
		this.nomeMunicipio = nomeMunicipio;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	public static List<MunicioDTO> fromDomain(List<Municipio> municipios) {
		List<MunicioDTO> lista = municipios.stream().map(municipio -> new MunicioDTO(municipio.getId(),
				municipio.getEstado().getUf() + " - " + municipio.getNome())).collect(Collectors.toList());

		return lista;
	}
}
