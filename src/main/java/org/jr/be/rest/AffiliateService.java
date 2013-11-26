package org.jr.be.rest;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jr.be.model.Affiliate;
import org.jr.be.model.Audit;


@Path("/affiliate")
public class AffiliateService {
        
   
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
        
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
        
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String create(Affiliate affiliate) throws Exception {
        	
        	
        	affiliate.setDeleted(false);
        	Audit audit = new Audit();
        	
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	audit.setCreateDate(date);
        	
        	// When login is done do this:
        	//audit.setCreateUser(createUser);
        	
        	affiliate.getPerson().setAudit(audit);
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	entityManager.merge(affiliate);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
        	return "{status: ok}";
        };
        
}