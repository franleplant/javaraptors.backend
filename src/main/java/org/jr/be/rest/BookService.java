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

import org.jr.be.model.Book;
import org.jr.be.model.Audit;
import org.jr.be.model.EntityType;


import org.jr.be.util.JsonResponseMsg;

@Path("/book")
public class BookService {
        
	private long entityTypeID = 1;
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
        
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getOne(@PathParam("id") Long id) {
            
    	Book book = null;
            
        EntityManager entityManager = entityManagerFactory.createEntityManager();             

        try {
        	book = entityManager.find(Book.class, id);
        	
        	//Has it found any entity?
        	book.getId();
        	
        	
        } catch(NullPointerException ex) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
        } finally {
        	entityManager.close();
        }      
        
       
        if (  book.isDeleted()  ) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        
        
        return book;      
    }
        
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg create(Book book) throws Exception {
        	
        	
        	book.setDeleted(false);
        	Audit audit = new Audit();
        	
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	audit.setCreateDate(date);
        	

        	
        	// When login is done do this:
        	//audit.setCreateUser(createUser);
        	
        	book.setAudit(audit);
        	
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	
        	EntityType type = entityManager.find(EntityType.class, entityTypeID);
        	
        	book.setType(type);
        	
        	
        	entityManager.merge(book);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
        	return new JsonResponseMsg("ok", "oohhh yeeeeaaaaaaaah!");
        };
        
        
        @POST
        @Path("/{id:[0-9][0-9]*}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg edit(Book book, @PathParam("id") Long id) throws Exception {
        	
        	
        	book.setDeleted(false);
      
        	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
        	//This references to right now
        	Date date = new Date();
        	
        	book.getAudit().setEditDate(date);
        	
        	// When login is done do this:
        	//affiliate.getPerson().getAudit().setEditUser();
        	
        	
        	u.begin();
        	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
        	
        	entityManager.merge(book);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "oohhh yeeeeaaaaaaaah!");
        };
        
        
        @DELETE
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg delete(@PathParam("id") Long id) throws Exception {
        	
        	
        	Book book = null;     
	        
        	u.begin();
	        EntityManager entityManager = entityManagerFactory.createEntityManager();             
	        
	        book = entityManager.find(Book.class, id);
	        
	        book.setDeleted(true);
	        
	        book.getAudit().setDeleteDate(new Date());
	        
	        //Finish this when loggin is working
	        //book.getAudit().setDeleteUser(deleteDate);
        	
        	entityManager.merge(book);
        	entityManager.flush();
        	u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "oohhh yeeeeaaaaaaaah!");
        };
        
}