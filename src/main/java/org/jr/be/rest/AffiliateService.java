package org.jr.be.rest;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jr.be.model.Affiliate;
import org.jr.be.model.Audit;
import org.jr.be.model.EntityType;


import org.jr.be.util.JsonResponseMsg;

@Path("/affiliate")
public class AffiliateService {
        
	private long entityTypeID = 3;
    
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
	
	        try {
	        	affiliate = entityManager.find(Affiliate.class, id);
	        	
	        	//Has it found any entity?
	        	affiliate.getId();
	        	
	        	
	        } catch(NullPointerException ex) {
	        	throw new WebApplicationException(Response.Status.NOT_FOUND);
	        } finally {
	        	entityManager.close();
	        }      
	        
	       
	        if (  affiliate.getDeleted()  ) {
	        	throw new WebApplicationException(Response.Status.NOT_FOUND);
	        }
	        
	        
	        
	        return affiliate;      
        }
        
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg create(Affiliate affiliate) throws Exception {
        	
        	
        	affiliate.setDeleted(false);
        	Audit audit = new Audit();
        	
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	audit.setCreateDate(date);
        	

        	
        	// When login is done do this:
        	//audit.setCreateUser(createUser);
        	
        	affiliate.getPerson().setAudit(audit);
        	
        	System.err.println(affiliate.getPerson().getAddress().getCity().getName());
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	
        	EntityType type = entityManager.find(EntityType.class, entityTypeID);
        	
        	affiliate.setType(type);
        	
        	
        	entityManager.merge(affiliate);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
        	return new JsonResponseMsg("ok", "no msg");
        };
        
        
        @POST
        @Path("/{id:[0-9][0-9]*}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public String edit(Affiliate affiliate, @PathParam("id") Long id) throws Exception {
        	
        	
        	affiliate.setDeleted(false);
      
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	affiliate.getPerson().getAudit().setEditDate(date);
        	
        	// When login is done do this:
        	//affiliate.getPerson().getAudit().setEditUser();
        	
    	
        	
        	System.err.println(affiliate.getPerson().getAddress().getCity().getName());
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	entityManager.merge(affiliate);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
        	return "{'status': 'ok'}";
        };
        
        
        @DELETE
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public String delete(@PathParam("id") Long id) throws Exception {
        	
        	
        	Affiliate affiliate = null;     
	        
        	u.begin();
	        EntityManager entityManager = entityManagerFactory.createEntityManager();             
	        
	        affiliate = entityManager.find(Affiliate.class, id);
	        
	        affiliate.setDeleted(true);
	        
	        affiliate.getPerson().getAudit().setDeleteDate(new Date());
	        
	        //Finish this when loggin is working
	        //affiliate.getPerson().getAudit().setDeleteUser(deleteDate);
        	
        	entityManager.merge(affiliate);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
        	return "{status: ok}";
        };
        
}