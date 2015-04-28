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
import uta.mav.appoint.login.*;

/**
 * Servlet implementation class AdvisingServlet
 */
@WebServlet("/AdvisingServlet")
public class AdvisingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	String header;
	ArrayList<Department> departments;
	private ArrayList<String> major;
	private ArrayList<AdvisorUser> advisors;
	private ArrayList<Character> letters;
	private ArrayList<String> degreeType;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		session = request.getSession(); // comment
		
		LoginUser user = (LoginUser)session.getAttribute("user");
		try{

			DatabaseManager dbm = new DatabaseManager();
			StudentUser studentUser = null;
			if(user!=null)
				try
			{
				studentUser = dbm.getStudent(user.getEmail());
			}
			catch (Exception e)
			{
				studentUser = null;
			}
			
			if(studentUser == null)
			{
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
				
				user = new LoginUser();
				session.setAttribute("user", user);
				departments = dbm.getDepartments();
				session.setAttribute("departments", departments);
				
				//get majors from database
				major = departments.get(0).getMajors();
				session.setAttribute("major", major);
			}
			else
			{
				int tempType = studentUser.getDegType();
				degreeType = new ArrayList<>();
				if(tempType>=4)
				{
					tempType -= 4;
					degreeType.add("Doctorate");
				}
				if(tempType>=2)
				{
					tempType -= 2;
					degreeType.add("Masters");
				}
				if(tempType>=1)
					degreeType.add("Bachelors");
				
				session.setAttribute("degreeType", degreeType);
				
				letters = new ArrayList<>();
				if(studentUser!=null)
					letters.add(studentUser.getLastNameInitial().charAt(0));
				session.setAttribute("letters", letters);
			
				departments = new ArrayList<Department>();
				major = new ArrayList<String>();
				
				System.out.println("Trying deps");
				ArrayList<Department> tempDeps = dbm.getDepartments();
				for(int depIndex=0; depIndex<tempDeps.size(); depIndex++)
				{
						if(user.getDepartments().contains(tempDeps.get(depIndex).getName()))
						{
							System.out.println("Found dep "+tempDeps.get(depIndex).getName());
							Department tempDep = tempDeps.get(depIndex);
							ArrayList<String> tempMajors = new ArrayList<String>();
							for(int majorIndex=0; majorIndex<tempDeps.get(depIndex).getMajors().size(); majorIndex++)
									if(user.getMajors().contains(tempDeps.get(depIndex).getMajors().get(majorIndex)))
									{
										System.out.println("Found major "+tempDeps.get(depIndex).getMajors().get(majorIndex));
										tempMajors.add(tempDeps.get(depIndex).getMajors().get(majorIndex));
									}
							
							tempDep.setMajors(tempMajors);
							departments.add(tempDep);
							
							if(major.size()==0)
								major = tempMajors;
						}
				}
				System.out.println("Finished deps");
				
				session.setAttribute("departments", departments);
				session.setAttribute("major", major);
			}
			

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
			System.out.println("Something failed!");
		}

		System.out.println("majors selected --------------- deps" + major.get(0));
		request.setAttribute("includeHeader", header);
		request.getRequestDispatcher("/WEB-INF/jsp/views/advising.jsp").forward(request, response);
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		
		System.out.println("Starting already? -------------------------");
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
				String selectedMajor = major.get(majorIndex);
				major.remove(selectedMajor);
				major.add(0, selectedMajor);
				session.setAttribute("majors", major);
				
			}
			else
			{
				major = selectedDep.getMajors();
				session.setAttribute("major", major);
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
				tempMajor.add(major.get(0));
				
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
