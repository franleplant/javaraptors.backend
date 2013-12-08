package org.jr.be.dto;

import org.jr.be.model.Contact;

public class ContactDTO {

	private String email;
	private Long tel;
	private Long cel;
	private String web;
	
	
	public void toDTO(Contact contact){
          email = contact.getEmail();
          tel = contact.getTel();
          cel = contact.getCel();
          web = contact.getWeb();}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getTel() {
		return tel;
	}


	public void setTel(Long tel) {
		this.tel = tel;
	}


	public Long getCel() {
		return cel;
	}


	public void setCel(Long cel) {
		this.cel = cel;
	}


	public String getWeb() {
		return web;
	}


	public void setWeb(String web) {
		this.web = web;
	}
	
	
}
