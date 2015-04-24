package uta.mav.appoint.login;

import java.util.ArrayList;

public class Department {
	private String name;
	private ArrayList<String> majors;
	
	public Department() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getMajors() {
		return majors;
	}
	public void setMajors(ArrayList<String> majors) {
		this.majors = majors;
	}
}
