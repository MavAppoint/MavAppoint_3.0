package uta.mav.appoint;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.CreateAdvisorBean;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.Department;
import uta.mav.appoint.login.LoginUser;
import uta.mav.appoint.visitor.AppointmentVisitor;
import uta.mav.appoint.visitor.Visitor;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;

/*
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
*/

/**
 * Servlet implementation class ViewAppointmentServlet
 */
public class CreateAdvisorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private ArrayList<Department> departments;
    private HttpSession session;   
    private String header;
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		
		try {
			DatabaseManager dbm = new DatabaseManager();
			departments = dbm.getDepartments();
			session.setAttribute("departments", departments);
		} catch(Exception e){
			System.out.println(e+" RegisterServlet");
		}
		
		if (user == null){
				user = new LoginUser();
				session.setAttribute("user", user);
				response.sendRedirect("/WEB-INF/jsp/views/login.jsp");		
		}
		else{
			try{
				header = "templates/" + user.getHeader() + ".jsp";
			}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
		
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/create_advisor.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		try{
			AdvisorUser advisorUser = new AdvisorUser();
			advisorUser.setEmail((String)request.getParameter("emailAddress"));
			advisorUser.setPassword("newadvisor!@3");
			advisorUser.setNotification("day");
			advisorUser.setPname((String)request.getParameter("pname"));
			
			ArrayList<String> departmentsSelected = new ArrayList<String>();
			String departmentFound = departments.get(Integer.valueOf(request.getParameter("drp_department"))).getName();
			departmentsSelected.add(departmentFound);
			advisorUser.setDepartments(departmentsSelected);
			
			advisorUser.setMajors(departments.get(Integer.valueOf(request.getParameter("drp_department"))).getMajors());
			
			advisorUser.setNameLow("A");
			advisorUser.setNameHigh("Z");
			advisorUser.setDegType(7);
			advisorUser.setIsLead(Integer.valueOf(request.getParameter("isLead")));
			
			try{
				DatabaseManager dbm = new DatabaseManager();
				if (dbm.createAdvisor(advisorUser)){
					user.setMsg("Advisor account created with password \""+advisorUser.getPassword()+"\".");
				}
				else{
					user.setMsg("Error: Cannot create account.");
				}
			}
			catch(Exception e){
				user.setMsg("Unable to create advisor..");
			}
			String msg = user.getMsg();
			
			response.setContentType("text/plain");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
			String msgSub = "Mavappoint User Information";

			String msgText ="An advisor account has been created for your email address! Login to http://bartsimpson.uta.edu:8080/MavAppoint/login to change your password. Your login information is:"
	            	+ "\nUsername: " + advisorUser.getPname()
	            	+ "\npassword: \""+advisorUser.getPassword()+"\" "
	            	+ "\nMavAppoint";
			String toEmail = advisorUser.getEmail();
			
			Email newMail = new Email(msgSub, msgText, toEmail);
			newMail.sendMail();
			out.write(msg);
			out.flush();
			out.close();
			}
		catch(Exception e){
			System.out.printf(e.toString());
		}
	}
}

