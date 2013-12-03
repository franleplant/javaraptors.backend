package org.jr.be.dto;

import org.jr.be.model.Copy;

public class AffiliateCopyDTO {

	//Copys book id
	private long id;
	
	private String title;
	
	public void toDTO(Copy copy){
		id = copy.getBook().getId();;
		title = copy.getBook().getTitle();
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
