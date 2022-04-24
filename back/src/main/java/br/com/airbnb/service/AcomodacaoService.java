package br.com.airbnb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Foto;
import br.com.airbnb.repository.AcomodacaoRepository;
import br.com.airbnb.service.image.ImageResponse;
import br.com.airbnb.service.image.ImageService;

@Service
public class AcomodacaoService {

	@Autowired
	private AcomodacaoRepository repository;

	@Autowired
	private ImageService imageService;

	public Acomodacao cadastraAcomodacao(Acomodacao acomodacao) {
		return this.repository.save(acomodacao);
	}

	@Transactional
	public Acomodacao cadastraFotos(Long id, MultipartFile[] fotos) {
		Optional<Acomodacao> acomodacaoOptional = this.repository.findById(id);

		if (!acomodacaoOptional.isPresent()) {
			throw new IllegalArgumentException();
		}

		Acomodacao acomodacao = acomodacaoOptional.get();

		List<ImageResponse> listaImagens = this.imageService.upload(fotos);
		List<Foto> listaFotos = listaImagens.stream()
				.map(imagem -> new Foto(null, imagem.getLink(), acomodacaoOptional.get(), imagem.getDeletehash()))
				.collect(Collectors.toList());

		acomodacao.adicionaImagens(listaFotos);

		return acomodacao;
	}
}
