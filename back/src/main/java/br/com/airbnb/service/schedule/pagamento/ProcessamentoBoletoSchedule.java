package br.com.airbnb.service.schedule.pagamento;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.airbnb.domain.acomodacao.reservas.pagamento.Pagamento;
import br.com.airbnb.repository.PagamentoRepository;

@Service
public class ProcessamentoBoletoSchedule {

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Scheduled(cron = "0 0 * * * ?")
	@Transactional
	public void processaReembolso() {
		List<Pagamento> pagamentos = this.pagamentoRepository.findByProcessadoFalse();

		pagamentos.forEach((pagamento) -> {
			// Imagine uma integração com um gateway ou um hook
			pagamento.processaPagamento();
		});
	}

}
