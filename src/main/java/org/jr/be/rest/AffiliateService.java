package org.jr.be.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jr.be.model.Affiliate;


@Path("/affiliate")
public class AffiliateService {
        
   
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
        
        @GET
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public Affiliate getOne(@PathParam("id") Long id) {
                
        	Affiliate affiliate = null;
                
	        EntityManager entityManager = entityManagerFactory.createEntityManager();             
	        
	        affiliate = entityManager.find(Affiliate.class, id);
	        
	        
	        entityManager.close();
	        
	        
	        return affiliate;        
        }
        
}