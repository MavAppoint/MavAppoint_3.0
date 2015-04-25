package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class UpdateAdvisor extends SQLCmd {
	
	private AdvisorUser advisorUser;
	
	Boolean b;
	
	public UpdateAdvisor(AdvisorUser advisorUser){
		this.advisorUser = advisorUser;
		
		SQLCmd sqlCmd = new UpdateUser(advisorUser);
		sqlCmd.execute();
		
		b = false;
	}
	
	public void queryDB(){
		try{
			String command = "UPDATE User_Advisor SET pName=?, notification=?, name_low=?, name_high=?, degree_types=?, lead_status=? WHERE userId=?";
			PreparedStatement statement = conn.prepareStatement(command);
			
			statement.setString(1,advisorUser.getPname());
			statement.setString(2,advisorUser.getNotification());
			statement.setString(3,advisorUser.getNameLow());
			statement.setString(4,advisorUser.getNameHigh());
			statement.setInt(5,advisorUser.getDegType());
			statement.setInt(6,advisorUser.getIsLead());
			statement.setInt(7,advisorUser.getUserId());

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
