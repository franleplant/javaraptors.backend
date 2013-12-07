package org.jr.be.dto;

import java.util.Date;

public class CopyLendPostDTO {
	
	//Copy id
	private long id;
	private long affiliate_id;
	private Date expectedReturnDate;
	private String lend_comments;
	private String lend_type;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAffiliate_id() {
		return affiliate_id;
	}
	public void setAffiliate_id(long affiliate_id) {
		this.affiliate_id = affiliate_id;
	}
	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}
	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}
	public String getLend_comments() {
		return lend_comments;
	}
	public void setLend_comments(String lend_comments) {
		this.lend_comments = lend_comments;
	}
	public String getLend_type() {
		return lend_type;
	}
	public void setLend_type(String lend_type) {
		this.lend_type = lend_type;
	}

}
