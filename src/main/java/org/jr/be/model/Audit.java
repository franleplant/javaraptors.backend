package org.jr.be.model;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Embeddable
public class Audit {

	@ManyToOne
	private User createUser;

	@NotNull
	@Temporal(DATE)
	private Date createDate;

	@ManyToOne
	private User editUser;

	@Temporal(DATE)
	private Date editDate;

	@ManyToOne
	private User deleteUser;

	@Temporal(DATE)
	private Date deleteDate;

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getEditUser() {
		return editUser;
	}

	public void setEditUser(User editUser) {
		this.editUser = editUser;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public User getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(User deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((createUser == null) ? 0 : createUser.hashCode());
		result = prime * result
				+ ((deleteDate == null) ? 0 : deleteDate.hashCode());
		result = prime * result
				+ ((deleteUser == null) ? 0 : deleteUser.hashCode());
		result = prime * result
				+ ((editDate == null) ? 0 : editDate.hashCode());
		result = prime * result
				+ ((editUser == null) ? 0 : editUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Audit other = (Audit) obj;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (createUser == null) {
			if (other.createUser != null)
				return false;
		} else if (!createUser.equals(other.createUser))
			return false;
		if (deleteDate == null) {
			if (other.deleteDate != null)
				return false;
		} else if (!deleteDate.equals(other.deleteDate))
			return false;
		if (deleteUser == null) {
			if (other.deleteUser != null)
				return false;
		} else if (!deleteUser.equals(other.deleteUser))
			return false;
		if (editDate == null) {
			if (other.editDate != null)
				return false;
		} else if (!editDate.equals(other.editDate))
			return false;
		if (editUser == null) {
			if (other.editUser != null)
				return false;
		} else if (!editUser.equals(other.editUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Audit [createUser=" + createUser + ", createDate=" + createDate
				+ ", editUser=" + editUser + ", editDate=" + editDate
				+ ", deleteUser=" + deleteUser + ", deleteDate=" + deleteDate
				+ "]";
	}

}
