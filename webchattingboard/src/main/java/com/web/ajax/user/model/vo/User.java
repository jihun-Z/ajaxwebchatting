package com.web.ajax.user.model.vo;


public class User {
	
	private String userID;
	private String userPassword;
	private String userName;
	private int userAge;
	private String userGender;
	private String email;
	private String profile;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userID, String userPassword, String userName, int userAge, String userGender, String email,
			String profile) {
		super();
		this.userID = userID;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userAge = userAge;
		this.userGender = userGender;
		this.email = email;
		this.profile = profile;
	}
	

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", userPassword=" + userPassword + ", userName=" + userName + ", userAge="
				+ userAge + ", userGender=" + userGender + ", email=" + email + ", profile=" + profile + "]";
	}


	
	

}
