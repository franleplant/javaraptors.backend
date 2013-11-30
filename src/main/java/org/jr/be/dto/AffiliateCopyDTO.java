package org.jr.be.dto;

import org.jr.be.model.Copy;

public class AffiliateCopyDTO {

	//Copys book id
	private Long id;
	
	private String title;
	
	public void toDTO(Copy copy){
		id = copy.getBook().getId();;
		title = copy.getBook().getTitle();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
