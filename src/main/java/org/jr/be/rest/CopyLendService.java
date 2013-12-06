package org.jr.be.rest;

import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jr.be.dto.CopyLendGetDTO;
import org.jr.be.model.Copy;
import org.jr.be.model.LendType;


@Path("/copy/lend")
public class CopyLendService {
	
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
//    @Resource
//    private UserTransaction u;
    
    private Date getMaxReturnDate(  String lendType ) {
    	Date max;
    	

    	
        if (  lendType == "local" ) {
        	//If it has only a local lend then the affiliate should return it the same day the he/she took it
        	max = new Date();
        } else {
        	//Holyday logic
        	max = new Date();
        }
        
    	return max;
    }
    
    
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public CopyLendGetDTO start_lend(@PathParam("id") Long id) {
    	
    	CopyLendGetDTO dto = new CopyLendGetDTO();
    	
            
        EntityManager entityManager = entityManagerFactory.createEntityManager();             

        Copy copy = entityManager.find(Copy.class, id);
        
        if (  copy.isDeleted()  ) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        
        dto.toDTO( copy );
        
        String parent_lend_type = "local";
        for (LendType lendType : copy.getLendTypes()) {
        	if ( lendType.getName() == "foreign" ) {
        		parent_lend_type = "foreign";
        	}
        };
        

        dto.getDate().setMax(  getMaxReturnDate(parent_lend_type)  );
        
       	
        entityManager.close();
        
        return dto;      
    	
    }

}
