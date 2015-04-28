package uta.mav.appoint;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.login.*;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; // test comment
	private ArrayList<Department> departments;
	private ArrayList<String> majors;
	
	HttpSession session;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();

		ArrayList<String> degreeType = new ArrayList<>();
		degreeType.add("Bachelor");
		degreeType.add("Master");
		degreeType.add("Doctorate");
		session.setAttribute("degreeType", degreeType);
		
		try {
			DatabaseManager dbm = new DatabaseManager();
			departments = dbm.getDepartments();
			session.setAttribute("departments", departments);
			
			//get majors from database
			majors = departments.get(0).getMajors();
			session.setAttribute("major", majors);
		} catch(Exception e){
			System.out.println(e+" RegisterServlet");
		}
		
		session.setAttribute("message", "");
		
		request.setAttribute("includeHeader", "templates/header.jsp");
		request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!Boolean.valueOf(request.getParameter("submitted")))
		{
			session = request.getSession();
			Integer departmentIndex = Integer.valueOf(request.getParameter("drp_department"));
			
			Department selectedDep = departments.get(departmentIndex);
			departments.remove(selectedDep);
			departments.add(0, selectedDep);
			session.setAttribute("departments", departments);
			
			majors = selectedDep.getMajors();
			session.setAttribute("major", majors);
			
			request.setAttribute("includeHeader", "templates/header.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
		}
		else
		{
			StudentUser studentUser = new StudentUser();
			String role = "student";
			studentUser.setRole(role);
			
			try{
				String email = request.getParameter("emailAddress");
				if(!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))
				{
					System.out.println("Email Address Invalid");
					session.setAttribute("message", "Email Address Invalid");
					request.setAttribute("error","Unable to add user");
					request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
				}
				studentUser.setEmail(email);
				
				String password = "newstudent!@3";
				studentUser.setPassword(password);
				
				
				String phone_num = request.getParameter("phone_num");
				if(!phone_num.matches("^\\d{3}-\\d{3}-\\d{4}"))
				{
					System.out.println("Phone Number Invalid");
					session.setAttribute("message", "Phone Number Invalid");
					request.setAttribute("error","Unable to add user");
					request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
				}
				studentUser.setPhoneNumber(phone_num);
				
				if(!request.getParameter("student_Id").matches("^100\\d{7}") && !request.getParameter("student_Id").matches("^6000\\d{6}")){
					System.out.println("Student ID Invalid");
					session.setAttribute("message", "Student ID Invalid");
					request.setAttribute("error","Unable to add user");
					request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
				}
				Integer student_Id = Integer.valueOf(request.getParameter("student_Id"));
				studentUser.setStudentId(student_Id);
				
				System.out.println("Last Name Initial: "+request.getParameter("drp_last_name_initial"));
				String lastNameInitial = request.getParameter("drp_last_name_initial");
				studentUser.setLastNameInitial(lastNameInitial);
				
				Integer degree_type = Integer.valueOf(request.getParameter("drp_degreeType"));
				studentUser.setDegType(degree_type);
				
				ArrayList<String> departmentsSelected = new ArrayList<String>();
				String departmentFound = departments.get(Integer.valueOf(request.getParameter("drp_department"))).getName();
				departmentsSelected.add(departmentFound);
				studentUser.setDepartments(departmentsSelected);
				
				System.out.println("param "+request.getParameter("drp_major").toString());
				ArrayList<String> majorsSelected = new ArrayList<String>();
				String majorFound = majors.get(Integer.valueOf(request.getParameter("drp_major")));
				majorsSelected.add(majorFound);
				studentUser.setMajors(majorsSelected);
			
				DatabaseManager dbm = new DatabaseManager();
				if (dbm.createStudent(studentUser)){
					Email userEmail = new Email("MavAppoint Account Created",
							"Your account for MavAppoint has been created! Your account information is:\n"
							+"Role: "+role+"\n"
							+"Password: "+password,
							email);
					userEmail.sendMail();
					session.setAttribute("message", "Account Created! Please check your e-mail for a new password.");
				}
				else
				{

					session.setAttribute("message", "Account could not be created");
				}
			}
			catch(Exception e){
				System.out.println(e+" RegisterServlet");
			}
			
			request.setAttribute("includeHeader", "templates/header.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
		}
	}

}
