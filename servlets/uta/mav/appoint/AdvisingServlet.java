package uta.mav.appoint;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.AdvisorUser;
import uta.mav.appoint.login.Department;
import uta.mav.appoint.login.LoginUser;

/**
 * Servlet implementation class AdvisingServlet
 */
@WebServlet("/AdvisingServlet")
public class AdvisingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;
	ArrayList<Department> departments;
	private ArrayList<String> majors;
	private ArrayList<AdvisorUser> advisors;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession(); // comment

		ArrayList<String> degreeType = new ArrayList<>();
		degreeType.add("Bachelor");
		degreeType.add("Master");
		degreeType.add("Doctorate");
		session.setAttribute("degreeType", degreeType);
		
		ArrayList<Character> letters = new ArrayList<>();
		char ch;
		for(ch = 'A'; ch <= 'Z'; ch++)
		{
			letters.add(ch);
		}
		session.setAttribute("letters", letters);
		
		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user == null){
			user = new LoginUser();
			session.setAttribute("user", user);
		}
		try{
			//get departments from database
				DatabaseManager dbm = new DatabaseManager();
				departments = dbm.getDepartments();
				session.setAttribute("departments", departments);
				
				//get majors from database
				majors = departments.get(0).getMajors();
				session.setAttribute("major", majors);
				
				header = "templates/" + user.getHeader() + ".jsp";
				//must be logged in to see advisor schedules - safety concern
				advisors =  dbm.getAdvisorsOfDepartment(departments.get(0).getName());
				if (advisors.size() != 0){
					session.setAttribute("advisors", advisors);
				}
				ArrayList<TimeSlotComponent> schedules = dbm.getAdvisorSchedules(advisors);
				if (schedules.size() != 0){
					session.setAttribute("schedules", schedules);
				}
				ArrayList<Object> appointments = dbm.getAppointments(user);
				if (appointments.size() != 0){
					session.setAttribute("appointments", appointments);
				}
		}
		catch(Exception e){
			
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();

		LoginUser user = (LoginUser)session.getAttribute("user");
		if (user == null){
			user = new LoginUser();
		}
		
		
		try{
			DatabaseManager dbm = new DatabaseManager();
			
			session = request.getSession();
			Integer departmentIndex = Integer.valueOf(request.getParameter("drp_department"));
			
			Department selectedDep = departments.get(departmentIndex);
			departments.remove(selectedDep);
			departments.add(0, selectedDep);
			session.setAttribute("departments", departments);
			
			majors = selectedDep.getMajors();
			session.setAttribute("major", majors);
			
			advisors =  dbm.getAdvisorsOfDepartment(departments.get(0).getName());
			if (advisors.size() != 0){
				session.setAttribute("advisors", advisors);
			}
			
			String advisor = (String)request.getParameter("advisor_button");
			ArrayList<TimeSlotComponent> schedule;
			schedule = dbm.getAdvisorSchedules(advisors);
			if (advisor != null){
				schedule = dbm.getAdvisorSchedule(advisor);
			}
			else{
				for(int i=0; i<advisors.size(); i++)
				{
					schedule.addAll(dbm.getAdvisorSchedule(advisors.get(i).getPname()));
				}
			}
			
			
			session.setAttribute("schedules", schedule);
			
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
	}
}
