package org.jr.be.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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

import org.jr.be.dto.AddressDTO;
import org.jr.be.dto.AffiliateCopyDTO;
import org.jr.be.dto.AffiliateDTO;
import org.jr.be.dto.AffiliateLendDTO;
import org.jr.be.dto.AffiliateSearchDTO;
import org.jr.be.dto.AuditDTO;
import org.jr.be.model.Affiliate;
import org.jr.be.model.Audit;
import org.jr.be.model.City;
import org.jr.be.model.EntityType;
import org.jr.be.model.Lend;
import org.jr.be.model.Prov;
import org.jr.be.model.Suspension;
import org.jr.be.util.JsonResponseMsg;

@Path("/affiliate")
public class AffiliateService {
        
	private long entityTypeID = 3;
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
    
    @Resource
    private UserTransaction u;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AffiliateSearchDTO search(@QueryParam("q") String query) {
    	
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
    	for (Affiliate a : results){
    		affiliateDTO = new AffiliateDTO();
    		
            if (  a.isDeleted()  ) {
            	break;
            }
    		
    	       // Fetch all the affiliates current lends
            List<Lend> lends = entityManager.createQuery(
            	    "from Lend as lend where lend.affiliate = ?1 and lend.actualReturnDate is null", Lend.class)
            	    .setParameter(1, a)
            	    .getResultList();
         
            
            AffiliateLendDTO lendDTO;;
            AffiliateCopyDTO copyDTO;
            
        	for (Lend lend : lends) {


    	        lendDTO = new AffiliateLendDTO();
    	        copyDTO = new AffiliateCopyDTO();
    	        
    	        //Lend DTO
    	        lendDTO.setId(                  lend.getId()                  );
    	        lendDTO.setLendingDate(         lend.getLendDate()            );
    	        lendDTO.setExpectedReturnDate(  lend.getExpectedReturnDate()  );        
    	        lendDTO.setType(                lend.getLendType().getName()  );
    	        lendDTO.setComments(            lend.getComments()            );
    	        
    	        // Lend DTO . copy
    	        copyDTO.setId(        lend.getCopy().getId()               );
    	        copyDTO.setTitle(     lend.getCopy().getBook().getTitle()  );
    	        
    	        lendDTO.setCopy(  copyDTO  );
    	        
    	        
    	        //Add to the Affiliate DTO
    	        affiliateDTO.getLends().add(  lendDTO  );
        	}
    		
	
	    	
	    	
	      	// Can the affiliate request more lends?
	        Set<Suspension> suspensions = a.getSuspensions();
	        boolean active = true;
	        
	        for (Suspension s : suspensions){
	        	if (  s.getEndDate().after(new Date())  ) {
	        		active = false;
	        		break;
	        	}
	        }
	        
	        affiliateDTO.setActive(active);
	        
	
	        
	        
	        
	        // Transfer all the data into the DTO
	        affiliateDTO.setId(       a.getId()  );
	        affiliateDTO.setName(     a.getPerson().getName()  );
	        affiliateDTO.setLastName( a.getPerson().getLastName()  );
	        affiliateDTO.setDni(      a.getPerson().getDni()  );
	        affiliateDTO.setCuil(     a.getPerson().getCuil()  );
	        affiliateDTO.setImg(      a.getPerson().getImg());
	        
	        affiliateDTO.setEmail(    a.getPerson().getContact().getEmail() );
	        affiliateDTO.setTel(      a.getPerson().getContact().getTel() );
	        affiliateDTO.setCel(      a.getPerson().getContact().getCel() );
	        
	        
	        affiliateDTO.setReputation(  a.getReputation()  );
	        affiliateDTO.setType( a.getType().getName()  );
	        
	        
	        affiliateDTO.setAudit(  new AuditDTO()  );
	        affiliateDTO.getAudit().setCreateDate(   a.getPerson().getAudit().getCreateDate()   );
	        affiliateDTO.getAudit().setEditDate(     a.getPerson().getAudit().getEditDate()     );
	        affiliateDTO.getAudit().setDeleteDate(   a.getPerson().getAudit().getDeleteDate()   );
	        
	        
	        
	        affiliateDTO.getAudit().setCreateUser(   a.getPerson().getAudit().getCreateUser().getPerson().getFullName() );
	        
	        
	        if (  a.getPerson().getAudit().getEditUser() != null  ){
	        	affiliateDTO.getAudit().setEditUser(     a.getPerson().getAudit().getEditUser().getPerson().getFullName()   );
	        }
	        
	        
	        if (  a.getPerson().getAudit().getDeleteUser() != null  ) {
	        	affiliateDTO.getAudit().setDeleteUser(   a.getPerson().getAudit().getDeleteUser().getPerson().getFullName() );        
	        }
	        
	        
	       
	        
	        
	        
	        affiliateDTO.setAddress(  new AddressDTO()  );
	        affiliateDTO.getAddress().setStreet(     a.getPerson().getAddress().getStreet()     );
	        affiliateDTO.getAddress().setNumber(     a.getPerson().getAddress().getNumber()     );
	        affiliateDTO.getAddress().setDepartment( a.getPerson().getAddress().getDepartment() );
	        affiliateDTO.getAddress().setCity(       a.getPerson().getAddress().getCity().getName() );
	        affiliateDTO.getAddress().setCp(         a.getPerson().getAddress().getCity().getCp()   );
	        affiliateDTO.getAddress().setProv(       a.getPerson().getAddress().getCity().getProv().getName()  );
	        affiliateDTO.getAddress().setCountry(    a.getPerson().getAddress().getCity().getProv().getCountry().getName()  );
	
	        
	          
	        resultsDTO.add(affiliateDTO);
    	}
    	
    	
    	
    	entityManager.close();
    	
    	
    	
    	AffiliateSearchDTO response = new AffiliateSearchDTO(); 	
    	response.setPage_number(1);
    	response.setPage_total(1);
    	response.setResults(resultsDTO);
    	
    	return response;
    }
    
        
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public AffiliateDTO getOne(@PathParam("id") Long id) {
            
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
        
   
        
        // Fetch all the affiliates current lends
        List<Lend> lends = entityManager.createQuery(
        	    "from Lend as lend where lend.affiliate = ?1 and lend.actualReturnDate is null", Lend.class)
        	    .setParameter(1, affiliate)
        	    .getResultList();
     
        
        AffiliateLendDTO lendDTO;;
        AffiliateCopyDTO copyDTO;
        
    	for (Lend lend : lends) {


	        lendDTO = new AffiliateLendDTO();
	        copyDTO = new AffiliateCopyDTO();
	        
	        //Lend DTO
	        lendDTO.setId(                  lend.getId()                  );
	        lendDTO.setLendingDate(         lend.getLendDate()            );
	        lendDTO.setExpectedReturnDate(  lend.getExpectedReturnDate()  );        
	        lendDTO.setType(                lend.getLendType().getName()  );
	        lendDTO.setComments(            lend.getComments()            );
	        
	        // Lend DTO . copy
	        copyDTO.setId(        lend.getCopy().getId()               );
	        copyDTO.setTitle(     lend.getCopy().getBook().getTitle()  );
	        
	        lendDTO.setCopy(  copyDTO  );
	        
	        
	        //Add to the Affiliate DTO
	        dto.getLends().add(  lendDTO  );
    	}
    	
    
        
        
        
        entityManager.close();
     
        
       
        if (  affiliate.isDeleted()  ) {
        	throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        
        
    	// Can the affiliate request more lends?
        Set<Suspension> suspensions = affiliate.getSuspensions();
        boolean active = true;
        
        for (Suspension s : suspensions){
        	if (  s.getEndDate().after(new Date())  ) {
        		active = false;
        		break;
        	}
        }
        
        dto.setActive(active);
        

        
        
        
        // Transfer all the data into the DTO
        dto.setId(       affiliate.getId()  );
        dto.setName(     affiliate.getPerson().getName()  );
        dto.setLastName( affiliate.getPerson().getLastName()  );
        dto.setDni(      affiliate.getPerson().getDni()  );
        dto.setCuil(     affiliate.getPerson().getCuil()  );
        dto.setImg(      affiliate.getPerson().getImg());
        
        dto.setEmail(    affiliate.getPerson().getContact().getEmail() );
        dto.setTel(      affiliate.getPerson().getContact().getTel() );
        dto.setCel(      affiliate.getPerson().getContact().getCel() );
        
        
        dto.setReputation(  affiliate.getReputation()  );
        dto.setType( affiliate.getType().getName()  );
        
        
        dto.setAudit(  new AuditDTO()  );
        dto.getAudit().setCreateDate(   affiliate.getPerson().getAudit().getCreateDate()   );
        dto.getAudit().setEditDate(     affiliate.getPerson().getAudit().getEditDate()     );
        dto.getAudit().setDeleteDate(   affiliate.getPerson().getAudit().getDeleteDate()   );
        
        
        
        dto.getAudit().setCreateUser(   affiliate.getPerson().getAudit().getCreateUser().getPerson().getFullName() );
        
        
        if (  affiliate.getPerson().getAudit().getEditUser() != null  ){
        	dto.getAudit().setEditUser(     affiliate.getPerson().getAudit().getEditUser().getPerson().getFullName()   );
        }
        
        
        if (  affiliate.getPerson().getAudit().getDeleteUser() != null  ) {
        	dto.getAudit().setDeleteUser(   affiliate.getPerson().getAudit().getDeleteUser().getPerson().getFullName() );        
        }
        
        
       
        
        
        
        dto.setAddress(  new AddressDTO()  );
        dto.getAddress().setStreet(     affiliate.getPerson().getAddress().getStreet()     );
        dto.getAddress().setNumber(     affiliate.getPerson().getAddress().getNumber()     );
        dto.getAddress().setDepartment( affiliate.getPerson().getAddress().getDepartment() );
        dto.getAddress().setFloor(      affiliate.getPerson().getAddress().getFloor()      );
        dto.getAddress().setCity(       affiliate.getPerson().getAddress().getCity().getName() );
        dto.getAddress().setCp(         affiliate.getPerson().getAddress().getCity().getCp()   );
        dto.getAddress().setProv(       affiliate.getPerson().getAddress().getCity().getProv().getName()  );
        dto.getAddress().setCountry(    affiliate.getPerson().getAddress().getCity().getProv().getCountry().getName()  );

        
        return dto;      
    }
    
    
    
    
        
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg create(AffiliateDTO dto) throws Exception {
    	
 	
    	
    	Affiliate affiliate = new Affiliate();
    	

    	
    	// Person
    	affiliate.getPerson().setName(      dto.getName()     );
    	affiliate.getPerson().setLastName(  dto.getLastName() );
        affiliate.getPerson().setDni(       dto.getDni()      );
        affiliate.getPerson().setCuil(      dto.getCuil()     );
        affiliate.getPerson().setImg(       dto.getImg()      );
        
        
        //Contact
        affiliate.getPerson().getContact().setEmail( dto.getEmail() );
        affiliate.getPerson().getContact().setTel(   dto.getTel()   );
        affiliate.getPerson().getContact().setCel(   dto.getCel()   );
    		
    	
    	// Address    	
        affiliate.getPerson().getAddress().setStreet(     dto.getAddress().getStreet()     );
        affiliate.getPerson().getAddress().setNumber(     dto.getAddress().getNumber()     );
        affiliate.getPerson().getAddress().setDepartment( dto.getAddress().getDepartment() );
        affiliate.getPerson().getAddress().setFloor(      dto.getAddress().getFloor()      );
        
        
        affiliate.setDeleted(false);
        affiliate.setReputation( dto.getReputation()  );
    	
        
    	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
    	//This references to today
    	Date today = new Date();
    	affiliate.getPerson().getAudit().setCreateDate(today);
        
     
    	u.begin();
    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
    	
    	
    	

    	// Very simplified approach
        //Country country = entityManager.find(Country.class, dto.getAddress().getCountry()  );
    	
    	Prov prov;
    	City city;
    	
    	String cp = dto.getAddress().getCp();
    	
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
    	try {
        	city = (City) entityManager.createQuery(
        		    "select c from City as c where c.cp = ?1")
        		    .setParameter(1, cp)
        		    .getSingleResult();
 	
    		
    	} catch (NoResultException e) {
    		
    		city = new City();
    		city.setCp(cp);
    		city.setName(  dto.getAddress().getCity()  );
    		city.setProv(  prov  );
    	} 
        
    	affiliate.getPerson().getAddress().setCity(city);
    	
    	
            	
    	// Set Entity Type
    	EntityType type = entityManager.find(EntityType.class, entityTypeID);    	
    	affiliate.setType(type);
    	
    	// When login is done do this:
    	//affiliate.getPerson().getAudit().setCreateUser(current_loged_user);
    	
    	// Persist it
    	entityManager.merge(affiliate);
    	entityManager.flush(); 	
    	
    	u.commit();
        entityManager.close();
        
    	return new JsonResponseMsg("ok", "OOOHHH YEEEEAAAAAAHHHHHH!!!!");
    };
        
        
    @POST
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg edit(AffiliateDTO dto, @PathParam("id") Long id) throws Exception {
    	
    	Affiliate affiliate = new Affiliate();
    	

    	affiliate.setId(  dto.getId()  );
    	
    	// Person
    	affiliate.getPerson().setName(      dto.getName()     );
    	affiliate.getPerson().setLastName(  dto.getLastName() );
        affiliate.getPerson().setDni(       dto.getDni()      );
        affiliate.getPerson().setCuil(      dto.getCuil()     );
        affiliate.getPerson().setImg(       dto.getImg()      );
        
        
        //Contact
        affiliate.getPerson().getContact().setEmail( dto.getEmail() );
        affiliate.getPerson().getContact().setTel(   dto.getTel()   );
        affiliate.getPerson().getContact().setCel(   dto.getCel()   );
    		
    	
    	// Address    	
        affiliate.getPerson().getAddress().setStreet(     dto.getAddress().getStreet()     );
        affiliate.getPerson().getAddress().setNumber(     dto.getAddress().getNumber()     );
        affiliate.getPerson().getAddress().setDepartment( dto.getAddress().getDepartment() );
        affiliate.getPerson().getAddress().setFloor(      dto.getAddress().getFloor()      );
        
    	

    	
    	affiliate.setReputation( dto.getReputation()  );
    	
   	
    	
    	u.begin();
    	EntityManager entityManager = entityManagerFactory.createEntityManager(); 
    	
    	
    	Affiliate existing_affiliate = entityManager.find(Affiliate.class, dto.getId()  ); 
    	
    	Audit audit = existing_affiliate.getPerson().getAudit();
    	
    	
    	//http://docs.oracle.com/javase/6/docs/api/java/util/Date.html
    	//This references to today
    	audit.setEditDate(  new Date()  );
    	
    	affiliate.getPerson().setAudit(  audit  );

    	//
    	// City, Prov, Country Handling
    	//
    	
        //Country country = entityManager.find(Country.class, dto.getAddress().getCountry()  );
    	
    	Prov prov;
    	City city;
    	
    	String cp = dto.getAddress().getCp();
    	
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
    	try {
        	city = (City) entityManager.createQuery(
        		    "select c from City as c where c.cp = ?1")
        		    .setParameter(1, cp)
        		    .getSingleResult();
 	
    		
    	} catch (NoResultException e) {
    		
    		city = new City();
    		city.setCp(cp);
    		city.setName(  dto.getAddress().getCity()  );
    		city.setProv(  prov  );
    	} 
        
    	affiliate.getPerson().getAddress().setCity(city);
    	
    	
            	
    	// Set Entity Type
    	EntityType type = entityManager.find(EntityType.class, entityTypeID);    	
    	affiliate.setType(type);
    	
    	// When login is done do this:
    	//affiliate.getPerson().getAudit().setCreateUser(current_loged_user);
    	
    	// Merge it
    	entityManager.merge(affiliate);
    	entityManager.flush(); 	
    	
    	u.commit();
        entityManager.close();
        
    	return new JsonResponseMsg("ok", "OOOHHH YEEEEAAAAAAHHHHHH!!!!");
    };
        
        
    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponseMsg delete(@PathParam("id") Long id) throws Exception {
    	
    	
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
        
        return new JsonResponseMsg("ok", "successfully deleted");
    };
        
}