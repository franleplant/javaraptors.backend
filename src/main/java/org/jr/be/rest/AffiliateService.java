package org.jr.be.rest;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jr.be.model.Affiliate;


@Path("/affiliate")
public class AffiliateService {
	
	@Inject
    private EntityManager entityManager;

    private Class<Affiliate> entityClass = Affiliate.class;
    
    public EntityManager getEntityManager() {
        return entityManager;
    }
	
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Affiliate getOne(@PathParam("id") Long id) {
		
		final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Affiliate> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        
        Root<Affiliate> root = criteriaQuery.from(entityClass);
        
        Predicate condition = criteriaBuilder.equal(root.get("id"), id);
        
        criteriaQuery.select(criteriaBuilder.createQuery(entityClass).getSelection()).
        	where(condition);
        
        Affiliate affiliate = entityManager.createQuery(criteriaQuery).getSingleResult();
        
        
        return affiliate;
	}
	
}
	
	
	
	
	
	
	
	
	
	