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
    private Department department;
    

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
				deptAdvisors = new ArrayList<AdvisorUser>();
				DatabaseManager dbm = new DatabaseManager();
				
				advUser = dbm.getAdvisor(user.getEmail());
				header = "templates/" + advUser.getHeader() + ".jsp";
				
				
				deptAdvisors = dbm.getAdvisorsOfDepartment(advUser.getDepartments().get(0));
				
			
				department = dbm.getDepartmentByName(advUser.getDepartments().get(0));
				
				session.setAttribute("deptAdvisors", deptAdvisors);
				session.setAttribute("department", department);
				request.setAttribute("includeHeader", header);
				request.getRequestDispatcher("/WEB-INF/jsp/views/assignstudents.jsp").forward(request, response);
			}
			catch(Exception e){
				System.out.printf(e.toString());
			}
		}
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
			deptAdvisors = dbm.getAdvisorsOfDepartment(advUser.getDepartments().get(0));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		System.out.println("/////////////////////////////////////////////////////////////");
		Enumeration<String> paramNames = request.getParameterNames();
		
		while(paramNames.hasMoreElements()) 
		{
		
			String paramName = (String)paramNames.nextElement();
			System.out.println(paramName );
			String majors = "majors";
			String highRange = "highRange";
			String lowRange = "lowRange";
			String degree = "degree";
			Integer index;
			ArrayList<String> majorsArray = new ArrayList<>();
			String highValue = new String();
			String lowValue = new String();
			ArrayList<String> degreeType = new ArrayList<>();
			
			if(paramName.contains(majors)){
				
				index = Integer.valueOf(paramName.substring(6));
				String[] paramValues = request.getParameterValues(paramName);
				for(int i=0; i<paramValues.length; i++) {
					majorsArray.add(paramValues[i]);
				}

				deptAdvisors.get(index).setMajors(majorsArray);
				
			}
			if(paramName.contains(highRange)){
				index = Integer.valueOf(paramName.substring(9));
				String[] paramValues = request.getParameterValues(paramName);
				highValue = paramValues[0];
		
				deptAdvisors.get(index).setNameHigh(highValue);
				
			}
			if(paramName.contains(lowRange)){
				index = Integer.valueOf(paramName.substring(8));
				String[] paramValues = request.getParameterValues(paramName);
				lowValue = paramValues[0];
			
				deptAdvisors.get(index).setNameLow(lowValue);
			}
			if(paramName.contains(degree)){
				index = Integer.valueOf(paramName.substring(6));
				String[] paramValues = request.getParameterValues(paramName);
				for(int i=0; i<paramValues.length; i++) {
					degreeType.add(paramValues[i]);
				}
				System.out.println(degreeType.toString().substring(1, degreeType.toString().length() -1));
				Integer type ;
				type = deptAdvisors.get(index).setDegreeTypeFromString(degreeType.toString().substring(1, degreeType.toString().length() -1));
				deptAdvisors.get(index).setDegType(type);
			}
			
		}
		DatabaseManager dbm2 = new DatabaseManager();
		try {
			
			dbm2.updateAdvisors(deptAdvisors);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("includeHeader", header);
		response.sendRedirect("index");	
		
	}

}

