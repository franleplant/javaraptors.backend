package org.jr.be.dto;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jr.be.model.Location;

public class LocationDTO {
	private long id;
	private String shelves;
	private String shelf;
	
	
	public void toDTO(  Location location  ){
		id = location.getId();
		shelves = location.getShelves();
		shelf = location.getShelf();
	}

	public Location toEntity(  EntityManager em ) {
		Location location = new Location();
		
		
		
		
		if ( id != 0) {
			
			try {
		    	Location existing_location = em.createQuery(
			     	    "from Author as a where a.id = ?1", Location.class)
			     	    .setParameter(1, id)
			     	    .getSingleResult();
		    	
		    	location = existing_location;
			} catch(NoResultException ex) {
			
					//Something really bad happened
			}

		} else {
			location.setShelves(shelves);
			location.setShelf(shelf);
			location.setDeleted(false);
			
			em.persist(location);
			em.flush();
			
		}	

			
		
		return location;
	}
	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getShelves() {
		return shelves;
	}


	public void setShelves(String shelves) {
		this.shelves = shelves;
	}


	public String getShelf() {
		return shelf;
	}


	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

}
