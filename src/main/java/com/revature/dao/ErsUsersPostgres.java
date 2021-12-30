package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.ErsUsers;
import com.revature.util.ConnectionUtil;

public class ErsUsersPostgres implements ErsUsersDao{
	
	@Override
	public List<ErsUsers> getErsUsers() throws IOException {
		String sql = "select * from ers.ers_users;";
		List<ErsUsers> users = new ArrayList<>();
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				ErsUsers user = new ErsUsers(rs.getInt("ers_users_id"),
											rs.getString("ers_username"),
											rs.getString("ers_password"),
											rs.getString("user_first_name"),
											rs.getString("user_last_name"),
											rs.getString("user_email"),
											rs.getInt("user_role_id"));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return users;
	}

	@Override
	public ErsUsers getErsUserById(int id) throws IOException {
		String sql = "select * from ers.ers_users where ers_users_id = ? ";
		ErsUsers ersUser = null;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, id);	
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				ersUser = new ErsUsers(rs.getInt("ers_users_id"),
									rs.getString("ers_username"),
									rs.getString("ers_password"),
									rs.getString("user_first_name"),
									rs.getString("user_last_name"),
									rs.getString("user_email"),
									rs.getInt("user_role_id"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return ersUser;
	}

	@Override
	public ErsUsers getErsUserByUsername(String username) throws IOException {
		String sql = "select * from ers.ers_users where ers_username = ?;"; 
		
		ErsUsers ersUser = null;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				ersUser = new ErsUsers(rs.getInt("ers_users_id"),
									rs.getString("ers_username"),
									rs.getString("ers_password"),
									rs.getString("user_first_name"),
									rs.getString("user_last_name"),
									rs.getString("user_email"),
									rs.getInt("user_role_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ersUser;
	}

	@Override
	public boolean updateErsUser(ErsUsers ersUser) {
		boolean result = false;
		String sql = "update ers.ers_users set ers_username = ?, ers_password = ?, user_first_name = ?, "
				+ "user_last_name = ?, user_email = ?"
				+ "where ers_users_id = ?;";
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, ersUser.getErsUsername());
			ps.setString(2, ersUser.getErsPassword());	
			ps.setString(3, ersUser.getUserFirstName());
			ps.setString(4, ersUser.getUserLastName());
			ps.setString(5, ersUser.getUserEmail());
			ps.setInt(6, ersUser.getErsUserId());
			
			if(ps.executeUpdate()>0) result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ErsUsers addErsUser(ErsUsers ersUser) {
		// TODO Auto-generated method stub
		return null;
	}

}


