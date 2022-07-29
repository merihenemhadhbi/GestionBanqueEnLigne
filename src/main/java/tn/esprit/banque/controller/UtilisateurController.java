package tn.esprit.banque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tn.esprit.banque.model.Employee;
import tn.esprit.banque.model.Morale;
import tn.esprit.banque.model.Physique;
import tn.esprit.banque.service.UtilisateurService;

@Controller
public class UtilisateurController {

	@Autowired
	UtilisateurService userService;
	
	@PostMapping(value = "/addPhysique", produces = "application/json", consumes = "application/json")
	public ResponseEntity createPhysique(@RequestBody Physique user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUtilisateur(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/addMorale", produces = "application/json", consumes = "application/json")
	public ResponseEntity createMorale(@RequestBody Morale user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUtilisateur(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/addAgent", produces = "application/json", consumes = "application/json")
	public ResponseEntity createEmployee(@RequestBody Employee user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUtilisateur(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/updatePhysique", produces = "application/json", consumes = "application/json")
	public ResponseEntity updatePhysique(@RequestBody Physique user) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUtilisateur(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/updateMorale", produces = "application/json", consumes = "application/json")
	public ResponseEntity updateMorale(@RequestBody Morale user) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUtilisateur(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/updateAgent", produces = "application/json", consumes = "application/json")
	public ResponseEntity updateEmployee(@RequestBody Employee user) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUtilisateur(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@DeleteMapping(value = "/deleteUtilisateur/{username}")
	public ResponseEntity deleteUtilisateur(@PathVariable("username") String username) {
		try {
			userService.deleteUtilisateur(username);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("User has been deleted");

	}
}
