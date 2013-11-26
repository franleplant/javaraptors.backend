package org.jr.be.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Lend {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private LendType lendType;
	
	@NotNull
	private Date lendDate;
	
	@NotNull
	private Date expectedReturnDate;
	
	private Date actualReturnDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User returningUser;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private User lendingUser;
	
	private String comments;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Affiliate affiliate;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Copy copy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LendType getLendType() {
		return lendType;
	}

	public void setLendType(LendType lendType) {
		this.lendType = lendType;
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

	public Date getActualReturnDate() {
		return actualReturnDate;
	}

	public void setActualReturnDate(Date actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}

	public User getReturningUser() {
		return returningUser;
	}

	public void setReturningUser(User returningUser) {
		this.returningUser = returningUser;
	}

	public User getLendingUser() {
		return lendingUser;
	}

	public void setLendingUser(User lendingUser) {
		this.lendingUser = lendingUser;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Affiliate getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(Affiliate affiliate) {
		this.affiliate = affiliate;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((actualReturnDate == null) ? 0 : actualReturnDate.hashCode());
		result = prime * result
				+ ((affiliate == null) ? 0 : affiliate.hashCode());
		result = prime
				* result
				+ ((expectedReturnDate == null) ? 0 : expectedReturnDate
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lendDate == null) ? 0 : lendDate.hashCode());
		result = prime * result
				+ ((lendType == null) ? 0 : lendType.hashCode());
		result = prime * result
				+ ((lendingUser == null) ? 0 : lendingUser.hashCode());
		result = prime * result
				+ ((returningUser == null) ? 0 : returningUser.hashCode());
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
		Lend other = (Lend) obj;
		if (actualReturnDate == null) {
			if (other.actualReturnDate != null)
				return false;
		} else if (!actualReturnDate.equals(other.actualReturnDate))
			return false;
		if (affiliate == null) {
			if (other.affiliate != null)
				return false;
		} else if (!affiliate.equals(other.affiliate))
			return false;
		if (copy == null) {
			if (other.copy != null)
				return false;
		} else if (!copy.equals(other.copy))
			return false;
		if (expectedReturnDate == null) {
			if (other.expectedReturnDate != null)
				return false;
		} else if (!expectedReturnDate.equals(other.expectedReturnDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lendDate == null) {
			if (other.lendDate != null)
				return false;
		} else if (!lendDate.equals(other.lendDate))
			return false;
		if (lendType == null) {
			if (other.lendType != null)
				return false;
		} else if (!lendType.equals(other.lendType))
			return false;
		if (lendingUser == null) {
			if (other.lendingUser != null)
				return false;
		} else if (!lendingUser.equals(other.lendingUser))
			return false;
		if (returningUser == null) {
			if (other.returningUser != null)
				return false;
		} else if (!returningUser.equals(other.returningUser))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lend [lendType=" + lendType + ", lendDate=" + lendDate
				+ ", expectedReturnDate=" + expectedReturnDate
				+ ", actualReturnDate=" + actualReturnDate + ", returningUser="
				+ returningUser + ", lendingUser=" + lendingUser
				+ ", comments=" + comments + ", affiliate=" + affiliate
				+ ", copy=" + copy + "]";
	}
}
