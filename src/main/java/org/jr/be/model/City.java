package org.jr.be.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;





@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name", "prov_id" }))
public class City {

	//Synthetic
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	
	
	@NotEmpty
    @Size(min = 2, message = "Too short")
	private String name;
	
	@NotEmpty
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Prov prov;
	
	@Column(unique = true)
	@NotEmpty
	private String cp;

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

	public Prov getProv() {
		return prov;
	}

	public void setProv(Prov prov) {
		this.prov = prov;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", prov=" + prov + ", cp=" + cp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cp == null) ? 0 : cp.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prov == null) ? 0 : prov.hashCode());
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
		City other = (City) obj;
		if (cp == null) {
			if (other.cp != null)
				return false;
		} else if (!cp.equals(other.cp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (prov == null) {
			if (other.prov != null)
				return false;
		} else if (!prov.equals(other.prov))
			return false;
		return true;
	}
	
	
}
