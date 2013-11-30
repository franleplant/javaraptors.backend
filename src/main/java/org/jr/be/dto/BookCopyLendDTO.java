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

}
