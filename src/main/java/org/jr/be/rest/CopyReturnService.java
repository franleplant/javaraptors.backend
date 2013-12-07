package org.jr.be.rest;

import java.util.Calendar;
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

import org.jr.be.dto.CopyReturnGetDTO;
import org.jr.be.dto.CopyReturnPostDTO;
import org.jr.be.model.Copy;
import org.jr.be.model.Lend;
import org.jr.be.model.Suspension;
import org.jr.be.model.User;
import org.jr.be.util.JsonResponseMsg;



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
	
    @POST
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponseMsg make_return(CopyReturnPostDTO dto, @PathParam("id") Long id) throws Exception {
		
		
        
     
    	u.begin();
    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
    	
    	Copy copy = entityManager.find(Copy.class, id);
    	
        Lend lend = entityManager.createQuery(
        	    "FROM Lend AS l WHERE l.copy = ?1 AND l.actualReturnDate IS null", Lend.class)
        	    .setParameter(1, copy )
        	    .getSingleResult();
        
        lend.setActualReturnDate(  new Date()  );
        lend.setComments( dto.getLend_comments() );
        
        //This shuld be replaced with Sesion user
        User user = entityManager.find(User.class, (long) 1);
        lend.setReturningUser(user);
        
        
        //Suspension logic
        if ( lend.getActualReturnDate().getTime() > lend.getExpectedReturnDate().getTime() ) {
    		long diff = lend.getActualReturnDate().getTime() - lend.getExpectedReturnDate().getTime();
    		long diffDays = diff / (24 * 60 * 60 * 1000);
    		
    		
        	Calendar cal = Calendar.getInstance();
        	cal.setTime( new Date() );
        	
        	// According to business rule, Suspend the affiliate 2 days for 
        	// each delayed day
        	cal.add(Calendar.DAY_OF_MONTH, (int) diffDays * 2);
        	

        	Suspension suspension = new Suspension();
        	
        	suspension.setStartDate(  new Date() );
        	suspension.setEndDate(  cal.getTime());
        	suspension.setComments(  "Suspendido automáticamente por el sistema debido a retrasos en la devolución de un préstamo");
        		
        	lend.getAffiliate().getSuspensions().add( suspension );
            
               
        }

    	
    	// Persist it
    	entityManager.merge(lend);
    	entityManager.flush(); 	
    	
    	u.commit();
        entityManager.close();
        
    	return new JsonResponseMsg("ok", "OOOHHH YEEEEAAAAAAHHHHHH!!!!");   
		
	}
}
