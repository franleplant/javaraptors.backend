package org.jr.be.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
		
		//TODO: if in copy.isDeleted()
		id = copy.getId();
		state = copy.getState();
		editionYear = copy.getEditionYear();
		comments = copy.getComments();
		
		Set<LendType> lt_entities = copy.getLendTypes();
		for ( LendType lt : lt_entities){
			lendTypes.add(  lt.getName()  );
		}
		
		
        try {
    	    Lend lend_entity = em.createQuery(
    	     	    "from Lend as lend where lend.copy = ?1 and lend.actualReturnDate is null", Lend.class)
    	     	    .setParameter(1, copy)
    	     	    .getSingleResult();
    	    lend.toDTO(  lend_entity  );
 	
        } catch(NoResultException ex) {
        	
        	//Dont do anything
        } 

	    
	    
	    
	    location.toDTO(  copy.getLocation()  );
	    
	    
	    audit.toDTO(  copy.getAudit()  );
		
	}
	
	
	public Copy toEntity(){
		Copy copy = new Copy();
		
				
		copy.setState(state);
		copy.setEditionYear(editionYear);
		copy.setComments(comments);
		copy.setDeleted(false);
		
		
		
		return copy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getEditionYear() {
		return editionYear;
	}

	public void setEditionYear(int editionYear) {
		this.editionYear = editionYear;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Set<String> getLendTypes() {
		return lendTypes;
	}

	public void setLendTypes(Set<String> lendTypes) {
		this.lendTypes = lendTypes;
	}

	public BookCopyLendDTO getLend() {
		return lend;
	}

	public void setLend(BookCopyLendDTO lend) {
		this.lend = lend;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public AuditDTO getAudit() {
		return audit;
	}

	public void setAudit(AuditDTO audit) {
		this.audit = audit;
	}

}
