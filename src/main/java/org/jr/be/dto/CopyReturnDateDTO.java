package org.jr.be.dto;

import java.util.Date;

import org.jr.be.model.Lend;

public class CopyReturnDateDTO {
	
	private Date lend;
	private Date today;
	private Date expectedReturn;
	
	
	public void toDTO(  Lend lend_entity ) {
		lend = lend_entity.getLendDate();
		today = new Date();
		expectedReturn = lend_entity.getExpectedReturnDate();
	}
	
	public Date getLend() {
		return lend;
	}
	public void setLend(Date lend) {
		this.lend = lend;
	}
	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}
	public Date getExpectedReturn() {
		return expectedReturn;
	}
	public void setExpectedReturn(Date expectedReturn) {
		this.expectedReturn = expectedReturn;
	}

}
