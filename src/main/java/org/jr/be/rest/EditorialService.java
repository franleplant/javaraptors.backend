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
import javax.persistence.NoResultException;

import org.jr.be.model.Editorial;
import org.jr.be.dto.EditorialDTO;
import org.jr.be.model.Audit;
import org.jr.be.model.EntityType;
import org.jr.be.model.City;
import org.jr.be.model.Prov;

import org.jr.be.util.JsonResponseMsg;

@Path("/editorial")
public class EditorialService {
        
	private long entityTypeID = 7;
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
        
    private City fetchCreateCity(EntityManager entityManager, EditorialDTO editorialDTO, Prov prov) {
        
        City city;
        String cp = editorialDTO.getAddress().getCp();
        String city_name = editorialDTO.getAddress().getCity();
        
    // Search for the city, if not found create it
        try {
            city = (City) entityManager.createQuery(
                     "select c from City as c where c.cp = ?1")
                     .setParameter(1, cp)
                     .getSingleResult();
     
                
        } catch (NoResultException e) {
                
                city = new City();
                city.setCp( cp );
                city.setName( city_name );
                city.setProv( prov );
        }
        
        return city;
    }
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public EditorialDTO getOne(@PathParam("id") Long id) {
            
            Editorial editorial = null;
            EditorialDTO dto = new EditorialDTO();
            
        EntityManager entityManager = entityManagerFactory.createEntityManager();    
        

        try {
                editorial = entityManager.find(Editorial.class, id);
                
                if (editorial.isDeleted()) throw new WebApplicationException(Response.Status.NOT_FOUND);

        } catch(NullPointerException ex) {
                
                throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        

        
              
        
        // Transfer all the data into the DTO
        dto.toDTO(editorial,entityManager);
        
        entityManager.close();
        return dto;      
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg create(EditorialDTO dto) throws Exception {
                       
            Editorial editorial = dto.toEntity();           
        
            //http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
            //This references to today
            editorial.getAudit().setCreateDate(  new Date()  );
        
     
            u.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager(); 
            
            //Placeholder for all city-prov logic 
            
            Prov prov;
            
            // Search the prov, if not found send and error msg and do not do anything else
            try {                 
            				prov = (Prov) entityManager.createQuery(
                          "select p from Prov p where p.name = ?1")
                          .setParameter(1, dto.getAddress().getProv() )
                          .getSingleResult();
                 
            } catch (NoResultException e) {
                    
                u.commit();
            entityManager.close();
                    return new JsonResponseMsg("error", "La provincia no exite");
                    
            }
            
                     
            // Search for the city, if not found create it
            editorial.getAddress().setCity( fetchCreateCity(entityManager, dto, prov) );
                     
            // Set Entity Type
            EntityType type = entityManager.find(EntityType.class, entityTypeID);            
            editorial.setType(type);
            
            // When login is done do this:
            //affiliate.getPerson().getAudit().setCreateUser(current_loged_user);
            
            System.out.println(editorial.toString());
            System.out.println(editorial.getId());
            System.out.println(dto.toString());
            
            // Persist it
            entityManager.merge(editorial);
            entityManager.flush();         
            
            u.commit();
            entityManager.close();
        
            return new JsonResponseMsg("ok", "Editorial created birmania.");
            
            
    }
    
    @POST
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg edit(EditorialDTO dto) throws Exception {
                       
            Editorial editorial = dto.toEntity();           
        
            //http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
            //This references to today
                   
     
            u.begin();
            EntityManager entityManager = entityManagerFactory.createEntityManager(); 
            
            Prov prov;
            
            // Search the prov, if not found send and error msg and do not do anything else
            try {                 
            prov = (Prov) entityManager.createQuery(
                          "select p from Prov p where p.name = ?1")
                          .setParameter(1, dto.getAddress().getProv() )
                          .getSingleResult();
                 
            } catch (NoResultException e) {
                    
            u.commit();
            entityManager.close();
            return new JsonResponseMsg("error", "La provincia no exite");
                    
            }
                      
            // Search for the city, if not found create it
            editorial.getAddress().setCity( fetchCreateCity(entityManager, dto, prov) );       
                     
            // Set Entity Type
            EntityType type = entityManager.find(EntityType.class, entityTypeID);            
            editorial.setType(type);
            
            Editorial existing_editorial = entityManager.find(Editorial.class, dto.getId()  );            
            Audit audit = existing_editorial.getAudit();
            audit.setEditDate(  new Date()  );
            editorial.setAudit(  audit  );
            
            
            // When login is done do this:
            //affiliate.getPerson().getAudit().setCreateUser(current_loged_user);
            
            // Persist it
            entityManager.merge(editorial);
            entityManager.flush();         
            
            u.commit();
            entityManager.close();
        
            return new JsonResponseMsg("ok", "Editorial edited birmania.");
            
            
    }
    
    
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
        
        //Finish this when logging is working
        //affiliate.getPerson().getAudit().setDeleteUser(deleteDate);
            
        entityManager.merge(editorial);
        entityManager.flush();
        u.commit();
        entityManager.close();
        
        return new JsonResponseMsg("ok", "Editorial Birmaniamente deleted");
    };
    
}
        