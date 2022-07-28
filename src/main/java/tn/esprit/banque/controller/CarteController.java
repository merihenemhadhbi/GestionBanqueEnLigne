package tn.esprit.banque.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.banque.service.compte.CompteContrat;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.service.CarteService;

@Controller
public class CarteController {
@Autowired
private CarteService CarteService; 
@Autowired
private CompteContrat compteContrat; 
@Autowired
private CompteRepository CompteRepo;
 @PostMapping(value = "/addCard/{idCompte}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> addCard(@RequestBody Carte Carte, @PathVariable("idCompte") Long idCompte) {
		try {
			Compte Compte = CompteRepo.findById(idCompte).get();
			Carte.setCompte(Compte);
			List<Carte> CardList = Compte.getCartetList();
			CardList.add(Carte);
			Carte carte = CarteService.addCarte(Carte);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(carte);
			

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex);
		}
	}
 @PutMapping(value = "/updateCarte")
	public ResponseEntity updateCarte(@RequestBody Carte Carte) {
		Carte carte = null;
		Compte Compte = null;
		try {
			Compte = CarteService.findCarteById(Carte.getNum_carte()).getCompte();
			Carte.setCompte(Compte);
			carte = CarteService.updateCarte(Carte);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(carte);
	}

	@DeleteMapping(value = "/deleteCarte/{id}")
	public ResponseEntity deleteCarte(@PathVariable Long id) {
		try {
			CarteService.deleteCarte(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Card deleted");
	}

	@GetMapping(value = "/findAllCards")
	public ResponseEntity findAllExistsCards() {
		List<Carte> CardList = new ArrayList<>();
		try {
			CardList = CarteService.findAllCartes();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(CardList);
	}

	@GetMapping(value = "/findAllCardsByAccount/{idCompte}")
	public ResponseEntity findAllCards(@PathVariable Long idCompte) {
		List<Carte> CardList = new ArrayList<>();
		Compte Compte = null;
		try {
			Compte = compteContrat.findLeCompte(idCompte);
			CardList = CarteService.findCarteByCompte(Compte);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(CardList);
	}

	@GetMapping(value = "/findCarte/{id}")
	public ResponseEntity findCard(@PathVariable Long id) {
		Carte Carte = null;
		try {
			Carte = CarteService.findCarteById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(Carte);
	}
}
