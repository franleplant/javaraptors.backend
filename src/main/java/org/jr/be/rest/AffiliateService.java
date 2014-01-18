package org.jr.be.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jr.be.dto.AffiliateDTO;
import org.jr.be.dto.AffiliateLendDTO;
import org.jr.be.dto.AffiliateSearchDTO;
import org.jr.be.model.Affiliate;
import org.jr.be.model.Audit;
import org.jr.be.model.City;
import org.jr.be.model.EntityType;
import org.jr.be.model.Lend;
import org.jr.be.model.Prov;
import org.jr.be.model.User;
import org.jr.be.util.JsonResponseMsg;

@Path("/affiliate")
public class AffiliateService {
        
	private long entityTypeID = 3;
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
    
    
    
    private Set<AffiliateLendDTO> fetchLends(EntityManager entityManager, Affiliate affiliate) {
    	
    	Set<AffiliateLendDTO> lendsDTO = new HashSet<AffiliateLendDTO>(); 
    	
	    // Fetch all the affiliates current lends
	    List<Lend> lends = entityManager.createQuery(
	     	    "from Lend as lend where lend.affiliate = ?1 and lend.actualReturnDate is null", Lend.class)
	     	    .setParameter(1, affiliate)
	     	    .getResultList();
	  
	     
	    AffiliateLendDTO lendDTO;
	     
	 	for (Lend lend : lends) {
	        lendDTO = new AffiliateLendDTO();  	        
	        lendDTO.toDTO(lend);
	        lendsDTO.add(  lendDTO  );
	 	}
	 	return lendsDTO;
    }
    
    
    private City fetchCreateCity(EntityManager entityManager, AffiliateDTO affiliateDTO, Prov prov) {
    	
    	City city;
    	String cp = affiliateDTO.getAddress().getCp();
    	String city_name =  affiliateDTO.getAddress().getCity();
    	
        // Search for the city, if not found create it       
    	try {
        	city = (City) entityManager.createQuery(
        		    "select c from City as c where c.cp = ?1")
        		    .setParameter(1, cp)
        		    .getSingleResult();
 	
    		
    	} catch (NoResultException e) {
    		
    		city = new City();
    		city.setCp(  cp  );
    		city.setName(  city_name  );
    		city.setProv(  prov  );
    	} 
    	
    	return city;
    }
    
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AffiliateSearchDTO search(@QueryParam("q") String query, @Context HttpServletRequest request) {
    	
    	
    	if ( request.getSession(false) != null ) {
    		
	    	String q = '%' + query + '%';
	    	
	    	EntityManager entityManager = entityManagerFactory.createEntityManager();
	    	
	        // Search by name or by lastname
	        List<Affiliate> results = entityManager.createQuery(
	        	    "FROM Affiliate as a WHERE upper(a.person.name) LIKE ?1 OR upper(a.person.lastName) like ?2", Affiliate.class)
	        	    .setParameter(1, q.toUpperCase() )
	        	    .setParameter(2, q.toUpperCase() )
	        	    .getResultList();	
	    	
	    	
	    	
	    	
	    	List<AffiliateDTO> resultsDTO = new ArrayList<AffiliateDTO>();
	    	AffiliateDTO affiliateDTO;
	    	
	    	//Create the dto
	    	for (Affiliate affiliate : results){
	    		affiliateDTO = new AffiliateDTO();
	    		
	            if (  affiliate.isDeleted()  ) {
	            	break;
	            }    
	            affiliateDTO.setLends(  fetchLends(entityManager, affiliate)  );
	            
	
		        // Transfer all the data into the DTO
		        affiliateDTO.toDTO(affiliate);
		        resultsDTO.add(affiliateDTO);
	    	}
	    	
	    	
	    	
	    	entityManager.close();
	    	
	    	
	    	
	    	AffiliateSearchDTO response = new AffiliateSearchDTO(); 	
	    	response.setPage_number(1);
	    	response.setPage_total(1);
	    	response.setResults(resultsDTO);
	    	
	    	return response;
    	
        
    	} else {
    		throw new WebApplicationException(Response.Status.FORBIDDEN);
    	}
    }
    
        
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public AffiliateDTO getOne(@PathParam("id") Long id) {
            
    //public AffiliateDTO getOne(@PathParam("id") Long id, @Context HttpServletRequest request) {
    	//if ( request.getSession(false) != null ) {
    		
    		
    		
    	
    		
	    	Affiliate affiliate = null;
	    	AffiliateDTO dto = new AffiliateDTO();
	            
	        EntityManager entityManager = entityManagerFactory.createEntityManager();    
	        
	
	        try {
	        	affiliate = entityManager.find(Affiliate.class, id);
	        	
	        	//Has it found any entity?
	        	affiliate.getId();
	
	        } catch(NullPointerException ex) {
	        	
	        	throw new WebApplicationException(Response.Status.NOT_FOUND);
	        }
	        
	
	        //GET All the lends
	        dto.setLends(  fetchLends(entityManager, affiliate)  );
	                
	        entityManager.close();
	     
	        
	       
	        if (  affiliate.isDeleted()  ) {
	        	throw new WebApplicationException(Response.Status.NOT_FOUND);
	        }
	        
	        
	        
	        // Transfer all the data into the DTO
	        dto.toDTO(affiliate);
	
	        
	        return dto; 
        
    	//} else {
    	//	throw new WebApplicationException(Response.Status.FORBIDDEN);
    	//}
    }
    
    

        
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg create(AffiliateDTO dto, @Context HttpServletRequest request) throws Exception {
    	   	
    	
    	if ( request.getSession(false) != null ) {
	    	System.out.println(request.getSession().getAttribute("user"));
	    	
	    	Affiliate affiliate = dto.toEntity();   	
	        
	    	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
	    	//This references to today
	    	affiliate.getPerson().getAudit().setCreateDate(  new Date()  );
	        
	     
	    	u.begin();
	    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
	    	
	    	
	    	
	
	    	// Very simplified approach
	        //Country country = entityManager.find(Country.class, dto.getAddress().getCountry()  );
	    	
	    	Prov prov;
	    	
	    	// Search the prov, if not found send and error msg and do not do anything else
	    	try {  		
		        prov = (Prov) entityManager.createQuery(
		    		    "select p from Prov p where p.name = ?1")
		    		    .setParameter(1, dto.getAddress().getProv()  )
		    		    .getSingleResult(); 
		        	     
	    	} catch (NoResultException e) {
	    		
	        	u.commit();
	            entityManager.close();
	    		return new JsonResponseMsg("error", "La provincia no exite");
	    		
	    	}
	    	
	    	  	
	    	// Search for the city, if not found create it    
	    	affiliate.getPerson().getAddress().setCity(  fetchCreateCity(entityManager, dto, prov)  );
	    	
	    	
	    	
	            	
	    	// Set Entity Type
	    	EntityType type = entityManager.find(EntityType.class, entityTypeID);    	
	    	affiliate.setType(type);
	    	
	    	affiliate.getPerson().getAudit().setCreateUser( (User) request.getSession().getAttribute("user")  );
	    	
	    	// Persist it
	    	entityManager.merge(affiliate);
	    	entityManager.flush(); 	
	    	
	    	u.commit();
	        entityManager.close();
	        
	    	return new JsonResponseMsg("ok", "OOOHHH YEEEEAAAAAAHHHHHH!!!!");
    	
    	} else {
    		throw new WebApplicationException(Response.Status.FORBIDDEN);
    	}
    };
        
        
    @POST
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg edit(AffiliateDTO dto, @PathParam("id") Long id, @Context HttpServletRequest request) throws Exception {
    	
    	
    	if ( request.getSession(false) != null ) {
    	
	    	Affiliate affiliate = dto.toEntity();   	
	   	
	   	
	    	
	    	u.begin();
	    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
	    	
	    	// Copy current audit object and paste it into the new modified version of affiliate
	    	Affiliate existing_affiliate = entityManager.find(Affiliate.class, dto.getId()  );    	
	    	Audit audit = existing_affiliate.getPerson().getAudit();
	    	
	
	    	//This references to today http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
	    	audit.setEditDate(  new Date()  );
	    	affiliate.getPerson().setAudit(  audit  );
	    	
	    	
	    	
			// Set Entity Type  	
			affiliate.setType(  existing_affiliate.getType() );
	    	
	
	    	//
	    	// City, Prov, Country Handling
	    	//
	    	
	        //Country country = entityManager.find(Country.class, dto.getAddress().getCountry()  );
	    	
	    	Prov prov;
	    	
	    	// Search the prov, if not found send and error msg and do not do anything else
	    	try {  		
		        prov = (Prov) entityManager.createQuery(
		    		    "select p from Prov p where p.name = ?1")
		    		    .setParameter(1, dto.getAddress().getProv()  )
		    		    .getSingleResult(); 
		        	     
	    	} catch (NoResultException e) {
	    		
	        	u.commit();
	            entityManager.close();
	    		return new JsonResponseMsg("error", "La provincia no exite");
	    		
	    	}
	    	
	    	
	
	    	
	    	// Search for the city, if not found create it    
	    	affiliate.getPerson().getAddress().setCity(  fetchCreateCity(entityManager, dto, prov)  );
	    	

	    	affiliate.getPerson().getAudit().setEditUser((User) request.getSession().getAttribute("user") );
	    	
	    	// Merge it
	    	entityManager.merge(affiliate);
	    	entityManager.flush(); 	
	    	
	    	u.commit();
	        entityManager.close();
	        
	    	return new JsonResponseMsg("ok", "OOOHHH YEEEEAAAAAAHHHHHH!!!!");
    	
    	
    	} else {
    		throw new WebApplicationException(Response.Status.FORBIDDEN);
    	}
    };
        
        
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg delete(@PathParam("id") Long id, @Context HttpServletRequest request) throws Exception {
    	
    	if ( request.getSession(false) != null ) {
    	
	    	Affiliate affiliate = null;     
	        
	    	u.begin();
	        EntityManager entityManager = entityManagerFactory.createEntityManager();             
	        
	
	        affiliate = entityManager.find(Affiliate.class, id);
	
	        
	        
	        affiliate.setDeleted(true);
	        
	        affiliate.getPerson().getAudit().setDeleteDate(new Date());
	        
	        affiliate.getPerson().getAudit().setDeleteUser((User) request.getSession().getAttribute("user") );
	    	
	    	entityManager.merge(affiliate);
	    	entityManager.flush();
	    	u.commit();
	        entityManager.close();
	        
	        return new JsonResponseMsg("ok", "successfully deleted");
        
    	
    	} else {
    		throw new WebApplicationException(Response.Status.FORBIDDEN);
    	}
    };
        
}