package org.jr.be.dto;

import org.jr.be.model.Genre;

public class GenreDTO {
	
	private String name;
	
	public void toDTO(  Genre genre  ){
		name = genre.getName();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
