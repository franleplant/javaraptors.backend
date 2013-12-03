package org.jr.be.dto;

import java.util.Date;

import org.jr.be.model.Lend;




// This will contain a single affiliate lend info
public class AffiliateLendDTO {
	
	private long id;
	
	// Lend Type
	private String type;
	
	private Date lendingDate;
	
	private Date expectedReturnDate;
	
	private String comments;
	
	private AffiliateCopyDTO copy;
	
	public void toDTO(Lend lend) {
		
		id = lend.getId();
		type = lend.getLendType().getName();
		lendingDate = lend.getLendDate();
		expectedReturnDate = getExpectedReturnDate();
		comments = lend.getComments();
        
		AffiliateCopyDTO copyDTO = new AffiliateCopyDTO();
		copyDTO.toDTO(lend.getCopy());
		
		copy = copyDTO;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
