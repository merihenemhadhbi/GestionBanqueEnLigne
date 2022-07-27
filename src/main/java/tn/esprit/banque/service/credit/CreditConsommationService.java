package tn.esprit.banque.service.credit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.transaction.Transactional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import tn.esprit.banque.exceptions.InvalidAccountException;
import tn.esprit.banque.exceptions.InvalidAmountException;
import tn.esprit.banque.exceptions.InvalidMensualiteException;
import tn.esprit.banque.model.Compte;
import tn.esprit.banque.model.Credits;
import tn.esprit.banque.repository.CreditRepository;
import tn.esprit.banque.service.compte.CompteContrat;

public class CreditConsommationService  extends CreditAbstractionService{
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
public CreditConsommationService() {
	credit=Credits.TypeCredit.CONSOMMATION;
	}

@Transactional
	@Override
	Credits createCredit(Credits credits, Compte compte)
			throws InvalidAmountException, InvalidAccountException, InvalidMensualiteException {
		// TODO Auto-generated method stub
	credits.setCompteCredit(compte);
	credits.setDateCredit(new Date());
	credits.setTypeCredit(credit);

    if(!compte.isEtatCompte()){
        throw new InvalidAccountException("Compte n'est plus disponible veuillez contacter votre agence");
    }else if ( credits.getMontantCredit().longValue() <= 1000 || credits.getMontantCredit().longValue() > 5000){
        throw new InvalidAmountException("Montant specifié est supérieur à 1000 et ne dépasse pas 5000");
    }else if ( credits.getNombreMensualitesCredit() > 36 ){
        throw new InvalidMensualiteException("Veuillez saisir une mensualité ne dépasse pas  3 ans ");
    }else {
     
        Long TMM =(long) 0.09;

        if (credits.getNombreMensualitesCredit() > 12){
        	Long interet= TMM /100; 
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
		return creditRepository.save(credits);

	}

}
}