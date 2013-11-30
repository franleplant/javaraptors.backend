package org.jr.be.dto;

import org.jr.be.model.Address;


public class AddressDTO {
	
	private String street;
	
	private int number;
	
	private String department;
	
	private String floor;
	
	private String city;
	
	private String cp;
	
	private String prov;
	
	private String country;

	public void toDTO(Address address) {
		
		
		street = 		address.getStreet();
		number = 		address.getNumber();
		department = 	address.getDepartment();
		floor = 		address.getFloor();
		city = 			address.getCity().getName();
		cp = 			address.getCity().getCp();
		prov = 			address.getCity().getProv().getName();
		country = 		address.getCity().getProv().getCountry().getName();

	}
	
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
