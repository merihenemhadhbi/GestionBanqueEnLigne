package tn.esprit.banque;

import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidUserException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Utilisateur;
import tn.esprit.banque.model.Compte.TypeCompte;

public abstract  class CompteAbstraction {
	TypeCompte typeCompte;

    abstract Compte createAccount(Compte compte,Utilisateur utilisateur) throws InvalidAmountException, InvalidUserException;


}
