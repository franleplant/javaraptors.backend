package org.jr.be.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Copy {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	
	//Lazy loading to avoid infinite loops while Marshalling
	@ManyToOne
	private Book book;
	
	//Physical state
	@NotEmpty
	private String state;
	
	private int editionYear;
	
	private String comments;
	
	@NotEmpty
	private Location location;
		
	@Embedded
    private Audit audit = new Audit();
	
	@NotEmpty
	@ManyToOne
	private EntityType type;
	
	@OneToMany
	private Set<LendType> lendTypes = new HashSet<LendType>();
	
	@NotEmpty
	private boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getEditionYear() {
		return editionYear;
	}

	public void setEditionYear(int editionYear) {
		this.editionYear = editionYear;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Audit getAudit() {
		return audit;
	}

	public void setAudit(Audit audit) {
		this.audit = audit;
	}

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public Set<LendType> getLendTypes() {
		return lendTypes;
	}

	public void setLendTypes(Set<LendType> lendTypes) {
		this.lendTypes = lendTypes;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Copy [book=" + book + ", editionYear=" + editionYear
				+ ", location=" + location + ", lendTypes=" + lendTypes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((audit == null) ? 0 : audit.hashCode());
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + editionYear;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lendTypes == null) ? 0 : lendTypes.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		Copy other = (Copy) obj;
		if (audit == null) {
			if (other.audit != null)
				return false;
		} else if (!audit.equals(other.audit))
			return false;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (deleted != other.deleted)
			return false;
		if (editionYear != other.editionYear)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lendTypes == null) {
			if (other.lendTypes != null)
				return false;
		} else if (!lendTypes.equals(other.lendTypes))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
}
