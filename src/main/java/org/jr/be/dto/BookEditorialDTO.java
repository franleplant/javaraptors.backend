package org.jr.be.dto;

import org.jr.be.model.Editorial;

public class BookEditorialDTO {
	
	private long id;
	private String name;
	
	public void toDTO(  Editorial editorial  ){
		id = editorial.getId();
		name = editorial.getName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
