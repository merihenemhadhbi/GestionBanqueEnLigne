package tn.esprit.banque.service.compte;

import tn.esprit.banque.exceptions.InvalidSwitchCaseException;
import tn.esprit.banque.model.Compte;

public interface FabriqueCompte {
	 CompteAbstraction generateAccount(Compte.TypeCompte typeCompte) throws InvalidSwitchCaseException;

}
