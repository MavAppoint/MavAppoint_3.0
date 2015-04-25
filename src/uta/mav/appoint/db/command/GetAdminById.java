package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.AdminUser;

public class GetAdminById extends SQLCmd{
	private Integer userId;
	private AdminUser adminUser;
	
	public GetAdminById(Integer userId){
		super();
		this.userId = userId;
		adminUser = new AdminUser();
		adminUser.setUserId(userId);
		
		SQLCmd sqlCmd = new GetUserById(adminUser);
		sqlCmd.execute();
		adminUser = (AdminUser)sqlCmd.getResult().get(0);
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
			
			result.add(adminUser);
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
