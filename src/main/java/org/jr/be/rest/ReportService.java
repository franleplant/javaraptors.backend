package org.jr.be.rest;

import java.util.HashSet;
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
    public Set<ReportLateReturnsDTO> getReturn() {
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
	

}
