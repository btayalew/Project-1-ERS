package com.revature.model;

import java.util.Objects;

public class ErsReimbursementType {

	private int reimbStatusId;
	private String reimbStatus;
	
	public ErsReimbursementType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErsReimbursementType(int reimbStatusId, String reimbStatus) {
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
		if (!(obj instanceof ErsReimbursementType))
			return false;
		ErsReimbursementType other = (ErsReimbursementType) obj;
		return Objects.equals(reimbStatus, other.reimbStatus) && reimbStatusId == other.reimbStatusId;
	}
	
	
}
