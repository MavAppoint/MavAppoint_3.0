package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.login.*;

public class CheckUser extends SQLCmd{
	String email;
	String password;
	String pname;
	
	public CheckUser(String e, String p){
		email = e;
		password = p;
	}
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT role FROM User where EMAIL=? AND PASSWORD=?";
			PreparedStatement statement = conn.prepareStatement(command); 
			statement.setString(1,email);
			statement.setString(2,password);
			res = statement.executeQuery();
			}
		catch (Exception e){
			System.out.println(e+" -- Found in -- "+this.getClass().getSimpleName());	
		}
		
	}
	
	@Override
	public void processResult(){
		LoginUser user = null;
		try{
			System.out.println(res);
			while(res.next()){
				if (res.getString(1).toLowerCase().equals("advisor")){
					DatabaseManager databaseManager = new DatabaseManager();
					user = databaseManager.getAdvisor(email);
				}
				else if (res.getString(1).toLowerCase().equals("student")){
					DatabaseManager databaseManager = new DatabaseManager();
					user = databaseManager.getStudent(email);
				}
				else if (res.getString(1).toLowerCase().equals("admin")){
					DatabaseManager databaseManager = new DatabaseManager();
					user = databaseManager.getAdmin(email);
				} 
				else {
					DatabaseManager databaseManager = new DatabaseManager();
					user = databaseManager.getFaculty(email);
				}
			}
			result.add(user);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}	
}
