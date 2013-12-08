package org.jr.be.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jr.be.dto.AffiliateCopyDTO;
import org.jr.be.dto.UserDTO;
import org.jr.be.model.User;
import org.jr.be.util.JsonResponseMsg;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceUnit(unitName = "primary")
	private EntityManagerFactory entityManagerFactory;
	
	User user;
	

	
	
	

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	

    	

    	System.out.println("----------- Esta clase anda, LoginServlet.java  -----------");

    }

    
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
 
        // get request parameters for userID and password
        String userMail = request.getParameter("user");
        String pwd = request.getParameter("pwd");
       
        if (checkLogin(pwd, userMail))
        {
        	System.out.println("----------- login post OK  -----------");
        	
        	
        	HttpSession session = request.getSession();
            session.setAttribute("user", userMail);
            //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(-1);
        	
            Cookie userCookie = new Cookie("user",userMail);
            Cookie roleCookie = new Cookie("roleID", user.getRol().getId().toString());
               
            //setting cookie to expiry in 30 mins
            userCookie.setMaxAge(-1);
            response.addCookie(userCookie);
            roleCookie.setMaxAge(-1);
            response.addCookie(roleCookie);

        	response.sendRedirect("loginSuccess.html");
    		
    	} else {
    		System.out.println("----------- login post ERROR  -----------");
    		//response.sendRedirect("loginFailed.html");
    		throw new WebApplicationException(Response.Status.NOT_FOUND);
    	}	
			
				
	
				
    }
    
	public Boolean checkLogin(String pwdPar, String userMailPar) {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();   
		
		
		

		
		// Search by password and user
	   	try {  		
    		user = (User) entityManager.createQuery(
        		    "select u from User as u where u.password = ?1 and u.person.contact.email = ?2 ")
        		    .setParameter(1,pwdPar  )
        		    .setParameter(2,userMailPar )
        		    .getSingleResult();
    		
    		System.out.println("-----------  "+ user.getId() +" -----------");
    		return true;
    		
    	} catch (NoResultException e) {
    		
        	entityManager.close();
        	
        	System.out.println("-----------  error -----------");
        	return false;
    		
    	}
		
		
	}
    

    	
    

	
}
