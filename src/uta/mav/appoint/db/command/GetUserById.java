package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.login.*;

public class GetUserById extends SQLCmd{
	private Integer userId;
	private LoginUser loginUser;
	
	public GetUserById(LoginUser loginUser){
		super();
		this.loginUser = loginUser;
		this.userId = loginUser.getUserId();
		
		SQLCmd sqlCmd = new GetMajorsByUserId(userId);
		sqlCmd.execute();

		System.out.println("Got Majors");
		
		ArrayList<String> majors = new ArrayList<String>();
		for(int i=0; i<sqlCmd.getResult().size(); i++)
			majors.add((String)sqlCmd.getResult().get(i));
		
		this.loginUser.setMajors(majors);

		System.out.println("Set Majors " + majors);
		
		sqlCmd = new GetDepartmentsByUserId(userId);
		sqlCmd.execute();

		System.out.println("Got departments");
		
		ArrayList<String> departments = new ArrayList<String>();
		for(int i=0; i<sqlCmd.getResult().size(); i++)
			departments.add((String)sqlCmd.getResult().get(i));
		
		this.loginUser.setDepartments(departments);

		System.out.println("Set departments "+ departments);
	}
	
	
	@Override
	public void queryDB(){
		try{
			
			String command = "SELECT email, password, role, validated from User where userid=?";
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
			System.out.println("about to process result "+res.next());
			int i = 1;
			loginUser.setEmail(res.getString(i));
			i++;

			System.out.println("Set email");
			loginUser.setPassword(res.getString(i));
			i++;

			System.out.println("Set role");
			loginUser.setRole(res.getString(i));
			i++;

			System.out.println("set validated");
			loginUser.setValidated(res.getInt(i));

			System.out.println("Adding user");
			result.add(loginUser);
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
