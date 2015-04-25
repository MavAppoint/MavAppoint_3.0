package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.FacultyUser;

public class GetFacultyById extends SQLCmd{
	private Integer userId;
	private FacultyUser facultyUser;
	
	public GetFacultyById(Integer userId){
		super();
		this.userId = userId;
		facultyUser = new FacultyUser();
		facultyUser.setUserId(userId);
		
		SQLCmd sqlCmd = new GetUserById(facultyUser);
		sqlCmd.execute();
		facultyUser = (FacultyUser)sqlCmd.getResult().get(0);
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT * FROM User WHERE userId=?";
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
			
			result.add(facultyUser);
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
