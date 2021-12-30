package com.revature.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.revature.dao.DaoFactory;
import com.revature.dao.ErsReimbursementDao;
import com.revature.dao.ErsReimbursementPostgres;
import com.revature.model.ErsReimbursement;

public class ErsService {

	ErsReimbursementPostgres erp = new ErsReimbursementPostgres();
	private ErsReimbursementDao erd;

	public ErsService() {
		erd = DaoFactory.getDaoFactory().getErsReimbursementDao();
	}

	/**
	 * Service method to retrieve all reimbursements
	 * 
	 * @return a List of reimbursements or an empty list if none are found
	 * @throws IOException
	 */
	public List<ErsReimbursement> getAllReimbursements() throws IOException {
		return erd.getErsReimbursements();
	}

	/**
	 * Service method to retrieve reimbursement by its reimbursement id
	 * 
	 * @param the reimbursement id
	 * @return a reimbursement  object or null if none is found
	 * @throws IOException
	 */
	public List<ErsReimbursement> getReimbByStatus(String status) throws IOException {
		List<ErsReimbursement> result = new ArrayList<>();
		String[] reimbStatus = { "under review", "approved", "rejected" };
		for(ErsReimbursement reimb:erd.getErsReimbursements()) {
			for (int i = 0; i < reimbStatus.length; i++) {
				if (status.toLowerCase().equals(reimbStatus[i])
					&& reimb.getReimbStatusId() == i+1) {
					result.add(reimb);
					break;
				}
			}
		}
		return result;
	}
	/**
	 * Service method to retrieve all reimbursements identified by reimbId
	 * 
	 * @return all reimbursements or null if none is found
	 * @throws IOException
	 */
	public ErsReimbursement getReimbById(int reimbId) throws IOException {
		return erd.getErsReimbursementById(reimbId);
	}

	/**
	 * Service method to retrieve reimbursement by reimbursement author id
	 * 
	 * @param the reimbursement author id
	 * @return a list of reimbursement objects or null if none is found
	 * @throws IOException
	 */
	public List<ErsReimbursement> getReimbByAuthId(int authId) throws IOException {

		return erd.getErsReimbursementByAuthorId(authId);

	}
	
	/**
	 * Service method to add new reimbursement claim
	 * 
	 * @param reimbursement object, requires an id and valid field values
	 * @throws IOException
	 */
	public ErsReimbursement addReimbursement(ErsReimbursement reimb) throws IOException {
		ErsReimbursement res = null;
		if( reimb != null) {
			res = erd.addErsReimbursement(reimb);
		}
		return res;
	}

	/**
	 * Service method to update reimbursement based on reimbursement id
	 * 
	 * @param reimbursement object, requires an id and valid field values
	 * @return true if reimbursement was updated, else false
	 * @throws IOException
	 */
	public boolean updateReimbursement(ErsReimbursement reimb) throws IOException {
		ErsReimbursement reimbToUpdate = erd.getErsReimbursementById(reimb.getReimbId());
		System.out.println("This is :"+reimbToUpdate);
			reimbToUpdate.setReimbId(reimb.getReimbId());
			reimbToUpdate.setReimbAuthor(reimb.getReimbAuthor());
			reimbToUpdate.setReimbAmount(reimb.getReimbAmount());
			reimbToUpdate.setReimbAmount(reimb.getReimbAmount());
			reimbToUpdate.setReimbResolved(reimb.getReimbResolved());
			reimbToUpdate.setReimbStatusId(reimb.getReimbStatusId());
			reimbToUpdate.setReimbTypeId(reimb.getReimbTypeId());
			
			return erp.updateErsReimbursement(reimbToUpdate);
	}
}
