package tn.esprit.banque.service;

import java.math.BigDecimal;
import java.util.List;

import tn.esprit.banque.model.Operation;

public interface OperationService {
	public void verser(Long codeCpte, BigDecimal montant,String commentaire);
	public void retirer(Long codeCpte, BigDecimal montant,String commentaire);
	public void virement(Long cpt1, Long cpt2, BigDecimal montant, String Commentaire);
	public void remiseCheque(Long cpt1, Long cpt2, BigDecimal montant, String Commentaire,Long numCheque);
	public List<Operation> listOperation(Long cpt);

}
