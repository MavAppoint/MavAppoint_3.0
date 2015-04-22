package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import uta.mav.appoint.login.*;

public class GetAdvisorById extends SQLCmd{
	private Integer userId;
	private AdvisorUser advisorUser;
	
	public GetAdvisorById(Integer userId){
		super();
		this.userId = userId;
		
		SQLCmd sqlCmd = new GetUserById(userId);
		sqlCmd.execute();
		advisorUser = (AdvisorUser)sqlCmd.getResult().get(0);
	}
	
	
	@Override
	public void queryDB(){
		try{
			String command = "SELECT pName,notification,name_low,name_high,degree_types,lead_status FROM User_Advisor WHERE userId=?";
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
			int i = 1;
			advisorUser.setPname(res.getString(i));
			i++;
			advisorUser.setNotification(res.getString(i));
			i++;
			advisorUser.setNameLow(res.getString(i));
			i++;
			advisorUser.setNameHigh(res.getString(i));
			i++;
			advisorUser.setDegType(res.getInt(i));
			i++;
			advisorUser.setIsLead(res.getInt(i));
			i++;
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
