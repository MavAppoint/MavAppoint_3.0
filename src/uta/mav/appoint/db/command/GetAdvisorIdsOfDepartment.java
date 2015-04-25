package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetAdvisorIdsOfDepartment extends SQLCmd{
	private String department;
	
	public GetAdvisorIdsOfDepartment(String department){
		super();
		this.department = department;
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = " select User_advisor.userId from user_advisor,department_user where department_user.userid=user_advisor.userid and department_user.name = ?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, department);
			res = statement.executeQuery();	
		}
		catch(SQLException sq){
			System.out.printf(sq.toString());
		}
	}
	
	
	
	@Override
	public void processResult(){
		try{
			while (res.next()){
				result.add(res.getString(1));
			}
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
