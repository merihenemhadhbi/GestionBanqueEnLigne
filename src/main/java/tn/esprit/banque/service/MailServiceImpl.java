package tn.esprit.banque.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	@Override
	public void envoyer(String to, String sujet, String contenu) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setSubject(sujet);
			mimeMessageHelper.setFrom(new InternetAddress(env.getProperty("spring.mail.username")));
			mimeMessageHelper.setTo(new InternetAddress(to));
			mimeMessageHelper.setText(contenu);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
