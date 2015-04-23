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
		advisorUser = new AdvisorUser();
		advisorUser.setUserId(userId);
		
		SQLCmd sqlCmd = new GetUserById(advisorUser);
		sqlCmd.execute();
		System.out.println("Got Advisor "+sqlCmd.getResult().get(0));
		advisorUser = (AdvisorUser)sqlCmd.getResult().get(0);
		System.out.println("Got Advisor");
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
			res.next();
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
			
			result.add(advisorUser);
		}
		catch(SQLException sq){
			System.out.println(sq.toString());
		}
		
	}
}
