package com.revature.model;

import java.util.Objects;

public class ErsUsers {
	
	private int ersUserId;
	private String ersUsername;
	private String ersPassword;
	private String userFirstName;
	private String userLastName;
	private String userEmail;
	private int userRoleId;
	
	public ErsUsers() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErsUsers(int ersUserId, String ersUsername, String ersPassword, String userFirstName, String userLastName,
			String userEmail, int userRoleId) {
		super();
		this.ersUserId = ersUserId;
		this.ersUsername = ersUsername;
		this.ersPassword = ersPassword;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userRoleId = userRoleId;
	}
	public int getErsUserId() {
		return ersUserId;
	}
	public void setErsUserId(int ersUserId) {
		this.ersUserId = ersUserId;
	}
	public String getErsUsername() {
		return ersUsername;
	}
	public void setErsUsername(String ersUsername) {
		this.ersUsername = ersUsername;
	}
	public String getErsPassword() {
		return ersPassword;
	}
	public void setErsPassword(String ersPassword) {
		this.ersPassword = ersPassword;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ersPassword, ersUserId, ersUsername, userEmail, userFirstName, userLastName, userRoleId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ErsUsers))
			return false;
		ErsUsers other = (ErsUsers) obj;
		return Objects.equals(ersPassword, other.ersPassword) && ersUserId == other.ersUserId
				&& Objects.equals(ersUsername, other.ersUsername) && Objects.equals(userEmail, other.userEmail)
				&& Objects.equals(userFirstName, other.userFirstName)
				&& Objects.equals(userLastName, other.userLastName) && userRoleId == other.userRoleId;
	}
	
	
}
