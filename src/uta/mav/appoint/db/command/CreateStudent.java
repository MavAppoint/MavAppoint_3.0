package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class CreateStudent  extends SQLCmd{

	private Integer userid;
	private Integer student_Id;
	private Integer degree_type;
	private String phone_num;
	private String last_name_initial;
	private Boolean b;
	
	public CreateStudent(StudentUser studentUser){
		userid = studentUser.getUserId();
		student_Id = studentUser.getStudentId();
		degree_type = studentUser.getDegType();
		phone_num = studentUser.getPhoneNumber();
		last_name_initial = studentUser.getLastNameInitial();
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "INSERT INTO User_Student (userid,student_Id,degree_type,phone_num,last_name_initial) "
								+"values(?,?,?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			int i=1;
			statement.setInt(i,userid);
			i++;
			statement.setInt(i,student_Id);
			i++;
			statement.setInt(i,degree_type);
			i++;
			statement.setString(i,phone_num);
			i++;
			statement.setString(i,last_name_initial);
			statement.executeUpdate();
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