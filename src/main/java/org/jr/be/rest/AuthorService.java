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

import org.jr.be.model.Author;
import org.jr.be.model.Audit;
import org.jr.be.model.EntityType;


import org.jr.be.util.JsonResponseMsg;

@Path("/author")
public class AuthorService {
        
        private long entityTypeID = 8;
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
        
        @GET
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public Author getOne(@PathParam("id") Long id) {
                
                Author author = null;
                
                EntityManager entityManager = entityManagerFactory.createEntityManager();             
        
                try {
                        author = entityManager.find(Author.class, id);
                        
                        //Has it found any entity?
                        author.getId();
                        
                        
                } catch(NullPointerException ex) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                } finally {
                        entityManager.close();
                }      
                
               
                if (  author.isDeleted()  ) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                }
                
                
                
                return author;      
        }
        
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg create(Author author) throws Exception {
                
                
                author.setDeleted(false);
                Audit audit = new Audit();
                
                //http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
                //This references to right now
                Date date = new Date();
                
                audit.setCreateDate(date);
                

                
                // When login is done do this:
                //audit.setCreateUser(createUser);
                
                author.setAudit(audit);
                
                //System.err.println(author.getPerson().getAddress().getCity().getName());
                
                u.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager(); 
                
                
                EntityType type = entityManager.find(EntityType.class, entityTypeID);
                
                author.setType(type);
                
                
                entityManager.merge(author);
                entityManager.flush();
                u.commit();
            entityManager.close();
            
                return new JsonResponseMsg("ok", "no msg");
        };
        
        
        @POST
        @Path("/{id:[0-9][0-9]*}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg edit(Author author, @PathParam("id") Long id) throws Exception {
                
                
                author.setDeleted(false);
      
                //http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
                //This references to right now
                Date date = new Date();
                
                author.getAudit().setEditDate(date);
                
                // When login is done do this:
                
                
            
                
                //System.err.println(author.getPerson().getAddress().getCity().getName());
                
                u.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager(); 
                
                entityManager.merge(author);
                entityManager.flush();
                u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "no msg");
        };
        
        
        @DELETE
        @Path("/{id:[0-9][0-9]*}")
        @Produces(MediaType.APPLICATION_JSON)
        public JsonResponseMsg delete(@PathParam("id") Long id) throws Exception {
                
                
                Author author = null;     
                
                u.begin();
                EntityManager entityManager = entityManagerFactory.createEntityManager();             
                
                author = entityManager.find(Author.class, id);
                
                author.setDeleted(true);
                
                author.getAudit().setDeleteDate(new Date());
                
                //Finish this when loggin is working
        
                
                entityManager.merge(author);
                entityManager.flush();
                u.commit();
            entityManager.close();
            
            return new JsonResponseMsg("ok", "no msg");
        };
        
}