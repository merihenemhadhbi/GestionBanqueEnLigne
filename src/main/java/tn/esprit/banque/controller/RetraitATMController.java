package tn.esprit.banque.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tn.esprit.banque.model.RetraitATM;
import tn.esprit.banque.BanqueEnLigneApplication;
import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.repository.CarteRepository;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.service.CarteService;
import tn.esprit.banque.service.RetraitATMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Controller
@EnableScheduling
public class RetraitATMController {
@Autowired
private RetraitATMService RetraitATMService; 
@Autowired
private CarteService CarteService; 
@Autowired
private CarteRepository CarteRepo;
@Autowired
private CompteRepository CompteRepo;
 @PostMapping(value = "/newWithdrawal/{idCarte}", produces = "application/json", consumes = "application/json")
	@Secured({"ROLE_PHYSIQUE","ROLE_MORALE"})	
 	public ResponseEntity<Object> addCard(@RequestBody RetraitATM RetraitATM, @PathVariable("idCarte") Long idCarte) {
	 try {  Long newPlafond = null;
			Carte Carte = CarteRepo.findById(idCarte).get();
			RetraitATM.setCarte(Carte);
			if(Carte.getcarte_Plafond() != null) {
				newPlafond = Carte.getcarte_Plafond()+RetraitATM.getMontant();
			}
			else {
				newPlafond=RetraitATM.getMontant();

			}
			BigDecimal newsolde = null;
			BigDecimal montant = new BigDecimal(RetraitATM.getMontant());
			
			if((Carte.getCompte().getSoldeCompte()).compareTo(montant) == 1 ) {

			   if(newPlafond < 1000) {
					
				
				RetraitATM retraitATM = RetraitATMService.addRetraitATM(RetraitATM);	
			    Carte.setcarte_Plafond(newPlafond);
			    CarteService.updateCarte(Carte);
			    newsolde = (Carte.getCompte().getSoldeCompte()).subtract(montant);
			    Compte compte = Carte.getCompte();
			    compte.setSoldeCompte(newsolde);
				CompteRepo.save(compte);
				return ResponseEntity.status(HttpStatus.CREATED).body(retraitATM);
				}
				else {
					return ResponseEntity.badRequest().body("ceiling is reached for this week, you still have "+(1000-Carte.getcarte_Plafond())+" DT");
			         }
			}
			else {
				return ResponseEntity.badRequest().body("Account Ballance is lower than the requested amount. your ballance is: "+Carte.getCompte().getSoldeCompte()+" DT");
                 }
			
			

		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body("Card not found");
		}
	}
 @PutMapping(value = "/updateRetraitATM")
	@Secured({"ROLE_PHYSIQUE","ROLE_MORALE"})
 	public ResponseEntity updateRetraitATM(@RequestBody RetraitATM RetraitATM) {
		RetraitATM retraitATM = null;
		Carte Carte = null;
		try {
			Carte = RetraitATMService.findRetraitATMById(RetraitATM.getid_retrait()).getCarte();
			RetraitATM.setCarte(Carte);
			RetraitATM = RetraitATMService.updateRetraitATM(RetraitATM);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(RetraitATM);
	}

	@DeleteMapping(value = "/deleteRetraitATM/{id}")
	@Secured({"ROLE_PHYSIQUE","ROLE_MORALE"})	
	public ResponseEntity deleteRetraitATM(@PathVariable Long id) {
		try {
			RetraitATMService.deleteRetraitATM(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Card deleted");
	}

	@GetMapping(value = "/findAllRetraitATMs")
	@Secured({"ROLE_PHYSIQUE","ROLE_MORALE"})	
	public ResponseEntity findAllExistsRetraitATM() {
		List<RetraitATM> RetraitList = new ArrayList<>();
		try {
			RetraitList = RetraitATMService.findAllRetraitATMs();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(RetraitList);
	}

	@GetMapping(value = "/findAllRetraitATMsByCard/{idCarte}")
	@Secured({"ROLE_PHYSIQUE","ROLE_MORALE"})	
	public ResponseEntity findAllCards(@PathVariable Long idCarte) {
		List<RetraitATM> CardList = new ArrayList<>();
		Carte Carte = null;
		try {
			Carte = CarteService.findCarteById(idCarte);
			CardList = RetraitATMService.findRetraitATMByCarte(Carte);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(CardList);
	}

	@GetMapping(value = "/findRetraitATM/{id}")
	@Secured({"ROLE_PHYSIQUE","ROLE_MORALE"})	
	public ResponseEntity findCard(@PathVariable Long id) {
		RetraitATM RetraitATM = null;
		try {
			RetraitATM = RetraitATMService.findRetraitATMById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(RetraitATM);
	}
	Logger log = LoggerFactory.getLogger(BanqueEnLigneApplication.class);

	@Scheduled(cron = "0 40 23 * * WED")
	public void clearCardCeiling() {
		log.info("The time is now {}", new Date());
		List<Carte> CardList = CarteService.findAllCartes();
		for (Carte carte : CardList) {
			Compte Compte = null;
			Compte = CarteService.findCarteById(carte.getNum_carte()).getCompte();
			carte.setCompte(Compte);
			carte.setcarte_Plafond(140L);
			CarteService.updateCarte(carte);
			}
		
	}
}
