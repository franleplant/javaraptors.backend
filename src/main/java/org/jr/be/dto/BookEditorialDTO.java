package org.jr.be.dto;

import java.util.Date;

import javax.persistence.EntityManager;

import org.jr.be.model.Editorial;

public class BookEditorialDTO {
	
	private long id;
	private String name;
	
	public void toDTO(  Editorial editorial  ){
		id = editorial.getId();
		name = editorial.getName();
	}
	
	public Editorial toEntity(){
		Editorial editorial = new Editorial();
		
		editorial.setName(name);
		editorial.setDeleted(false);
		editorial.getAudit().setCreateDate(  new Date()  );
		//editorial.getAudit().setCreateUser( session user);


		
		return editorial;
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
