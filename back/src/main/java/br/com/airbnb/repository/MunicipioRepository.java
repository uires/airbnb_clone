package br.com.airbnb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.airbnb.domain.municipio.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
	List<Municipio> findByNomeLike(String likePattern);
}
