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
