package br.com.airbnb.service.schedule.reembolso;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.reservas.Reembolso;
import br.com.airbnb.domain.email.Email;
import br.com.airbnb.repository.ReembolsoRepository;
import br.com.airbnb.service.email.EmailService;

@Service
public class ProcessaReembolsoService {

	@Autowired
	private ReembolsoRepository reembolsoRepository;

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 0 0/2 1/1 * ?")
	@Transactional
	public void processaReembolso() {
		List<Reembolso> reembolsos = this.reembolsoRepository.findByProcessadoFalse();
		reembolsos.stream().forEach(reembolso -> {
			reembolso.processa();
			try {
				this.emailService.sendHtmlMessage(this.montaEmailReembolso(reembolso));
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Monta a estrutura do e-mail (processamento de variáveis do thymeleaf,
	 * subject, etc)
	 * 
	 * @param reembolso
	 * @return
	 */
	private Email montaEmailReembolso(Reembolso reembolso) {
		HashMap<String, Object> valores = new LinkedHashMap<String, Object>();
		valores.put("reembolso", reembolso);
		return new Email("O reembolso da reserva foi realizado com sucesso",
				reembolso.getReserva().getHospede().getEmail(), "processamento-reembolso.html", valores);
	}

}
