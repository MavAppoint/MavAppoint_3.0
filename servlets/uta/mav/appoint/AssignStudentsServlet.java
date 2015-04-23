package uta.mav.appoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.login.*;
import uta.mav.appoint.db.DatabaseManager;

/**
 * Servlet implementation class AssignStudentsServlet
 */
@WebServlet("/AssignStudentsServlet")
public class AssignStudentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;   
    private String header;
    private AdvisorUser advUser;
    private ArrayList<AdvisorUser> deptAdvisors;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user == null){
				
				response.sendRedirect("login");		
		}
		else{
			try{
				advUser = new AdvisorUser();
				
				DatabaseManager dbm = new DatabaseManager();
				advUser = dbm.getAdvisor(user.getEmail());
				header = "templates/" + advUser.getHeader() + ".jsp";
				deptAdvisors = new ArrayList<AdvisorUser>();
				
				deptAdvisors = dbm.getAdvisorsOfDepartment(advUser.getDept());
				
				if (deptAdvisors.size() > 0){
					session.setAttribute("deptAdvisors", deptAdvisors);
				}
			}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
	
		
		
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/assignstudents.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user == null){
				
				response.sendRedirect("login");		
		}else{
			try {
			DatabaseManager dbm = new DatabaseManager();
			
			advUser = dbm.getAdvisor(user.getEmail());
			header = "templates/" + advUser.getHeader() + ".jsp";
			deptAdvisors = new ArrayList<AdvisorUser>();
			deptAdvisors = dbm.getAdvisorsOfDepartment(advUser.getDept());
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Enumeration<String> paramNames = request.getParameterNames();
				while(paramNames.hasMoreElements()) 
				{
				
					String paramName = (String)paramNames.nextElement();
					System.out.println(paramName );
					String[] paramValues = request.getParameterValues(paramName);
					if (paramValues.length == 1) {
						String paramValue = paramValues[0];
					if (paramValue.length() == 0)
						System.out.println("No Value");
					else
						System.out.println(paramValue);
					}else 
					{
						for(int i=0; i<paramValues.length; i++) {
							System.out.println( paramValues[i]);
					}
					
					}
				}
		request.setAttribute("includeHeader", header);
		response.sendRedirect("index");	
		
	}

}

