package br.com.airbnb.service.schedule;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.reservas.Reembolso;
import br.com.airbnb.repository.ReembolsoRepository;

@Service
public class ProcessaReembolsoService {

	@Autowired
	private ReembolsoRepository reembolsoRepository;

	@Scheduled(cron = "0 0/1 * 1/1 * ?")
	@Transactional
	public void processaReembolso() {
		List<Reembolso> reembolsos = this.reembolsoRepository.findByProcessadoFalse();
		reembolsos.stream().forEach(reembolso -> {
			reembolso.processa();
		});
	}

}
