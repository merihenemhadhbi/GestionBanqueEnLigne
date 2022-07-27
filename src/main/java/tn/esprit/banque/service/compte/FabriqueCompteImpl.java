package tn.esprit.banque.service.compte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.model.Compte;

@Service
public class FabriqueCompteImpl implements FabriqueCompte {

	 private ApplicationContext applicationContext;

	    @Autowired
	    public void setApplicationContext(ApplicationContext applicationContext) {
	        this.applicationContext = applicationContext;
	    }

	    @Override
	    public CompteAbstraction generateAccount(Compte.TypeCompte typeCompte) throws InvalidSwitchCaseException {

	        CompteAbstraction compte;

	        switch (typeCompte){
	            case COURANT:
	                compte = applicationContext.getBean(CompteCourant.class);
	                break;
	            case EPARGNE:
	                compte = applicationContext.getBean(CompteEpargne.class);
	                break;
	            default:
	                throw new InvalidSwitchCaseException("Veuillez specifier le type de compte");
	        }

	       return compte;

	    }

}
