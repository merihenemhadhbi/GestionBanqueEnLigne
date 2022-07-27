package tn.esprit.banque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tn.esprit.banque.model.Commentaire;
import tn.esprit.banque.model.Post;
import tn.esprit.banque.service.CompteService;
import tn.esprit.banque.service.OperationService;

@Controller
public class OperationController {
	@Autowired
	CompteService oarteService;
	OperationService operationService;
	 
		
}
