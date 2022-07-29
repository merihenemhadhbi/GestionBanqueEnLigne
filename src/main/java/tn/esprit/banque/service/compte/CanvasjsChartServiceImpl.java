package tn.esprit.banque.service.compte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import tn.esprit.banque.model.Operation;
import tn.esprit.banque.model.Retrait;
import tn.esprit.banque.model.Versement;
import tn.esprit.banque.model.Virement;
@Service
public class CanvasjsChartServiceImpl implements CanvasjsChartService  {
	
	private static JdbcTemplate jdbcTemplate;
	
	static {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/banque");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        jdbcTemplate = new JdbcTemplate(dataSource);
	}
 
	
	public static class DatabaseConnectionException  extends RuntimeException{
		
		private static final long serialVersionUID = 1L;
 
		public DatabaseConnectionException(String message) {
			super(message);
		}
	}
	@Override
	public List<List<Operation>>  getCanvasjsChartData() {
		
		 List<List<Operation>> list = new ArrayList<List<Operation>>();
		 List<Operation> dataPoints1 = new ArrayList<Operation>();
		 List<Operation> finalList = new ArrayList<Operation>();
String versement = "select * from versement";
String virement = "select * from virement";
String retrait = "select * from retrait";

        try {
        	dataPoints1 = jdbcTemplate.query(versement, new RowMapper<Operation>() {
 
				@Override
				public Versement mapRow(ResultSet rs, int rowNum) throws SQLException {
					Versement dataPoint = new Versement();
	     
	            	dataPoint.setMontant(rs.getLong("montant"));
	            	dataPoint.setDate_operation(rs.getDate("date_operation"));
	     
	                return dataPoint;
				}});
        }
        catch(Exception e){
        	dataPoints1 = null;
        	throw new DatabaseConnectionException("Error while getting dataPoints");
        }
        if(!dataPoints1.isEmpty()) {
        	finalList.addAll(dataPoints1);
		}
		// virement
		try {
        	dataPoints1 = jdbcTemplate.query(virement, new RowMapper<Operation>() {
 
				@Override
				public Virement mapRow(ResultSet rs, int rowNum) throws SQLException {
					Virement dataPoint = new Virement();
	     
	            	dataPoint.setMontant(rs.getLong("montant"));
	            	dataPoint.setDate_operation(rs.getDate("date_operation"));
	     
	                return dataPoint;
				}});
        }
        catch(Exception e){
        	dataPoints1 = null;
        	throw new DatabaseConnectionException("Error while getting dataPoints");
        }
		if(!dataPoints1.isEmpty()) {
        	finalList.addAll(dataPoints1);
		}
		// retrait
		
		try {
        	dataPoints1 = jdbcTemplate.query(retrait, new RowMapper<Operation>() {
 
				@Override
				public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
					Retrait dataPoint = new Retrait();
	     
	            	dataPoint.setMontant(rs.getLong("montant"));
	            	dataPoint.setDate_operation(rs.getDate("date_operation"));
	     
	                return dataPoint;
				}});
        }
        catch(Exception e){
        	dataPoints1 = null;
        	throw new DatabaseConnectionException("Error while getting dataPoints");
        }
		if(!dataPoints1.isEmpty()) {
        	finalList.addAll(dataPoints1);
		}
		if(!finalList.isEmpty()) {
			list.add(finalList);
		}
		return list;
	}
	
}


