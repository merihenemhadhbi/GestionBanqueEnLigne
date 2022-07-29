package tn.esprit.banque.service;

public interface MailService {
	void envoyer(String to, String sujet, String contenu);
}
