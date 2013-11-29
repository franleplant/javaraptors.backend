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

import org.jr.be.model.Editorial;
import org.jr.be.model.Audit;
import org.jr.be.model.EntityType;


import org.jr.be.util.JsonResponseMsg;

@Path("/editorial")
public class EditorialService {
        
	private long entityTypeID = 7;
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
        
        @GET
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public Editorial getOne(@PathParam("id") Long id) {
                
        	Editorial editorial = null;
                
	        EntityManager entityManager = entityManagerFactory.createEntityManager();             
	
	        try {
	        	editorial = entityManager.find(Editorial.class, id);
	        	
	        	//Has it found any entity?
	        	editorial.getId();
	        	
	        	
	        } catch(NullPointerException ex) {
	        	throw new WebApplicationException(Response.Status.NOT_FOUND);
	        } finally {
	        	entityManager.close();
	        }      
	        
	       
	        if (  editorial.isDeleted()  ) {
	        	throw new WebApplicationException(Response.Status.NOT_FOUND);
	        }
	        
	        
	        
	        return editorial;      
        }
        
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg create(Editorial editorial) throws Exception {
        	
        	
        	editorial.setDeleted(false);
        	Audit audit = new Audit();
        	
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	audit.setCreateDate(date);
        	

        	
        	// When login is done do this:
        	//audit.setCreateUser(createUser);
        	
        	editorial.setAudit(audit);
        	
        	//System.err.println(editorial.getPerson().getAddress().getCity().getName());
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	
        	EntityType type = entityManager.find(EntityType.class, entityTypeID);
        	
        	editorial.setType(type);
        	
        	
        	entityManager.merge(editorial);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
        	return new JsonResponseMsg("ok", "no msg");
        };
        
        
        @POST
        @Path("/{id:[0-9][0-9]*}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg edit(Editorial editorial, @PathParam("id") Long id) throws Exception {
        	
        	
        	editorial.setDeleted(false);
      
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	editorial.getAudit().setEditDate(date);
        	
        	// When login is done do this:
        	
        	
    	
        	
        	//System.err.println(editorial.getPerson().getAddress().getCity().getName());
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	entityManager.merge(editorial);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "no msg");
        };
        
        
        @DELETE
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg delete(@PathParam("id") Long id) throws Exception {
        	
        	
        	Editorial editorial = null;     
	        
        	u.begin();
	        EntityManager entityManager = entityManagerFactory.createEntityManager();             
	        
	        editorial = entityManager.find(Editorial.class, id);
	        
	        editorial.setDeleted(true);
	        
	        editorial.getAudit().setDeleteDate(new Date());
	        
	        //Finish this when loggin is working
	
        	
        	entityManager.merge(editorial);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "no msg");
        };
        
}