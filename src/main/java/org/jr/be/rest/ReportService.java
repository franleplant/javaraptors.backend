package org.jr.be.rest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jr.be.dto.ReportLateReturnsDTO;
import org.jr.be.dto.ReportLendDTO;
import org.jr.be.model.Book;
import org.jr.be.model.Lend;


@Path("/report")
public class ReportService {
	
	@PersistenceUnit(unitName = "primary")
	private EntityManagerFactory entityManagerFactory;

	@Resource
	private UserTransaction u;
	
	@GET
    @Path("/late_returns")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ReportLateReturnsDTO> late_returns() {
		Set<ReportLateReturnsDTO> response = new HashSet<ReportLateReturnsDTO>();
    	
    	EntityManager entityManager = entityManagerFactory.createEntityManager();  
    	
   	
    	
        List<Lend> lends = entityManager.createQuery(
        	    "FROM Lend AS l WHERE l.actualReturnDate IS null", Lend.class)
        	    .getResultList();
        
        entityManager.close();
        
        ReportLateReturnsDTO dto;
        for ( Lend lend : lends ) {
        	
        	dto = new ReportLateReturnsDTO();
        	dto.toDTO(  lend  );       	
        	if (dto.getDelayed_days() > 0) {
        		response.add(  dto );
        	}
        	
        }
        
    	
    	return response;
    }
	
	
	@GET
    @Path("/lends")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ReportLendDTO> lends_by_month() {
		Set<ReportLendDTO> response = new HashSet<ReportLendDTO>();
    	
    	EntityManager entityManager = entityManagerFactory.createEntityManager();  
    	
    	ReportLendDTO dto;
    	
    	
    	//http://www.mkyong.com/java/how-to-modify-date-time-date-manipulation-java/
    	Calendar cal = Calendar.getInstance();
    	cal.setTime( new Date() );
    	
    	
    	
    	//Iterate over past 12 months
    	for (int  i = 0; i < 12; i++) {
    		
        	int month = cal.get(Calendar.MONTH);
        	
        	 	
        	//http://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html_single/#queryhql
        	Iterator lend_report = entityManager.createQuery(
                    "SELECT l.copy.book, count(*)  FROM Lend l WHERE month(  l.lendDate  ) = ?1 GROUP BY l.copy.book")
                    .setParameter(1, month)
                    .getResultList()
                    .iterator();
    	
        	
        	//Iterate over all books
    	    while ( lend_report.hasNext() ) {
    	    	
    	    	dto = new ReportLendDTO();
    	    	
    	    	Object[] tuple = (Object[]) lend_report.next();
    	    	
    	    	
    	        Book book = (Book) tuple[0];
    	        Long lend_number = (Long) tuple[1];

    	        
    	        
    	        dto.setId(  book.getId()  );
    	        dto.setTitle(  book.getTitle());
    	        dto.setLend_number(lend_number);
    	        dto.setMonth(month);
    	        dto.setYear(  cal.get(Calendar.YEAR)  );
    	        
    	        
    	        response.add(  dto );
    	        
    	    }	
    	    
    	    cal.add(Calendar.MONTH, -1);
    	}
    	

        
        entityManager.close();
                
    	
    	return response;
    }
	

}
