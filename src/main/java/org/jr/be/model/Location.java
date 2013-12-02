package org.jr.be.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Location {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	@NotEmpty
	private String shelves;
	
	@NotEmpty
	private String shelf;
	
	//Deprecating
	@ManyToOne(fetch = FetchType.EAGER)
	private EntityType type;
	
	@NotNull
	private Boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShelves() {
		return shelves;
	}

	public void setShelves(String shelves) {
		this.shelves = shelves;
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shelf == null) ? 0 : shelf.hashCode());
		result = prime * result + ((shelves == null) ? 0 : shelves.hashCode());
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
		Location other = (Location) obj;
		if (shelf == null) {
			if (other.shelf != null)
				return false;
		} else if (!shelf.equals(other.shelf))
			return false;
		if (shelves == null) {
			if (other.shelves != null)
				return false;
		} else if (!shelves.equals(other.shelves))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Location [shelves=" + shelves + ", shelf=" + shelf + "]";
	}

}
