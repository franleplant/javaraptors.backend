package org.jr.be.dto;


public class ReportLendDTO {
	
	//book id
	private long id;
	private String title;
	
	private int month;
	private int year;
	private Long lend_number;



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


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public Long getLend_number() {
		return lend_number;
	}


	public void setLend_number(Long lend_number) {
		this.lend_number = lend_number;
	}
}
