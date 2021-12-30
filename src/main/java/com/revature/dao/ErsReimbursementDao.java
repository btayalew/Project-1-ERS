package com.revature.dao;

import java.io.IOException;
import java.util.List;

import com.revature.model.ErsReimbursement;

public interface ErsReimbursementDao {

	List<ErsReimbursement> getErsReimbursements() throws IOException;

	ErsReimbursement getErsReimbursementById(int id) throws IOException;
	
	List<ErsReimbursement> getErsReimbursementByAuthorId(int authId) throws IOException;

	List<ErsReimbursement> getErsReimbursementByStatusId(int statusId) throws IOException;
	
	ErsReimbursement addErsReimbursement(ErsReimbursement ersReimbursement) throws IOException;

	boolean updateErsReimbursement(ErsReimbursement ersReimbursement) throws IOException;
}
