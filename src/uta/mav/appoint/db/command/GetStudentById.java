package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.StudentUser;

public class GetStudentById  extends SQLCmd{
	private Integer userId;
	private StudentUser studentUser;
	
	public GetStudentById(Integer userId){
		super();
		this.userId = userId;
		studentUser = new StudentUser();
		studentUser.setUserId(userId);
		
		SQLCmd sqlCmd = new GetUserById(studentUser);
		sqlCmd.execute();
		studentUser = (StudentUser)sqlCmd.getResult().get(0);
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT student_Id,degree_type,phone_num,last_name_initial FROM User_Student WHERE userId=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setInt(1,userId);
			res = statement.executeQuery();
		}
		catch(SQLException sq){
			System.out.printf(sq.toString());
		}
	}
	
	@Override
	public void processResult(){
		try{
			res.next();
			int i = 1;
			studentUser.setStudentId(res.getInt(i));
			i++;
			studentUser.setDegType(res.getInt(i));
			i++;
			studentUser.setPhoneNumber(res.getString(i));
			i++;
			studentUser.setLastNameInitial(res.getString(i));
			
			result.add(studentUser);
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}

