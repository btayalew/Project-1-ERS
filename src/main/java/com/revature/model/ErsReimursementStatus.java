package com.revature.model;

import java.util.Objects;

public class ErsReimursementStatus {

	private int reimbStatusId;
	private String reimbStatus;
	public ErsReimursementStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ErsReimursementStatus(int reimbStatusId, String reimbStatus) {
		super();
		this.reimbStatusId = reimbStatusId;
		this.reimbStatus = reimbStatus;
	}
	public int getReimbStatusId() {
		return reimbStatusId;
	}
	public void setReimbStatusId(int reimbStatusId) {
		this.reimbStatusId = reimbStatusId;
	}
	public String getReimbStatus() {
		return reimbStatus;
	}
	public void setReimbStatus(String reimbStatus) {
		this.reimbStatus = reimbStatus;
	}
	@Override
	public int hashCode() {
		return Objects.hash(reimbStatus, reimbStatusId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ErsReimursementStatus))
			return false;
		ErsReimursementStatus other = (ErsReimursementStatus) obj;
		return Objects.equals(reimbStatus, other.reimbStatus) && reimbStatusId == other.reimbStatusId;
	}
	
	
}
