package org.jr.be.rest;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jr.be.dto.CopyReturnGetDTO;
import org.jr.be.model.Copy;
import org.jr.be.model.Lend;



@Path("/copy/return")
public class CopyReturnService {

	@PersistenceUnit(unitName = "primary")
	private EntityManagerFactory entityManagerFactory;

	@Resource
	private UserTransaction u;
	
	
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public CopyReturnGetDTO getReturn(@PathParam("id") Long id) {
    	CopyReturnGetDTO dto = new CopyReturnGetDTO();
    	
    	EntityManager entityManager = entityManagerFactory.createEntityManager();  
    	
    	Copy copy = entityManager.find(Copy.class, id);
    	
    	
        Lend lend = entityManager.createQuery(
        	    "FROM Lend AS l WHERE l.copy = ?1 AND l.actualReturnDate IS null", Lend.class)
        	    .setParameter(1, copy )
        	    .getSingleResult();
        
        dto.toDTO(  lend  );
    	
    	
    	
        entityManager.close();
    	
    	return dto;
    }
	
	
}
