package org.jr.be.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	
	
   	private static final long serialVersionUID = 1L;

//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	
//    	
//		HttpSession session = request.getSession();
//    	
//
//    	System.out.println("----------- Esta clase anda, LogoutServlet.java  -----------");
//    	
//    	System.out.println("----------- " + session.getId() + "  -----------");
//
//    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("JSESSIONID")){
                System.out.println("JSESSIONID="+cookie.getValue());
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
        }
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        System.out.println("----------- User="+session.getAttribute("user"));
        if(session != null){
            session.invalidate();
        }
        
        response.sendRedirect("frontend/app/login.html");
    }

}
