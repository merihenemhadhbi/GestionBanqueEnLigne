package tn.esprit.banque.service.credit;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidMensualiteException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Credits;
import tn.esprit.banque.repository.CreditRepository;
import tn.esprit.banque.service.compte.CompteContrat;

public class CreditImmobilierService extends CreditAbstractionService {
	   private static final Logger LOG = LoggerFactory.getLogger(CreditImmobilierService.class);

	    public Double getTMM(){

	      Document doc = null;
	    try {
	        doc = Jsoup.connect("https://www.bct.gov.tn/bct/siteprod/tableau_statistique_a.jsp?params=PL203105&la=AR").get();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	        Elements tableRows = doc.select(".bct-table-fixed table tr");
	        Double lastValue = 0d;
	        for (Element tableRow : tableRows) {
	            String rowData = tableRow.text();
	            if (rowData.split(" ").length == 7){
	                lastValue = Double.parseDouble(rowData.split(" ")[6]);
	            }
	        }
	        return lastValue;

	    }
	private CreditRepository creditRepository; 
	private CompteContrat compteContrat; 


	@Autowired
	public void setCreditRepository(CreditRepository creditRepository) {
		this.creditRepository = creditRepository;
	}

	@Autowired
	public void setCompteContrat(CompteContrat compteContrat) {
		this.compteContrat = compteContrat;
	}
	public CreditImmobilierService() {
		credit=Credits.TypeCredit.IMMOBILIER;
		}
    @Transactional
	@Override
	Credits createCredit(Credits credits, Compte compte)
			throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException {
    	credits.setCompteCredit(compte);
    	credits.setDateCredit(new Date());
    	credits.setTypeCredit(credit);

        if(!compte.isEtatCompte()){
            throw new InvalidAccountException("Compte n'est plus disponible veuillez contacter votre agence");
        }else if ( credits.getMontantCredit().longValue() <= 10000 ){
            throw new InvalidAmountException("Montant specifié est supérieur à 10000");
        }else if ( credits.getNombreMensualitesCredit() > 240 ){
            throw new InvalidMensualiteException("Veuillez saisir une mensualité ne dépasse pas  20 ans ");
        }else {
         
         	Double TMM = getTMM();
            if (credits.getNombreMensualitesCredit()<= 84){
            	Long interet= TMM.longValue() +2 /100; 
            	Long MontantSansInteret= credits.getMontantCredit();
             Long MontantAvecInteret= MontantSansInteret+(MontantSansInteret+interet);
             Long MontantApayerMensuelle = MontantAvecInteret/credits.getNombreMensualitesCredit(); 
             credits.setMensualite(MontantApayerMensuelle);
             Long MontantMax= compte.getSoldeCompte().longValue()*40/100 ;
             if(MontantMax >= MontantApayerMensuelle  ) {
             	credits.setApprouver(true); 
             }else {
             	credits.setApprouver(false);}

            }else if (credits.getNombreMensualitesCredit() > 84 && credits.getNombreMensualitesCredit() <= 180) {
           
            	Long interet= (long) ((TMM +2.25) /100); 
            	Long MontantSansInteret= credits.getMontantCredit();
             Long MontantAvecInteret= MontantSansInteret+(MontantSansInteret+interet);
             Long MontantApayerMensuelle = MontantAvecInteret/credits.getNombreMensualitesCredit(); 
             credits.setMensualite(MontantApayerMensuelle);
             Long MontantMax= compte.getSoldeCompte().longValue()*40/100 ;
             if(MontantMax >= MontantApayerMensuelle  ) {
             	credits.setApprouver(true); 
             }else {
             	credits.setApprouver(false);}
             
            }else if (credits.getNombreMensualitesCredit() > 180 && credits.getNombreMensualitesCredit() <= 240) {
           
            	Long interet= (long) ((TMM +2.5) /100); 
            	Long MontantSansInteret= credits.getMontantCredit();
             Long MontantAvecInteret= MontantSansInteret+(MontantSansInteret+interet);
             Long MontantApayerMensuelle = MontantAvecInteret/credits.getNombreMensualitesCredit(); 
             credits.setMensualite(MontantApayerMensuelle);
             Long MontantMax= compte.getSoldeCompte().longValue()*40/100 ;
             if(MontantMax >= MontantApayerMensuelle  ) {
             	credits.setApprouver(true); 
             }else {
             	credits.setApprouver(false);}
            }
            
            }
		return creditRepository.save(credits);
	}

}
