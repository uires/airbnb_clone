package br.com.airbnb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.airbnb.controller.dto.MunicioDTO;
import br.com.airbnb.repository.MunicipioRepository;

@RestController
@RequestMapping("/municipio")
public class MunicipioController {

	@Autowired
	private MunicipioRepository repository;

	@GetMapping("/consulta-municipio")
	public ResponseEntity<List<MunicioDTO>> listaMunicipios(
			@RequestParam(required = true, name = "abreviacao") String abreviacao) {

		List<MunicioDTO> municipios = MunicioDTO.fromDomain(this.repository.findByNomeLike("%" + abreviacao + "%"));
		return ResponseEntity.ok(municipios);
	}
	
}
