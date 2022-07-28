package tn.esprit.banque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.RetraitATM;
import tn.esprit.banque.model.Carte;
import tn.esprit.banque.repository.RetraitATMRepository;

@Service
public class RetraitATMServiceImpl implements RetraitATMService {
	@Autowired
	private RetraitATMRepository RetraitATMRepository;

	@Override
	public RetraitATM addRetraitATM(RetraitATM RetraitATM) {
		return RetraitATMRepository.save(RetraitATM);

	}

	@Override
	public RetraitATM updateRetraitATM(RetraitATM RetraitATM) {
		return RetraitATMRepository.save(RetraitATM);

	}

	@Override
	public void deleteRetraitATM(Long id) {
RetraitATMRepository.deleteById(id);		
	}

	@Override
	public List<RetraitATM> findAllRetraitATMs() {
		return  (List<RetraitATM>)RetraitATMRepository.findAll();
	}

	@Override
	public RetraitATM findRetraitATMById(Long id) {
		return RetraitATMRepository.findById(id).get();
	}

	@Override
	public List<RetraitATM> findRetraitATMByCarte(Carte Carte) {
		return RetraitATMRepository.findByCarte(Carte);
	}

}
