package tn.esprit.banque.service.compte;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidUserException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.model.Compte.TypeCompte;
import tn.esprit.banque.repository.CompteRepository;

@Component
public class CompteEpargne extends CompteAbstraction {

	private CompteRepository cptRepo;
    

    /*@Autowired
    public void setUser(Utilisateur user) {
        this.user = user;
    }*/

    @Autowired
    public void setCptDAO(CompteRepository cptRepo) {
        this.cptRepo = cptRepo;
    }

  

    @Transactional
    public Compte createAccount(Compte compte,Long userId) throws InvalidAmountException, InvalidUserException {

        //Utilisateur utilisateur = user.findTheUser(userId);

        compte.setDateCreation(new Date());
        compte.setEtatCompte(true);
        //compte.setUtilisateur(utilisateur);
        TypeCompte typeCompte = Compte.TypeCompte.EPARGNE;
		compte.setTypeCompte(typeCompte);

        if ( compte.getSoldeCompte().longValue() < 0 ){
            throw new InvalidAmountException("Montant specifié null et/ou negative");
        }

        return cptRepo.save(compte);

    }


}
