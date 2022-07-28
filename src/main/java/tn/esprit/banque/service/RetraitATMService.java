package tn.esprit.banque.service;

import java.util.List;

import tn.esprit.banque.model.RetraitATM;
import tn.esprit.banque.model.Carte;

public interface RetraitATMService {
	RetraitATM addRetraitATM(RetraitATM RetraitATM);

	RetraitATM updateRetraitATM(RetraitATM RetraitATM);

	void deleteRetraitATM(Long id);

	List<RetraitATM> findAllRetraitATMs();

	RetraitATM findRetraitATMById(Long id);
	
	List<RetraitATM> findRetraitATMByCarte(Carte carte);
}
