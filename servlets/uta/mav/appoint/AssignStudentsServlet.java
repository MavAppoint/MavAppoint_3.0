package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

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
				System.out.println(user.getEmail());
				DatabaseManager dbm = new DatabaseManager();
				header = "templates/" + advUser.getHeader() + ".jsp";
				deptAdvisors = new ArrayList<AdvisorUser>();
				AdvisorUser adv1 = new AdvisorUser( "Dr. Reynaldo", "A", "Z", 8);
				AdvisorUser adv2 = new AdvisorUser( "Dr. Alex", "A", "Z", 8);
				deptAdvisors.add(adv2);
				deptAdvisors.add(adv1);
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
		// TODO Auto-generated method stub
	}

}

