package uta.mav.appoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();

		
		try{
			LoginUser user = (LoginUser)session.getAttribute("user");
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
		}
		catch(Exception e){
			header = "templates/header.jsp";
			System.out.println(e);
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/change_password.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			session = request.getSession();
			LoginUser user = (LoginUser)session.getAttribute("user");
			DatabaseManager dbm = new DatabaseManager();
			String currentpassword = request.getParameter("currentpassword");
			String password = request.getParameter("password");
			String repeatpassword = request.getParameter("repeatpassword");
			if(user.getPassword().equals(currentpassword)){
				if(password.equals(repeatpassword))
				{
					user.setPassword(password);
					user.setValidated(1);
					dbm.updateUser(user);
					

					session.setAttribute("user", user);
					response.sendRedirect("login");
				}
			}
			else{
				response.sendRedirect("changePassword");
				System.out.println("Password invalid");
			}
		}
		catch(Exception e){
			e.getStackTrace();
		}
	}

}
