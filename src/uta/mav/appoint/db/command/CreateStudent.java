package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class CreateStudent  extends SQLCmd{

	private Integer userid;
	private Integer student_Id;
	private Integer degree_type;
	private String phone_num;
	private String last_name;
	private String first_name;
	private Boolean b;
	
	public CreateStudent(StudentUser studentUser){
		userid = studentUser.getUserId();
		student_Id = studentUser.getStudentId();
		degree_type = studentUser.getDegreeType();
		phone_num = studentUser.getPhoneNumber();
		last_name = studentUser.getLastName();
		first_name = studentUser.getFirstName();
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "INSERT INTO User_Student (userid,student_Id,degree_type,phone_num,last_name,first_name) "
								+"values(?,?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1,userid);
			statement.setInt(2,student_Id);
			statement.setInt(3,degree_type);
			statement.setString(4,phone_num);
			statement.setString(5,last_name);
			statement.setString(6,first_name);
			System.out.println("Made it here");
			statement.executeUpdate();
			System.out.println("Made it here too");
			b = true;
		}
		catch(SQLException sqe){
			System.out.println(sqe.toString()+"RegisterInitialStudent");
		}
		
	}

	@Override
	public void processResult() {
		System.out.println("Created "+result);
		result.add(b);
	}

}