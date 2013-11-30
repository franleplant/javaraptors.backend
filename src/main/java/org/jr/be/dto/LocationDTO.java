package org.jr.be.dto;

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

}
