package tn.esprit.banque.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Credits;
import tn.esprit.banque.model.Credits.TypeCredit;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.repository.CreditRepository;
import tn.esprit.banque.service.credit.CreditAbstractionService;
import tn.esprit.banque.service.credit.CreditConsommationService;
import tn.esprit.banque.service.credit.CreditImmobilierService;
import tn.esprit.banque.service.credit.FabriqueCreditService;

@RestController
public class CreditController {
	@Autowired
	private FabriqueCreditService fabriqueCreditService;
	@Autowired
	private CompteRepository compteRepository;

	@GetMapping("/retrieve-all-credits")
	public ResponseEntity retrieveAllCredits() {
		List<Credits> listCredit = new ArrayList<>();
		try {
			listCredit = fabriqueCreditService.allCredits();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(listCredit);

	}

	@PostMapping("/add-credit")
	public ResponseEntity createCredit(@RequestBody Credits c, @RequestParam Long idCompte) {
		Credits postcredit = null;
		CreditAbstractionService creditAbstractionService = null;
		try {
			creditAbstractionService = fabriqueCreditService.generateCredit(c.getTypeCredit());
			Compte compte = compteRepository.findById(idCompte).get();
			switch (c.getTypeCredit()) {
			case IMMOBILIER:
				CreditImmobilierService creditImmobilierService = (CreditImmobilierService) creditAbstractionService;
				postcredit = creditImmobilierService.createCredit(c, compte);
				break;
			case CONSOMMATION:
				CreditConsommationService creditConsommationService = (CreditConsommationService) creditAbstractionService;
				postcredit = creditConsommationService.createCredit(c, compte);
				break;
			default:
				throw new InvalidSwitchCaseException("Veuillez choisir un type de credit valide");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(postcredit);
	}

	@GetMapping("/get-credit/{credit-id}")
	public ResponseEntity retrieveCredit(@PathVariable("credit-id") Long id) {
		Credits credit = null;
		try {
			credit = fabriqueCreditService.findUnCredit(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(credit);
	}


	@PutMapping("/affectercredit")
	public ResponseEntity Affectercredit(@RequestParam Long idCredit, @RequestParam Long idCompte) {
		Credits postcredit = null;
		 Credits credit = fabriqueCreditService.findUnCredit(idCredit);
		 Compte compte = compteRepository.findById(idCompte).get();
		CreditAbstractionService creditAbstractionService = null;
		try {
			creditAbstractionService = fabriqueCreditService.generateCredit(credit.getTypeCredit());
			switch (credit.getTypeCredit()) {
			case IMMOBILIER:
				CreditImmobilierService creditImmobilierService = (CreditImmobilierService) creditAbstractionService;
				postcredit = creditImmobilierService.affectercredit(credit,compte);
				break;
			case CONSOMMATION:
				CreditConsommationService creditConsommationService = (CreditConsommationService) creditAbstractionService;
				postcredit = creditConsommationService.affectercredit(credit,compte);
				break;
			default:
				throw new InvalidSwitchCaseException("Veuillez choisir un type de credit valide");
			}
	} catch (Exception ex) {
	ex.printStackTrace();
	return ResponseEntity.badRequest().body(ex.getMessage());
	}
		return ResponseEntity.status(HttpStatus.OK).body(credit);

}

	@PostMapping("/calculcreditdeux/{compte-id}")
	public ResponseEntity calculCreditdeux(@RequestBody Credits credit, @PathVariable("compte-id") Long idCompte) {

		Credits postcredit = null;
		CreditAbstractionService creditAbstractionService = null;

		try {
			creditAbstractionService = fabriqueCreditService.generateCredit(credit.getTypeCredit());
			switch (credit.getTypeCredit()) {
			case IMMOBILIER:
				CreditImmobilierService creditImmobilierService = (CreditImmobilierService) creditAbstractionService;
				postcredit = creditImmobilierService.Createothercredit(credit, idCompte);
				break;
			case CONSOMMATION:
				CreditConsommationService creditConsommationService = (CreditConsommationService) creditAbstractionService;
				postcredit = creditConsommationService.Createothercredit(credit, idCompte);
				break;
			default:
				throw new InvalidSwitchCaseException("Veuillez choisir un type de credit valide");
		} 
			}catch (Exception ex) {
		ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(credit);
	}
}
