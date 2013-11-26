package org.jr.be.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jr.be.model.Affiliate;


@Path("/affiliate")
public class AffiliateService {
	


    private Class<Affiliate> entityClass = Affiliate.class;
  
    /*
    
	@Inject
    private EntityManager entityManager;
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
	*/
    
    
    @PersistenceUnit(unitName = "primary")
    private EntityManagerFactory entityManagerFactory;
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Affiliate getOne(@PathParam("id") Long id) {
		
		
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
        Affiliate affiliate = null;
        
        affiliate = entityManager.find(Affiliate.class, id);
        
        affiliate.getLends();
        
        affiliate.getSuspensions();
        
        entityManager.close();
        
        
        return affiliate;
		
		
		/*
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Affiliate> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        
        Root<Affiliate> root = criteriaQuery.from(entityClass);
        
        Predicate condition = criteriaBuilder.equal(root.get("id"), id);
        
        criteriaQuery.select(criteriaBuilder.createQuery(entityClass).getSelection()).
        	where(condition);
        
        Affiliate affiliate = entityManager.createQuery(criteriaQuery).getSingleResult();
        
        
        //return affiliate;
         * 
         * 
         */
    
		/*
        EntityManager entityManager = PersistenceManager.getEntityManager();
        
        Affiliate affiliate = null;
        
        affiliate = entityManager.find(Affiliate.class, id);
        
        entityManager.close();
        
        
        return affiliate;
        
        
        */
		

		
		
	}
	
}
	
	
	
	
	
	
	
	
	
	