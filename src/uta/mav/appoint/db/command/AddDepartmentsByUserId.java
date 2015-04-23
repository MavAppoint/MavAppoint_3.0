package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddDepartmentsByUserId extends SQLCmd {
	
	private Integer userId;
	private ArrayList<String> departments;
	
	Boolean b;
	
	public AddDepartmentsByUserId(Integer userId, ArrayList<String> departments){
		this.userId = userId;
		this.departments = departments;
		b = false;
	}
	
	public void queryDB(){
		try{
			String command = "INSERT INTO department_user (name, userId) VALUES ";
			for(int majorsIndex = 0; majorsIndex < departments.size(); majorsIndex++)
				if(majorsIndex>0)
					command += ", ";
				command += "(?, ?)";
			PreparedStatement statement = conn.prepareStatement(command);
			for(int majorsIndex = 0; majorsIndex < departments.size(); majorsIndex++)
			{
				statement.setString(majorsIndex*2,departments.get(majorsIndex));
				statement.setInt(majorsIndex*2+1,userId);
			}

			statement.executeUpdate();
			b=true;
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
	}
	
	public void processResult(){
		result.add(b);
	}

}
