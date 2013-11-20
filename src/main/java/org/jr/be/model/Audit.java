package org.jr.be.model;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class Audit {
	
	@NotEmpty
	@OneToOne
	private User createUser;
	
	@NotEmpty
	@Temporal(DATE)
	private Date createDate;	
	
	@OneToOne
	private User editUser;
	
	@Temporal(DATE)
	private Date editDate;	
	
	@OneToOne
	private User deleteUser;
	
	@Temporal(DATE)
	private Date deleteDate;

}
