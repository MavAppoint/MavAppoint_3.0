package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;

public class GetAdvisorIDsFromDept extends SQLCmd{
String department;
	
	public GetAdvisorIDsFromDept (String d){
		department = d;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "SELECT user_advisor.userId FROM user_advisor,department_user WHERE user_advisor.userid=department_user.userid and department_user.name=?";
			PreparedStatement statement = conn.prepareStatement(command);
			statement.setString(1,department);
			res = statement.executeQuery();
			}
			catch(Exception e){
				System.out.println(e);
			}
	}

	@Override
	public void processResult() {
		try{
			while (res.next()){
				result.add(res.getInt(1));
			}
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
