package tn.esprit.banque.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Operation;
import tn.esprit.banque.model.Retrait;
import tn.esprit.banque.model.Versement;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.repository.OperationRepository;
import tn.esprit.banque.service.compte.CompteContrat;
@Service
public class OperationServiceImpl implements OperationService {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	 private CompteContrat CompteService;

@Override
	public void verser(Long idCpte, BigDecimal montant,String commentaire) {
		Compte cp = null;
		try {
			cp = CompteService.findLeCompte(idCpte);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date =  new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		
		Versement v = new Versement( new Date(), date,"validate" , commentaire, cp);
		cp.setSoldeCompte(cp.getSoldeCompte().add(montant));
		compteRepository.save(cp);
		
	}

	@Override
	public void retirer(Long idCpte, BigDecimal montant, String commentaire) {
		Compte cp = null;
     	try {
			cp = CompteService.findLeCompte(idCpte);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BigDecimal rouge = null;
		BigDecimal solde =cp.getSoldeCompte();
		Date date =  new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		//if compteCourant
		if(true) {
			rouge= rouge.add(new BigDecimal(50));
		}
		if( solde.add(rouge).compareTo(montant) >1)
			throw new RuntimeException("Solde insuffisant");
		Retrait r = new Retrait(new Date(), date,"validate" , commentaire, cp);
		cp.setSoldeCompte(cp.getSoldeCompte().subtract(montant) );
		compteRepository.save(cp);
		
	}

	@Override
	public void virement(Long cpt1, Long cpt2, BigDecimal montant, String Commentaire) {
		if(cpt1.equals(cpt2)){
			throw new RuntimeException("Impossibile de faire un virement sur le même compte");
		}
//		
		retirer(cpt1,montant,"");
		verser(cpt2, montant,"");
		
	}

	/*@Override
	public List<Operation> listOperation(Long cpt) {
		
		return (List<Operation>) operationRepository.listOperation(cpt);
	}
*/
	@Override
	public void remiseCheque(Long cpt1, Long cpt2, BigDecimal montant, String Commentaire, Long numCheque) {
		Compte cp = null;
		Compte cp2 = null;
     	try {
			cp = CompteService.findLeCompte(cpt1);
			cp2 = CompteService.findLeCompte(cpt2);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	
     	if (cp.getSoldeCompte().compareTo(montant)==1 ||cp.getSoldeCompte().compareTo(montant)==0 )
		virement(cpt1, cpt2, montant, Commentaire);
     	else 
     		//compte compteCourant
     		if (false)
     		throw new RuntimeException("Impossibile de verser le cheque numéro "+numCheque +" dans le compte" +cpt2);
	}

	@Override
	public List<Operation> listOperation(Long cpt) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
	

}