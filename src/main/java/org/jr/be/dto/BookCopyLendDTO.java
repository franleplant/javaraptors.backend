package org.jr.be.dto;

import java.util.Date;

import org.jr.be.model.Lend;

public class BookCopyLendDTO {
	private String lendType;
    private Date lendingDate;
    private Date expectedReturnDate;
    private String comments;
    
    public void toDTO(  Lend lend ){
    	lendType = lend.getLendType().getName();
    	lendingDate = lend.getLendDate();
    	expectedReturnDate = lend.getExpectedReturnDate();
    	comments = lend.getComments();
    	
    }

	public String getLendType() {
		return lendType;
	}

	public void setLendType(String lendType) {
		this.lendType = lendType;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
