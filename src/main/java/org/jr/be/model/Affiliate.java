package org.jr.be.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
public class Affiliate {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	private double reputation;
	
	@Embedded
	private Person person = new Person();
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private EntityType type;
	
	@OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Set<Suspension> suspensions = new HashSet<Suspension>();
	
	@NotNull
	private boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getReputation() {
		return reputation;
	}

	public void setReputation(double reputation) {
		this.reputation = reputation;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}


	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public Set<Suspension> getSuspensions() {
		return suspensions;
	}

	public void setSuspensions(Set<Suspension> suspensions) {
		this.suspensions = suspensions;
	}

	

	@Override
	public String toString() {
		return "Affiliate [reputation=" + reputation + ", person=" + person
				+ ", deleted=" + deleted + "]";
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		long temp;
		temp = Double.doubleToLongBits(reputation);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((suspensions == null) ? 0 : suspensions.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Affiliate other = (Affiliate) obj;
		if (deleted != other.deleted)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (Double.doubleToLongBits(reputation) != Double
				.doubleToLongBits(other.reputation))
			return false;
		if (suspensions == null) {
			if (other.suspensions != null)
				return false;
		} else if (!suspensions.equals(other.suspensions))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
}
