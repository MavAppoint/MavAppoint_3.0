package uta.mav.appoint.db.command;

import java.sql.PreparedStatement;
import uta.mav.appoint.email.*;

import uta.mav.appoint.login.*;

public class CreateUser  extends SQLCmd{
	
	private LoginUser loginUser;
	private String email;
	private String password;
	private String role;
	private Boolean b;
	
	public CreateUser(LoginUser loginUser){
		this.email=loginUser.getEmail();
		this.password=loginUser.getPassword();
		this.role = loginUser.getRole();
		this.loginUser = loginUser;
		b = false;
	}
	
	@Override
	public void queryDB() {
		try{
			String command = "INSERT INTO user (email,password,role)"
							+" values(?,?,?)";
			PreparedStatement statement = conn.prepareStatement(command);
			
			statement.setString(1,email);
			statement.setString(2,password);
			statement.setString(3,role);
			
			statement.executeUpdate();
			
			b = true;
			}
		catch(Exception e){
			System.out.println(e);
		}
	}

	@Override
	public void processResult() {
		try
		{
			SQLCmd cmd = new GetUserIDByEmail(loginUser.getEmail());
			cmd.execute();
			loginUser.setUserId((int)cmd.getResult().get(0));
			System.out.println("Finished getting ID");
			
			cmd = new AddMajorsByUserId(loginUser.getUserId(), loginUser.getMajors());
			cmd.execute();
			System.out.println("Finished adding majors");
			
			cmd = new AddDepartmentsByUserId(loginUser.getUserId(), loginUser.getDepartments());
			cmd.execute();
			System.out.println("Finished adding departments");
		}
		catch(Exception e)
		{
			System.out.println(e+" In "+this.getClass().getSimpleName());
		}
		
		result.add(b);	
	}

		
}
