package org.jr.be.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

public class Book {
	
	// Synthetic id
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	
	@NotEmpty
	@Column(unique = true)
	private String isbn;
	
	
	@NotEmpty
	private String title;
	
	//Bidirectional
	@NotEmpty
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy="books")
	private Set<Author> authors = new HashSet<Author>();
	
	@NotEmpty
	@ManyToMany(cascade = CascadeType.PERSIST)
	private Set<Genre> genres = new HashSet<Genre>();
	
	private String editionNumber;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Country editionCountry;
	
	@NotEmpty
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Editorial editorial;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy="book")
	private Set<Copy> copys = new HashSet<Copy>();
	
	private String img;
	
	private String summary;
	
	private String lang;
	
	@NotEmpty
	private double val;
	
	@NotEmpty
	private String price;
	
	private String comments;
	
	@Embedded
    private Audit audit = new Audit();
	
	@NotEmpty
	@ManyToOne
	private EntityType type;
	
	@NotEmpty
	private boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public String getEditionNumber() {
		return editionNumber;
	}

	public void setEditionNumber(String editionNumber) {
		this.editionNumber = editionNumber;
	}

	public Country getEditionCountry() {
		return editionCountry;
	}

	public void setEditionCountry(Country editionCountry) {
		this.editionCountry = editionCountry;
	}

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	public Set<Copy> getCopys() {
		return copys;
	}

	public void setCopys(Set<Copy> copys) {
		this.copys = copys;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", authors="
				+ authors + ", genres=" + genres + ", editionNumber="
				+ editionNumber + ", editionCountry=" + editionCountry
				+ ", editorial=" + editorial + ", audit=" + audit + ", type="
				+ type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((audit == null) ? 0 : audit.hashCode());
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((copys == null) ? 0 : copys.hashCode());
		result = prime * result
				+ ((editionCountry == null) ? 0 : editionCountry.hashCode());
		result = prime * result
				+ ((editionNumber == null) ? 0 : editionNumber.hashCode());
		result = prime * result
				+ ((editorial == null) ? 0 : editorial.hashCode());
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		long temp;
		temp = Double.doubleToLongBits(val);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Book other = (Book) obj;
		if (audit == null) {
			if (other.audit != null)
				return false;
		} else if (!audit.equals(other.audit))
			return false;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (copys == null) {
			if (other.copys != null)
				return false;
		} else if (!copys.equals(other.copys))
			return false;
		if (editionCountry == null) {
			if (other.editionCountry != null)
				return false;
		} else if (!editionCountry.equals(other.editionCountry))
			return false;
		if (editionNumber == null) {
			if (other.editionNumber != null)
				return false;
		} else if (!editionNumber.equals(other.editionNumber))
			return false;
		if (editorial == null) {
			if (other.editorial != null)
				return false;
		} else if (!editorial.equals(other.editorial))
			return false;
		if (genres == null) {
			if (other.genres != null)
				return false;
		} else if (!genres.equals(other.genres))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (Double.doubleToLongBits(val) != Double.doubleToLongBits(other.val))
			return false;
		return true;
	}


}
