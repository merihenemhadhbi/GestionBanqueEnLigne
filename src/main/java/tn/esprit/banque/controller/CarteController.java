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

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.service.CarteService;
import tn.esprit.banque.service.CompteService;

@Controller
public class CarteController {
@Autowired
private CarteService CarteService; 
 private CompteService CompteService; 
 @PostMapping(value = "/addCard/{idCompte}")
	public ResponseEntity addCard(@RequestBody Carte Carte, @PathVariable Long idCompte) {
		Carte carte = null;
		Compte Compte = null;
		try {
			Compte = CompteService.findCompteById(idCompte);
			Carte.setCompte(Compte);
			carte = CarteService.addCarte(Carte);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body("Compte not found");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(carte);
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

	@GetMapping(value = "/findAllCardsByTopic/{idCompte}")
	public ResponseEntity findAllCards(@PathVariable Long idCompte) {
		List<Carte> CardList = new ArrayList<>();
		Compte Compte = null;
		try {
			Compte = CompteService.findCompteById(idCompte);
			CardList = Compte.getCartetList();
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
