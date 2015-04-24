package uta.mav.appoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uta.mav.appoint.beans.GetSet;
import uta.mav.appoint.beans.RegisterBean;
import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.email.Email;
import uta.mav.appoint.login.*;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; // test comment
	private ArrayList<String> departments;
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
			departments = dbm.getDepartmentStrings();
			session.setAttribute("departments", departments);
			
			//get majors from database
			majors = dbm.getMajor();
			session.setAttribute("major", majors);
		} catch(Exception e){
			System.out.println(e+" RegisterServlet");
		}
		
		request.setAttribute("includeHeader", "templates/header.jsp");
		request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean success = false;
		StudentUser studentUser = new StudentUser();
		String role = "student";
		studentUser.setRole(role);
		
		try{
			String email = request.getParameter("emailAddress");
			if(!email.endsWith("@mavs.uta.edu"))
			{
				System.out.println("Email Address Invalid");
				request.setAttribute("error","Unable to add user");
				request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
			}
			studentUser.setEmail(email);
			
			String password = request.getParameter("password");
			if(password.length()<6)
			{
				System.out.println("Unsecure Password");
				request.setAttribute("error","Unable to add user");
				request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
			}
			String rpassword = request.getParameter("repeatPassword");
			if(!password.equals(rpassword))
			{
				System.out.println("Passwords do not match Invalid");
				request.setAttribute("error","Unable to add user");
				request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
			}
			studentUser.setPassword(password);
			
			String phone_num = request.getParameter("phone_num");
			if(!phone_num.matches("^\\d{3}-\\d{3}-\\d{4}"))
			{
				System.out.println("Phone Number Invalid");
				request.setAttribute("error","Unable to add user");
				request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
			}
			studentUser.setPhoneNumber(phone_num);
			
			if(!request.getParameter("student_Id").matches("^100\\d{7}") && !request.getParameter("student_Id").matches("^6000\\d{6}")){
				System.out.println("Student ID Invalid");
				request.setAttribute("error","Unable to add user");
				request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request,response);
			}
			Integer student_Id = Integer.valueOf(request.getParameter("student_Id"));
			studentUser.setStudentId(student_Id);
			
			String lastName = request.getParameter("lastName");
			studentUser.setLastName(lastName);
			
			String firstName = request.getParameter("firstName");
			studentUser.setFirstName(firstName);
			
			Integer degree_type = Integer.valueOf(request.getParameter("drp_degreeType"));
			studentUser.setDegreeType(degree_type);
			
			ArrayList<String> departmentsSelected = new ArrayList<String>();
			String departmentFound = departments.get(Integer.valueOf(request.getParameter("drp_department")));
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
						+"Email: "+email+"\n"
						+"Password: "+password,
						email);
				userEmail.sendMail();
					
				session = request.getSession();
				session.setAttribute("user", studentUser);
				response.sendRedirect("index");
				success = true;
			}
		}
		catch(Exception e){
			System.out.println(e+"RegisterServlet");
		}
		if(!success){
			System.out.println("Couldn't Log In");
			//if unable to log in, add error message and redirect back to register
			request.setAttribute("includeHeader", "templates/header.jsp");
			request.getRequestDispatcher("/WEB-INF/jsp/views/register.jsp").forward(request, response);
		}
	}

}
