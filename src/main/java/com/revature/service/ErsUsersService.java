package com.revature.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.dao.DaoFactory;
import com.revature.dao.ErsUsersDao;
import com.revature.model.ErsUsers;

public class ErsUsersService {
	private ErsUsersDao eud;
	
	public ErsUsersService() {
		eud = DaoFactory.getDaoFactory().getErsUsersDao();
	}
	/**
	 * Service method to retrieve all employees, sets the employee passwords to null before returning them
	 * @return a List of Employees or an empty list if none are found
	 * @throws IOException 
	 */
	public List<ErsUsers> getErsUsers() throws IOException{

		List<ErsUsers> ersUsers = eud.getErsUsers().stream()
				.map(e -> {
					e.setErsPassword(null); 
					return e;})
				.collect(Collectors.toList());
		
		return ersUsers;
	}
	/**
	 * Service method to retrieve an employee by id, sets the employee password to null before returning
	 * @param id of the employee
	 * @return an employee or null if none is found
	 * @throws IOException 
	 */
	public ErsUsers getErsUserById(int id) throws IOException{
		ErsUsers ersUser = eud.getErsUserById(id);
		if (ersUser != null) {
			ersUser.setErsPassword(null);
		}
		
		return ersUser;
	}
	
	/**
	 * Service method to retrieve an employee by its username, sets the employee password to null before returning
	 * @param username of the employee
	 * @return an employee or null if none is found
	 * @throws IOException 
	 */
	public ErsUsers getErsUserByUsername(String username) throws IOException{
		ErsUsers ersUser = eud.getErsUserByUsername(username);
		if (ersUser != null) {
			ersUser.setErsPassword(username);
		}
		
		return ersUser;
	}
	/**
	 * Service method to update an employee's info (ONLY username, password, and name) based on their id
	 * @param employee object, requires an id and valid field values
	 * @return true if an employee was updated, else false
	 * @throws IOException 
	 */
	public boolean updateErsUserInfo(ErsUsers ersUser) throws IOException {
		
		ErsUsers ersUser_update = eud.getErsUserById(ersUser.getErsUserId());
		
		if(ersUser.getUserFirstName() != null && !ersUser.getUserFirstName().equals(ersUser_update.getUserFirstName())) {
			ersUser_update.setUserFirstName(ersUser.getUserFirstName());
		}
		
		if(ersUser.getUserLastName() != null && !ersUser.getUserLastName().equals(ersUser_update.getUserLastName())) {
			ersUser_update.setUserLastName(ersUser.getUserLastName());
		}
		
		if(ersUser.getErsUsername() != null && !ersUser.getErsUsername().equals(ersUser_update.getErsUsername())) {
			ersUser_update.setErsUsername(ersUser.getErsUsername());
		}

		if(ersUser.getErsPassword() != null && !ersUser.getErsPassword().equals(ersUser_update.getErsPassword())) {
			ersUser_update.setErsPassword(ersUser.getErsPassword());
		}
		
		return eud.updateErsUser(ersUser_update);	
	}

	public static void submitReimbursementRequest() {
		
	}
	
	public static void uploadDocument() {
		
	}
	
	public static void viewPendingReimbursementRequest() {
		
	}
	
	public static void viewReslolvedReimbursementRequest() {
		
	}
	
	public static void viewAccountInformation() {
		
	}
	
	public static void updateAccountInformation() {
		
	}
	
	public static void resetUsername() {
		
	}
	public static void resetPassword() {
		
	}
}
