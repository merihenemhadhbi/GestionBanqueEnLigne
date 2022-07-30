package tn.esprit.banque.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.repository.LdapUtilisateurRepository;

@RestController
@RequestMapping("users")
public class LdapController {
    @Autowired
    private LdapUtilisateurRepository ldapUtilisateurRepo;
     
    @GetMapping("/get-user-names")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<String>> getLdapUserNames() {
        return new ResponseEntity<>(ldapUtilisateurRepo.getAllUtilisateurNames(), HttpStatus.OK);
    }
    @GetMapping("/get-users")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<List<Utilisateur>> getLdapUsers() {
        return new ResponseEntity<>(ldapUtilisateurRepo.getAllUtilisateurs(), HttpStatus.OK);
    }
    @GetMapping("/get-user")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Utilisateur> findLdapPerson(@RequestParam(name = "user-id") String userId) {
        return new ResponseEntity<>(ldapUtilisateurRepo.getUtilisateurNamesByUid(userId), HttpStatus.OK);
    }
}
