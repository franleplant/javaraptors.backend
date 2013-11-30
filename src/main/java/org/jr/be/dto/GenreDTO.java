package org.jr.be.dto;

import org.jr.be.model.Genre;

public class GenreDTO {
	
	private String name;
	
	public void toDTO(  Genre genre  ){
		name = genre.getName();
		
	}
	
}
