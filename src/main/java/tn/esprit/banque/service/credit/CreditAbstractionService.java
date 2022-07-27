package tn.esprit.banque.service.credit;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidMensualiteException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Credits;

public abstract class CreditAbstractionService {

    Credits.TypeCredit credit;

    abstract Credits createCredit(Credits credits, Compte compte) throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException;

}