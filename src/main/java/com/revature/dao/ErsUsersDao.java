package com.revature.dao;

import java.io.IOException;
import java.util.List;

import com.revature.model.ErsUsers;

public interface ErsUsersDao {
	
	List<ErsUsers> getErsUsers() throws IOException;

	ErsUsers getErsUserById(int id) throws IOException;

	ErsUsers getErsUserByUsername(String username) throws IOException;

	ErsUsers addErsUser(ErsUsers ersUser) throws IOException;

	boolean updateErsUser(ErsUsers ersUser) throws IOException;

}
