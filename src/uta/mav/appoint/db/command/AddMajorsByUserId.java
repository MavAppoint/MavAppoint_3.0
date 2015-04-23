package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddMajorsByUserId extends SQLCmd {
	
	private Integer userId;
	private ArrayList<String> majors;
	
	Boolean b;
	
	public AddMajorsByUserId(Integer userId, ArrayList<String> majors){
		this.userId = userId;
		this.majors = majors;
		b = false;
	}
	
	public void queryDB(){
		try{
			String command = "INSERT INTO major_user (name, userId) VALUES ";
			for(int majorsIndex = 0; majorsIndex < majors.size(); majorsIndex++)
			{
				if(majorsIndex>0)
					command += ", ";
				command += " (?, ?)";
			}
			PreparedStatement statement = conn.prepareStatement(command);
			for(int majorsIndex = 0; majorsIndex < majors.size(); majorsIndex++)
			{
				System.out.println("Want to add :"+majors.get(majorsIndex)+":");
				statement.setString(majorsIndex*2+1,majors.get(majorsIndex));
				statement.setInt(majorsIndex*2+2,userId);
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
