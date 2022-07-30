package tn.esprit.banque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tn.esprit.banque.model.Employee;
import tn.esprit.banque.model.Morale;
import tn.esprit.banque.model.Physique;
import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.service.UtilisateurService;

@Controller
@RequestMapping("utilisateur")
public class UtilisateurController {

	@Autowired
	UtilisateurService userService;
	
	@PostMapping(value = "/addPhysique", produces = "application/json", consumes = "application/json")
	public ResponseEntity createPhysique(@RequestBody Physique user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUtilisateur(user));
		}catch(NameAlreadyBoundException ex) {
			return ResponseEntity.badRequest().body("user already existed");
		} 
		catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/addMorale", produces = "application/json", consumes = "application/json")
	public ResponseEntity createMorale(@RequestBody Morale user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUtilisateur(user));
		}catch(NameAlreadyBoundException ex) {
			return ResponseEntity.badRequest().body("user already existed");
		} 
		catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/addAgent", produces = "application/json", consumes = "application/json")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity createEmployee(@RequestBody Employee user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUtilisateur(user));
		}catch(NameAlreadyBoundException ex) {
			return ResponseEntity.badRequest().body("user already existed");
		} 
		catch (Exception ex) {
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
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity updateEmployee(@RequestBody Employee user) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUtilisateur(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@DeleteMapping(value = "/deleteUtilisateur/{username}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity deleteUtilisateur(@PathVariable("username") String username) {
		try {
			userService.deleteUtilisateur(username);
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("User has been deleted");

	}
	
	@GetMapping(value = "/getuser/{username}", produces = "application/json")
	public ResponseEntity updateMorale(@PathVariable("username") String username) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.findUtilisateurById(username));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@GetMapping(value = "/getallusers", produces = "application/json")
	@Secured({"ROLE_ADMIN","ROLE_AGENT"})
	public ResponseEntity getAllUsers() {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.findAllUtilisateur());
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@GetMapping(value = "/getallenabledusers", produces = "application/json")
	@Secured({"ROLE_ADMIN","ROLE_AGENT"})
	public ResponseEntity getAllEnabledUsers() {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.findAllEnabledUtilisateur());
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@GetMapping(value = "/getalldisabledusers", produces = "application/json")
	@Secured({"ROLE_ADMIN","ROLE_AGENT"})
	public ResponseEntity getAllDisabledUsers() {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.findAllDisabledUtilisateur());
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/validate/{username}", produces = "application/json")
	@Secured({"ROLE_ADMIN","ROLE_AGENT"})
	public ResponseEntity validate(@PathVariable("username") String username) {
		try {
			Utilisateur user = userService.findUtilisateurById(username);
			if(user.isEnabled()) {
				throw new RuntimeException("user already enabled");
			}
			user.setEnabled(true);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.triggerValidate(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
	
	@PostMapping(value = "/disable/{username}", produces = "application/json")
	public ResponseEntity disable(@PathVariable("username") String username) {
		try {
			Utilisateur user = userService.findUtilisateurById(username);
			if(!user.isEnabled()) {
				throw new RuntimeException("user already disabled");
			}
			user.setEnabled(false);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.triggerValidate(user));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}
}
