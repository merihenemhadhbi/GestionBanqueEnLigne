package tn.esprit.banque.service.compte;

import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidUserException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Compte.TypeCompte;

public abstract  class CompteAbstraction {
	TypeCompte typeCompte;

    abstract Compte createAccount(Compte compte,Long userId) throws InvalidAmountException, InvalidUserException;


}
