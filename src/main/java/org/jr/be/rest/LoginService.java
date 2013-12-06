package org.jr.be.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jr.be.dto.UserDTO;
import org.jr.be.model.Book;
import org.jr.be.model.City;
import org.jr.be.model.Prov;
import org.jr.be.model.User;
import org.jr.be.util.JsonResponseMsg;
import java.io.*;
import java.net.*;
import java.util.*;
@Path("/login")
public class LoginService {
    
@PersistenceUnit(unitName = "primary")
private EntityManagerFactory entityManagerFactory;

@Resource
private UserTransaction u;
 


@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public JsonResponseMsg checkLogin(UserDTO dto) throws Exception {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();   
		
		User user;

		// Search by password and user
	   	try {  		
    		user = (User) entityManager.createQuery(
        		    "select u from User as u where u.password = ?1 and u.person.contact.email = ?2 ")
        		    .setParameter(1, dto.getPass() )
        		    .setParameter(2, dto.getUser() )
        		    .getSingleResult();
    		
    		System.out.println("-----------  "+ user.getId() +" -----------");
    		return new JsonResponseMsg("status", "ok");
    		
    	} catch (NoResultException e) {
    		
        	entityManager.close();
        	System.out.println("-----------  error -----------");
    		return new JsonResponseMsg("status", "error");
    		
    	}
		
		
		
		
		
};
 
 
	
	
	
 
}