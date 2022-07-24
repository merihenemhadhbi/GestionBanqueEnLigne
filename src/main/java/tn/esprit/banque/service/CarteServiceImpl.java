package tn.esprit.banque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.repository.CarteRepository;
@Service
public class CarteServiceImpl implements CarteService {
	@Autowired
	private CarteRepository CarteRepository;

	@Override
	public Carte addCarte(Carte Carte) {
		return CarteRepository.save(Carte);

	}

	@Override
	public Carte updateCarte(Carte Carte) {
		return CarteRepository.save(Carte);

	}

	@Override
	public void deleteCarte(Long id) {
CarteRepository.deleteById(id);		
	}

	@Override
	public List<Carte> findAllCartes() {
		return  (List<Carte>)CarteRepository.findAll();
	}

	@Override
	public Carte findCarteById(Long id) {
		return CarteRepository.findById(id).get();
	}

	@Override
	public List<Carte> findCarteByCompte(Compte compte) {
		return CarteRepository.findByCompte(compte);
	}

}
