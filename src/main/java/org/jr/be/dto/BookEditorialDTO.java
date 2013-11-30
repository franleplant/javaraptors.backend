package org.jr.be.dto;

import org.jr.be.model.Editorial;

public class BookEditorialDTO {
	
	private long id;
	private String name;
	
	public void toDTO(  Editorial editorial  ){
		id = editorial.getId();
		name = editorial.getName();
	}

}
