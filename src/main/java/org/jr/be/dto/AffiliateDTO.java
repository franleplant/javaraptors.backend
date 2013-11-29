package org.jr.be.dto;

import java.util.HashSet;
import java.util.Set;

public class AffiliateDTO {

	private Long id;
	
	private String name;
	
	private String lastName;
	
	private String dni;
	
	private String cuil;
	
	private String email;
	
	private long tel;
	
	private long cel;
		
	private double reputation;
	
	private String type;
	
	private String img;
	
	// An affiliate can be unable to request more lends due to several suspensions in curse
	private boolean isActive;	
	
	private AuditDTO audit;
	
	private AddressDTO address;

	
	private Set<AffiliateLendDTO> lends = new HashSet<AffiliateLendDTO>(); 
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getTel() {
		return tel;
	}

	public void setTel(long tel) {
		this.tel = tel;
	}

	public long getCel() {
		return cel;
	}

	public void setCel(long cel) {
		this.cel = cel;
	}

	public double getReputation() {
		return reputation;
	}

	public void setReputation(double reputation) {
		this.reputation = reputation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public AuditDTO getAudit() {
		return audit;
	}

	public void setAudit(AuditDTO audit) {
		this.audit = audit;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public Set<AffiliateLendDTO> getLends() {
		return lends;
	}

	public void setLends(Set<AffiliateLendDTO> lends) {
		this.lends = lends;
	}
	

	

	
	
	
	
}
