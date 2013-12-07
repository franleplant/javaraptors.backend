package org.jr.be.dto;

import org.jr.be.model.Affiliate;

public class CopyReturnAffiliateDTO {
	
	private long id;
	private String name;
	private String lastName;
	
	
	public void toDTO(  Affiliate affiliate ) {
		id = affiliate.getId();
		name = affiliate.getPerson().getName();
		lastName = affiliate.getPerson().getLastName();
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


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
