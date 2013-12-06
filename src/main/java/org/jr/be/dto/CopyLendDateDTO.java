package org.jr.be.dto;

import java.util.Date;

public class CopyLendDateDTO {
	
	private Date today;
	private Date max;
	
	
	public Date getToday() {
		return today;
	}
	public void setToday(Date today) {
		this.today = today;
	}
	public Date getMax() {
		return max;
	}
	public void setMax(Date max) {
		this.max = max;
	}

}
