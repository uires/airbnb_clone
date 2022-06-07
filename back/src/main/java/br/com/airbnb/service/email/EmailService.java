package br.com.airbnb.service.email;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.com.airbnb.domain.email.Email;

@Service
public class EmailService {
	@Value("${email.noreply}")
	private String de;

	private final JavaMailSender emailSender;
	private final SpringTemplateEngine templateEngine;

	public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
		this.emailSender = emailSender;
		this.templateEngine = templateEngine;
	}

	public void sendHtmlMessage(Email email) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		Context context = new Context();
		context.setVariables(email.getValores());
		helper.setFrom(this.de);
		helper.setTo(email.getPara());
		helper.setSubject("Airbnb - " + email.getAssunto());
		String html = this.templateEngine.process(email.getTempleteHtml(), context);
		helper.setText(html, true);

		this.emailSender.send(message);
	}

}
