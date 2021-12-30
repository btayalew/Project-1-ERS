package com.revature.model;

import java.util.Objects;

public class ErsUsersRoles {
	
	private int ersUserRoleId;
	private String ersUserRole;
	public ErsUsersRoles() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErsUsersRoles(int ersUserRoleId, String ersUserRole) {
		super();
		this.ersUserRoleId = ersUserRoleId;
		this.ersUserRole = ersUserRole;
	}
	public int getErsUserRoleId() {
		return ersUserRoleId;
	}
	public void setErsUserRoleId(int ersUserRoleId) {
		this.ersUserRoleId = ersUserRoleId;
	}
	public String getErsUserRole() {
		return ersUserRole;
	}
	public void setErsUserRole(String ersUserRole) {
		this.ersUserRole = ersUserRole;
	}
	@Override
	public int hashCode() {
		return Objects.hash(ersUserRole, ersUserRoleId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ErsUsersRoles))
			return false;
		ErsUsersRoles other = (ErsUsersRoles) obj;
		return Objects.equals(ersUserRole, other.ersUserRole) && ersUserRoleId == other.ersUserRoleId;
	}

	
}
