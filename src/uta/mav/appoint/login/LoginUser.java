package uta.mav.appoint.login;

import java.util.ArrayList;

import uta.mav.appoint.db.DatabaseManager;
import uta.mav.appoint.visitor.Visitor;

public class LoginUser {
	private Integer userId;
	private String password;
	private Integer validated;
	private String role;
	private ArrayList<String> majors;
	private ArrayList<String> departments;
	String email;
	String msg;
	
	public LoginUser(){
		email = "";
		msg = "";
	}
	
	public void accept(Visitor v){
		v.check(this);
	}
	
	public String getPname(){
		return "";
	}
	
	public ArrayList<Object> accept(Visitor v, Object o){//allow javabean to be passed in
		return v.check(this,o);
	}
		
	
	public LoginUser(String em) {
		email = em;
	}
	
	/*
	 * @return the header - override in extended classes for proper header display
	 */
	public String getHeader(){
		return "header";
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	public String getMsg() {
		return msg;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setMsg(String s){
		this.msg = s;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getValidated() {
		return validated;
	}

	public void setValidated(Integer validated) {
		this.validated = validated;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ArrayList<String> getMajors() {
		return majors;
	}

	public void setMajors(ArrayList<String> majors) {
		this.majors = majors;
	}

	public ArrayList<String> getDepartments() {
		return departments;
	}

	public void setDepartments(ArrayList<String> departments) {
		this.departments = departments;
	}
	
}
