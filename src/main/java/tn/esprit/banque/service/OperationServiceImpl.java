package tn.esprit.banque.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Operation;
import tn.esprit.banque.model.RemiseCheque;
import tn.esprit.banque.model.Retrait;
import tn.esprit.banque.model.Versement;
import tn.esprit.banque.model.Virement;
import tn.esprit.banque.repository.CarteRepository;
import tn.esprit.banque.repository.CompteRepository;
import tn.esprit.banque.repository.OperationRepository;
import tn.esprit.banque.service.compte.CompteContrat;
import tn.esprit.banque.service.compte.CompteCourant;
@Service
public class OperationServiceImpl implements OperationService {
	
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private CompteContrat CompteService;
	
	private CompteRepository compteRepo;
    @Autowired
    public void setCompteRepo(CompteRepository compteRepo) {
        this.compteRepo = compteRepo;
    }
    @Transactional
    @Override
	public void verser(Long idCpte, BigDecimal montant,String commentaire) {
		
		
		Compte cp= new Compte();
		try {
			//Compte cp1 = compteRepo.findById(idCpte).orElseThrow(() -> new InvalidAccountException("Compte indisponible"));
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
		v.setMontant(montant.longValue());
		cp.setSoldeCompte(cp.getSoldeCompte().add(montant));
		operationRepository.save(v);
		compteRepo.save(cp);
		
	}
    @Transactional
	@Override
	public void retirer(Long idCpte, BigDecimal montant, String commentaire) {
		Compte cp = null;
     	try {
			cp = CompteService.findLeCompte(idCpte);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//BigDecimal rouge = cp.get
		BigDecimal solde =cp.getSoldeCompte();
		Date date =  new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		//if compteCourant
		
		if( solde.compareTo(montant) >1)
			throw new RuntimeException("Solde insuffisant");
		Retrait r = new Retrait(new Date(), date,"validate" , commentaire, cp);
		r.setMontant(montant.longValue());
		cp.setSoldeCompte(cp.getSoldeCompte().subtract(montant) );
		operationRepository.save(r);
		compteRepo.save(cp);
		
	}
   
	@Override
	public void virement(Long cpt1, Long cpt2, BigDecimal montant, String Commentaire) {
		if(cpt1.equals(cpt2)){
			throw new RuntimeException("Impossibile de faire un virement sur le même compte");
		}
//		
		Compte cp1= new Compte();
		Compte cp2= new Compte();
		try {
			//Compte cp1 = compteRepo.findById(idCpte).orElseThrow(() -> new InvalidAccountException("Compte indisponible"));
			cp1 = CompteService.findLeCompte(cpt1);
			cp2 = CompteService.findLeCompte(cpt2);
			
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date date =  new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		if( cp1.getSoldeCompte().compareTo(montant) >1)
			throw new RuntimeException("Solde insuffisant");
		Virement v = new Virement(new Date(), date, "validate",Commentaire , cp1);
		v.setMontant(montant.longValue());
		cp1.setSoldeCompte(cp1.getSoldeCompte().subtract(montant) );
		cp2.setSoldeCompte(cp2.getSoldeCompte().add(montant));
		operationRepository.save(v);
		compteRepo.save(cp1);
		compteRepo.save(cp2);
		
		
		
		
		
		
		
	}

	
	@Override
	public void remiseCheque(Long cpt1, Long cpt2, BigDecimal montant, String commentaire, Long numCheque) {
		Compte cp = null;
		Compte cp2 = null;
     	try {
			cp = CompteService.findLeCompte(cpt1);
			cp2 = CompteService.findLeCompte(cpt2);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     	
     	if (cp.getSoldeCompte().compareTo(montant)==1 ||cp.getSoldeCompte().compareTo(montant)==0 ) {
     		
     		Date date =  new Date();
    		Calendar c = Calendar.getInstance(); 
    		c.setTime(date); 
    		c.add(Calendar.DATE, 1);
    		date = c.getTime();
    		if( cp.getSoldeCompte().compareTo(montant) >1)
    			throw new RuntimeException("Solde insuffisant");
    		RemiseCheque r = new RemiseCheque(new Date(), date, "validate", commentaire, cp);
    			//	RemiseCheque(cp, cp2, montant, Commentaire, numCheque); 
    		r.setMontant(montant.longValue());
    		cp.setSoldeCompte(cp.getSoldeCompte().subtract(montant) );
    		cp2.setSoldeCompte(cp2.getSoldeCompte().add(montant));
    		operationRepository.save(r);
    		compteRepo.save(cp);
    		compteRepo.save(cp2);
     	}
		
     	
	}

	
	


	
	
	

}