package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import uta.mav.appoint.login.Department;

public class GetDepartmentByName   extends SQLCmd{
	private String name;
	
	public GetDepartmentByName(String name){
		super();
		this.name = name;
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT name from major where dep_name=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1, name);
			res = statement.executeQuery();	
		}
		catch(SQLException sq){
			System.out.printf(sq.toString());
		}
	}
	
	
	
	@Override
	public void processResult(){
		try{
			Department department = new Department();
			department.setName(name);
			
			ArrayList<String> majors = new ArrayList<String>();
			while (res.next()){
				majors.add(res.getString(1));
			}
			department.setMajors(majors);
			
			result.add(department);
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
