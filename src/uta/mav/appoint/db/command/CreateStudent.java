package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class CreateStudent  extends SQLCmd{

	int userid;
	int student_Id;
	int degree_type;
	String phone_num;
	Boolean b;
	
	public CreateStudent(StudentUser studentUser){
		userid = studentUser.getUserId();
		student_Id = studentUser.getStudentId();
		degree_type = studentUser.getDegreeType();
		phone_num = studentUser.getPhoneNumber();
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "INSERT INTO User_Student (userid,student_Id,degree_type,phone_num) "
								+"values(?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1,userid);
			statement.setInt(2,student_Id);
			statement.setInt(3,degree_type);
			statement.setString(4,phone_num);
			statement.executeUpdate();
			b = true;
		}
		catch(SQLException sqe){
			System.out.println(sqe.toString()+"RegisterInitialStudent");
		}
		
	}

	@Override
	public void processResult() {
		result.add(b);
	}

}