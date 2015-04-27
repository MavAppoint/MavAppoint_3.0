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
	private ArrayList<Character> letters;
	private ArrayList<String> degreeType;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession(); // comment

		degreeType = new ArrayList<>();
		degreeType.add("Bachelors");
		degreeType.add("Masters");
		degreeType.add("Doctorate");
		session.setAttribute("degreeType", degreeType);
		
		letters = new ArrayList<>();
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
			
			if(departmentIndex==0) // same department
			{ 
				Integer majorIndex = Integer.valueOf(request.getParameter("drp_major"));
				String selectedMajor = majors.get(majorIndex);
				majors.remove(selectedMajor);
				majors.add(0, selectedMajor);
				session.setAttribute("majors", majors);
				
			}
			else
			{
				majors = selectedDep.getMajors();
				session.setAttribute("major", majors);
			}
			
			
			Integer letterIndex = Integer.valueOf(request.getParameter("drp_lastName"));
			Character selectedLetter = letters.get(letterIndex);
			letters = new ArrayList<>();
			char ch;
			for(ch = 'A'; ch <= 'Z'; ch++)
			{
				letters.add(ch);
			}
			letters.remove(selectedLetter);
			letters.add(0, selectedLetter);
			session.setAttribute("letters", letters);
			
			Integer degreeIndex = Integer.valueOf(request.getParameter("drp_degreeType"));
			String selectedDegree = degreeType.get(degreeIndex);
			degreeType.remove(selectedDegree);
			degreeType.add(0, selectedDegree);
			session.setAttribute("degreeType", degreeType);
			
			advisors =  dbm.getAdvisorsOfDepartment(departments.get(0).getName());
			if (advisors.size() != 0){
				session.setAttribute("advisors", advisors);
			}
			
			ArrayList<TimeSlotComponent> schedules = new ArrayList<TimeSlotComponent>();
			schedules = dbm.getAdvisorSchedules(advisors);
			if (schedules.size() != 0)
				session.setAttribute("schedules", schedules);
			
			String advisor = (String)request.getParameter("advisor_button");
			if (advisor != null && !advisor.equals("all")){
				System.out.println("Not going to check -- "+advisors.get(0).getPname());
				
				schedules = dbm.getAdvisorSchedule(advisor);
				if (schedules.size() != 0)
					session.setAttribute("schedules", schedules);
			}
			else{
				System.out.println("About to check -- "+advisors.get(0).getPname());
				ArrayList<String> tempDep = new ArrayList<String>();
				tempDep.add(departments.get(0).getName());
				
				ArrayList<String> tempMajor = new ArrayList<String>();
				tempMajor.add(majors.get(0));
				
				int degreeValue = user.setDegreeTypeFromString(selectedDegree);
				ArrayList<AdvisorUser> selectedAdvisors = new ArrayList<AdvisorUser>();
				for(int i=0; i<advisors.size(); i++)
				{
					System.out.println("Checking -- "+advisors.get(i).getPname());
					if(advisors.get(i).advisesStudent(tempDep, tempMajor, selectedLetter, degreeValue))
					{
						System.out.println("ADVISES -- "+advisors.get(i).getPname());
						selectedAdvisors.add(advisors.get(i));
					}
				}
				
				//if (selectedAdvisors.size() != 0)
					session.setAttribute("advisors", selectedAdvisors);
				
				schedules = dbm.getAdvisorSchedules(selectedAdvisors);
				//if (schedules.size() != 0)
					session.setAttribute("schedules", schedules);
			}
			
			/*ArrayList<Object> appointments = dbm.getAppointments(user);
			if (appointments.size() != 0){
				session.setAttribute("appointments", appointments);
			}*/
		}
		catch(Exception e){
			System.out.printf(e.toString());
		}
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
	}
}
