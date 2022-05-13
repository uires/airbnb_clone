package br.com.airbnb.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.airbnb.controller.form.ConsultaForm;
import br.com.airbnb.domain.acomodacao.Acomodacao;
import br.com.airbnb.domain.acomodacao.Foto;
import br.com.airbnb.repository.AcomodacaoRepository;
import br.com.airbnb.repository.specification.AcomodacaoSpecification;
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

		Arrays.asList(fotos).stream().forEach(foto -> {
			if ((foto.getSize() / 1024.00) > 500) {
				throw new IllegalArgumentException("Arquivo maior do que 500kB");
			}

			String extension = StringUtils.getFilenameExtension(foto.getOriginalFilename());
			if (extension != "png" || extension != "jpg" || extension != "jpeg" || extension != "webp") {
				throw new IllegalArgumentException("A extensão do arquivo é inválida");
			}
		});

		Optional<Acomodacao> acomodacaoOptional = this.repository.findById(id);

		if (!acomodacaoOptional.isPresent()) {
			throw new IllegalArgumentException();
		}

		Acomodacao acomodacao = acomodacaoOptional.get();

		List<ImageResponse> listaImagens = this.imageService.uploadMany(fotos);
		List<Foto> listaFotos = listaImagens.stream()
				.map(imagem -> new Foto(null, imagem.getLink(), acomodacaoOptional.get(), imagem.getDeletehash()))
				.collect(Collectors.toList());

		acomodacao.adicionaImagens(listaFotos);

		return acomodacao;
	}

	public Optional<Acomodacao> busca(Long id) {
		Optional<Acomodacao> acomodacao = this.repository.findById(id);
		return acomodacao;
	}

	public Page<Acomodacao> consultaAcomodacoes(Pageable pageable, ConsultaForm consultaForm) {
		return this.repository.findAll(
				Specification
						.where(AcomodacaoSpecification.precoNoite(new BigDecimal("30.00"), new BigDecimal("500.00"))),
				pageable);
	}
}
