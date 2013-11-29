package org.jr.be.dto;

import java.util.Date;




// This will contain a single affiliate lend info
public class AffiliateLendDTO {
	
	private Long id;
	
	// Lend Type
	private String type;
	
	private Date lendingDate;
	
	private Date expectedReturnDate;
	
	private String comments;
	
	private AffiliateCopyDTO copy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getLendingDate() {
		return lendingDate;
	}

	public void setLendingDate(Date lendingDate) {
		this.lendingDate = lendingDate;
	}

	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public AffiliateCopyDTO getCopy() {
		return copy;
	}

	public void setCopy(AffiliateCopyDTO copy) {
		this.copy = copy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
