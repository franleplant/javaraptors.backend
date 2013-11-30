package org.jr.be.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.jr.be.model.Copy;
import org.jr.be.model.Lend;
import org.jr.be.model.LendType;

public class BookCopyDTO {
	private long id;
	private String state;
	private int editionYear;	
	private String comments;
	private Set<String> lendTypes = new HashSet<String>();
	private BookCopyLendDTO lend = new BookCopyLendDTO();
	private LocationDTO location = new LocationDTO();
	private AuditDTO audit = new AuditDTO();
	
	public void toDTO(  Copy copy, EntityManager em  ){
		id = copy.getId();
		state = copy.getState();
		editionYear = copy.getEditionYear();
		comments = copy.getComments();
		
		Set<LendType> lt_entities = copy.getLendTypes();
		for ( LendType lt : lt_entities){
			lendTypes.add(  lt.getName()  );
		}
		
	    Lend lend_entity = em.createQuery(
	     	    "from Lend as lend where lend.copy = ?1 and lend.actualReturnDate is null", Lend.class)
	     	    .setParameter(1, copy)
	     	    .getSingleResult();
	    
	    lend.toDTO(  lend_entity  );
	    
	    location.toDTO(  copy.getLocation()  );
	    
	    
	    audit.toDTO(  copy.getAudit()  );
		
	}

}
