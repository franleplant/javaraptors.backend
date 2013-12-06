package org.jr.be.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.jr.be.model.Author;
import org.jr.be.dto.AuthorDTO;
import org.jr.be.dto.AuthorSearchDTO;
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
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorSearchDTO search(@QueryParam("q") String query) {

            
            String q = '%' + query + '%';

            
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            
        // Search by nick

        List<Author> results = entityManager.createQuery(

                 "FROM Author as c WHERE upper(c.nick) LIKE ?1", Author.class)

                 .setParameter(1, q.toUpperCase() )

                 .getResultList();        

            
            
            
            
            List<AuthorDTO> authorsDTO = new ArrayList<AuthorDTO>();

            AuthorDTO authorDTO;
            
            //Create the dto
            for (Author author : results){

            	authorDTO = new AuthorDTO();

                    
            if ( author.isDeleted() ) {

                    break;
            }
            authorDTO.toDTO(author, entityManager);

            

         // Transfer all the data into the DTO

         authorsDTO.add(authorDTO);

            }
            
            
            
            entityManager.close();

            
            
            
            AuthorSearchDTO response = new AuthorSearchDTO();         

            response.setPage_number(1);

            response.setPage_total(1);

            response.setResults(authorsDTO);

            
            return response;

    }
    
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorDTO getOne(@PathParam("id") Long id) {
            
            Author author = null;
            AuthorDTO dto = new AuthorDTO();
            
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        

        try {
        	author = entityManager.find(Author.class, id);
                
                //Has it found any entity?
        	author.getId();

        } catch(NullPointerException ex) {
                
                throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        

        
              
        
        // Transfer all the data into the DTO
        dto.toDTO(author,entityManager);
        
        entityManager.close();
        return dto;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg create(AuthorDTO dto) throws Exception {
                       
    		Author author = dto.toEntity();
        
            //http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
            //This references to today
    		author.getAudit().setCreateDate( new Date() );
        
     
            u.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
                              
            // Set Entity Type
            EntityType type = entityManager.find(EntityType.class, entityTypeID);
            author.setType(type);
            
            // When login is done do this:
            //affiliate.getPerson().getAudit().setCreateUser(current_loged_user);
            
            // Persist it
            entityManager.merge(author);
            entityManager.flush();
            
            u.commit();
            entityManager.close();
        
            return new JsonResponseMsg("ok", "Autor created birmania.");
            
            
    }
    
    @POST
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg edit(AuthorDTO dto) throws Exception {
                       
            Author author = dto.toEntity();
        
            //http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
            //This references to today
                   
     
            u.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            
            EntityType type = entityManager.find(EntityType.class, entityTypeID);
            author.setType(type);
            
            Author existing_author = entityManager.find(Author.class, dto.getId() );
            Audit audit = existing_author.getAudit();
            audit.setEditDate( new Date() );
            author.setAudit( audit );
            
            
            // When login is done do this:
            //affiliate.getPerson().getAudit().setCreateUser(current_loged_user);
            
            // Persist it
            entityManager.merge(author);
            entityManager.flush();
            
            u.commit();
            entityManager.close();
        
            return new JsonResponseMsg("ok", "Author edited birmania.");
            
            
    }
    
    
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
        
        //Finish this when logging is working
        //affiliate.getPerson().getAudit().setDeleteUser(deleteDate);
            
        entityManager.merge(author);
        entityManager.flush();
        u.commit();
        entityManager.close();
        
        return new JsonResponseMsg("ok", "Author Birmaniamente deleted");
    };
    
}