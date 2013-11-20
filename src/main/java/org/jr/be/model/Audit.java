package org.jr.be.model;

import javax.persistence.Embeddable;

@Embeddable
public class Audit {
	
	private int removeMe;

	public int getRemoveMe() {
		return removeMe;
	}

	public void setRemoveMe(int removeMe) {
		this.removeMe = removeMe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + removeMe;
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
		if (removeMe != other.removeMe)
			return false;
		return true;
	}

}
