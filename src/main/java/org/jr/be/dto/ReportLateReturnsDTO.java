package org.jr.be.dto;

import java.util.Date;

import org.jr.be.model.Lend;

public class ReportLateReturnsDTO {

	// Book id
	private long id;
	private long copy_id;
	private String title;	
	private CopyReturnAffiliateDTO affiliate = new CopyReturnAffiliateDTO();
	private String lendType;
	private String lendingUser;
	private Date lendDate;
	private Date expectedReturnDate;
	
	
	
	
	private int delayed_days;
	
	

	
	
	
	public void toDTO(  Lend lend  ) {
		id = lend.getCopy().getBook().getId();
		copy_id = lend.getCopy().getId();
		title = lend.getCopy().getBook().getTitle();
		affiliate.toDTO(  lend.getAffiliate() );
	
		lendType = lend.getLendType().getName();
		lendingUser = lend.getLendingUser().getPerson().getFullName();
		lendDate = lend.getLendDate();
		expectedReturnDate = lend.getExpectedReturnDate();
		
		Date today = new Date();
		
        if ( today.getTime() > lend.getExpectedReturnDate().getTime() ) {
    		long diff = today.getTime() - lend.getExpectedReturnDate().getTime();
    		long diffDays = diff / (24 * 60 * 60 * 1000);
    		
    		
    		delayed_days = (int) diffDays;
            
               
        } else {
        	delayed_days = 0;
        }
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getCopy_id() {
		return copy_id;
	}


	public void setCopy_id(long copy_id) {
		this.copy_id = copy_id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public CopyReturnAffiliateDTO getAffiliate() {
		return affiliate;
	}


	public void setAffiliate(CopyReturnAffiliateDTO affiliate) {
		this.affiliate = affiliate;
	}


	public int getDelayed_days() {
		return delayed_days;
	}


	public void setDelayed_days(int delayed_days) {
		this.delayed_days = delayed_days;
	}


	public String getLendType() {
		return lendType;
	}


	public void setLendType(String lendType) {
		this.lendType = lendType;
	}


	public String getLendingUser() {
		return lendingUser;
	}


	public void setLendingUser(String lendingUser) {
		this.lendingUser = lendingUser;
	}


	public Date getLendDate() {
		return lendDate;
	}


	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}


	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}


	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}
}
