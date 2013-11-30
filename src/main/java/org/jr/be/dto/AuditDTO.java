package org.jr.be.dto;

import java.util.Date;

import org.jr.be.model.Audit;

public class AuditDTO {
	
	private Date createDate;
	private Date editDate;
	private Date deleteDate;
	
	private String createUser;
	private String editUser;
	private String deleteUser;
	
	public void toDTO(Audit audit){
		createDate = audit.getCreateDate();
		editDate = audit.getEditDate();
		deleteDate = audit.getDeleteDate();
		createUser = audit.getCreateUser().getPerson().getFullName();
		
        
        
        if (  audit.getEditUser() != null  ){
        	editUser =    audit.getEditUser().getPerson().getFullName();
        }
        
        
        if (  audit.getDeleteUser() != null  ) {
        	deleteUser =    audit.getDeleteUser().getPerson().getFullName();        
        }
	}
	
	
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getEditUser() {
		return editUser;
	}
	public void setEditUser(String editUser) {
		this.editUser = editUser;
	}
	public String getDeleteUser() {
		return deleteUser;
	}
	public void setDeleteUser(String deleteUser) {
		this.deleteUser = deleteUser;
	}
	

}
