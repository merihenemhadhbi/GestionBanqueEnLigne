package tn.esprit.banque.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lowagie.text.DocumentException;

import tn.esprit.banque.model.Carte;
import tn.esprit.banque.model.Operation;
import tn.esprit.banque.repository.OperationRepository;
import tn.esprit.banque.service.OperationService;
import tn.esprit.banque.service.compte.CompteContrat;
import tn.esprit.banque.service.operationUtils.RelevePDFExporter;

@Controller
public class OperationController {
	@Autowired
	OperationService operationService;
	
	 @Autowired 
	 private OperationRepository operation;
	 
	 
	
	@RequestMapping(value="/saveOperation", method=RequestMethod.POST)
	public ResponseEntity<String> saveOperation(
			String typeOperation, Long codeCpte,
			BigDecimal montant, Long codeCompte2, String commentaire, Long numCheque ){
		
		try{
			if(typeOperation.equals("VERS")){
			  
				operationService.verser(codeCpte, montant, commentaire);
			}
			else if(typeOperation.equals("RETR")){
				operationService.retirer(codeCpte, montant, commentaire);
			} 
			if (typeOperation.equals("VIR")){
				operationService.virement(codeCpte, codeCompte2, montant, commentaire);
				//operationService.virement(codeCpte, codeCompte2, montant,commentaire);
			}
			if (typeOperation.equals("RCHEQUE")){
				operationService.remiseCheque(codeCpte, codeCompte2, montant, commentaire, numCheque);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("Operation succes");
	}
	
	@GetMapping(value = "operation/findAll")
	public ResponseEntity findAll() {
		List<Operation> listOperation = new ArrayList<>();
		try {
			listOperation = operation.findAll();
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(listOperation);
	}	
	 @GetMapping("/export/releve")
	    public void exportToPDF(HttpServletResponse response, int month,long compte ) throws DocumentException, IOException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=relevé" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	         
	        ArrayList<Operation> operations = (ArrayList<Operation>) operation.findAll();
	         
	        RelevePDFExporter exporter = new RelevePDFExporter(operations,month,compte);
	        exporter.export(response);
	         
	    }
	
}
