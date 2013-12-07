package org.jr.be.rest;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jr.be.dto.CopyLendGetDTO;
import org.jr.be.dto.CopyLendPostDTO;
import org.jr.be.model.Affiliate;
import org.jr.be.model.Copy;
import org.jr.be.model.Hollyday;
import org.jr.be.model.Lend;
import org.jr.be.model.LendType;
import org.jr.be.model.User;
import org.jr.be.util.JsonResponseMsg;


@Path("/copy/lend")
public class CopyLendService {
	
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
    
    private Date getMaxReturnDate(  String lendType, EntityManager em ) {
    	Date max;
    	

    	
        if (  lendType == "local" ) {
        	//If it has only a local lend then the affiliate should return it the same day the he/she took it
        	max = new Date();
        	
        } else {
        	
        	//Holyday logic
        	max = new Date();
        	
        	Calendar cal = Calendar.getInstance();
        	cal.setTime( new Date() );
     	
        	
        	int working_days = 0;
        	int total_days = 0;
        	
            
            
            while (working_days < 10) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                	
                	//es holyday?
                	
                	try {
                		em.createQuery(
	                    	    "FROM Hollyday as h WHERE h.date = ?1", Hollyday.class)
	                    	    .setParameter(1, cal.getTime() )
	                    	    .getSingleResult();
                	
	                	//Test if a hollyday in the given date has been found
	                	//hollyday.getId();
	                	
                	} catch(NoResultException ex) {
                		//If not
                		working_days++;
                	}
                	
                }
                
                total_days++;
            }
            
            System.out.println("Working days");
            System.out.println(working_days);
            System.out.println("Total Days");
            System.out.println(total_days);
            
            
        	Calendar cal_end = Calendar.getInstance();
        	cal_end.setTime( new Date() );
        	cal_end.add( Calendar.DATE, total_days);
        	
        	max = cal_end.getTime();
        	

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
            System.out.println("Iteration");
            System.out.println(lendType.getName());
            //getName is not matchig, dont know why
        	if ( lendType.getId() == 2 ) {
        		parent_lend_type = "foreign";
                System.out.println("Inside the if");
                System.out.println(parent_lend_type);
        		break;
        	}
        };
        
        System.out.println("Parent Type Lend");
        System.out.println(parent_lend_type);
        

        dto.getDate().setMax(  getMaxReturnDate(parent_lend_type, entityManager)  );
        
       	
        entityManager.close();
        
        return dto;      
    	
    }



	@POST
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponseMsg make_lend(CopyLendPostDTO dto, @PathParam("id") Long id) throws Exception {
		
		
        
     
    	u.begin();
    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
    	
    	Lend lend = new Lend();
    	
    	
    	
    	Copy copy = entityManager.find(Copy.class, id);
    	lend.setCopy(copy);
    	Affiliate affiliate = entityManager.find(Affiliate.class, dto.getAffiliate_id());
    	lend.setAffiliate(affiliate);
    	
    	lend.setComments( dto.getLend_comments() );

    	lend.setLendDate( new Date() );
    	
    	lend.setExpectedReturnDate( dto.getExpectedReturnDate() );
    
    	
    	//Hardcode the user with id number 1
    	//This will come from the session when login is impelemented
    	User lendingUser = entityManager.find(User.class, (long) 1);
    	lend.setLendingUser(lendingUser);
    	
    	
    	
    	LendType lendType = entityManager.createQuery(
        	    "FROM LendType as l WHERE l.name = ?1", LendType.class)
        	    .setParameter(1, dto.getLend_type() )
        	    .getSingleResult();

    	lend.setLendType(lendType);
    	
    	System.out.println(lendType);
    	
    	// Persist it
    	entityManager.merge(lend);
    	entityManager.flush(); 	
    	
    	u.commit();
        entityManager.close();
        
    	return new JsonResponseMsg("ok", "OOOHHH YEEEEAAAAAAHHHHHH!!!!");   
		
	}
	
}
