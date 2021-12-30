package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.ErsReimbursement;
import com.revature.util.ConnectionUtil;
import com.revature.util.LogUtil;

public class ErsReimbursementPostgres implements ErsReimbursementDao {

	@Override
	public List<ErsReimbursement> getErsReimbursements() throws IOException {
		String sql = "select * from ers.ers_reimbursement;";
		List<ErsReimbursement> reimbs = new ArrayList<>();
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				ErsReimbursement reimb = 
					new ErsReimbursement(rs.getInt("reimb_id"),
										rs.getDouble("reimb_amount"),
										rs.getTimestamp("reimb_submitted"),
										rs.getTimestamp("reimb_resolved"),
										rs.getString("reimb_description"),
										rs.getString("reimb_receipt"),
										rs.getInt("reimb_author"),
										rs.getInt("reimb_resolver"),
										rs.getInt("reimb_status_id"),
										rs.getInt("reimb_type_id"));
				reimbs.add(reimb);
			}
		} catch (SQLException e) {
			LogUtil.whatHappend("Exception in ErsReimbursementDao");
			e.printStackTrace();
		} 
		return reimbs;
	}

	@Override
	public ErsReimbursement getErsReimbursementById(int id) throws IOException {
		String sql = "select * from ers.ers_reimbursement where reimb_id = ? ";
		ErsReimbursement reimb = null;
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, id);	
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				reimb = new ErsReimbursement(rs.getInt("reimb_id"),
											rs.getDouble("reimb_amount"),
											rs.getTimestamp("reimb_submitted"),
											rs.getTimestamp("reimb_resolved"),
											rs.getString("reimb_description"),
											rs.getString("reimb_receipt"),
											rs.getInt("reimb_author"),
											rs.getInt("reimb_resolver"),
											rs.getInt("reimb_status_id"),
											rs.getInt("reimb_type_id"));
			}
		} catch (SQLException e) {
			LogUtil.whatHappend("Exception in ErsReimbursementDao");
			e.printStackTrace();
		} 
		return reimb;
	}

	@Override
	public List<ErsReimbursement> getErsReimbursementByStatusId(int statusId) throws IOException {
		
		List<ErsReimbursement> reimbs = new ArrayList<>();
		
		String sql = "select * from ers.ers_reimbursement where reimb_status_id = ? ";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, statusId);	
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ErsReimbursement reimb = 
					new ErsReimbursement(rs.getInt("reimb_id"),
										rs.getDouble("reimb_amount"),
										rs.getTimestamp("reimb_submitted"),
										rs.getTimestamp("reimb_resolved"),
										rs.getString("reimb_description"),
										rs.getString("reimb_receipt"),
										rs.getInt("reimb_author"),
										rs.getInt("reimb_resolver"),
										rs.getInt("reimb_status_id"),
										rs.getInt("reimb_type_id"));
				reimbs.add(reimb);
			}
		} catch (SQLException e) {
			LogUtil.whatHappend("Exception in ErsReimbursementDao");
			e.printStackTrace();
		} 
		return reimbs;
	}

	@Override
	public List<ErsReimbursement> getErsReimbursementByAuthorId(int authId) throws IOException {
		
		List<ErsReimbursement> reimbs = new ArrayList<>();
		
		String sql = "select * from ers.ers_reimbursement where reimb_author = ? ";
		
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setInt(1, authId);	
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				ErsReimbursement reimb = 
					new ErsReimbursement(rs.getInt("reimb_id"),
										rs.getDouble("reimb_amount"),
										rs.getTimestamp("reimb_submitted"),
										rs.getTimestamp("reimb_resolved"),
										rs.getString("reimb_description"),
										rs.getString("reimb_receipt"),
										rs.getInt("reimb_author"),
										rs.getInt("reimb_resolver"),
										rs.getInt("reimb_status_id"),
										rs.getInt("reimb_type_id"));
				reimbs.add(reimb);
			}
		} catch (SQLException e) {
			LogUtil.whatHappend("Exception in ErsReimbursementDao");
			e.printStackTrace();
		} 
		return reimbs;
	}

	@Override
	public ErsReimbursement addErsReimbursement(ErsReimbursement er) throws IOException {
		ErsReimbursement newReimb = null;
			
		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			String sql = "insert into ers.ers_reimbursement (reimb_amount,"
					+ "reimb_submitted,reimb_resolved,reimb_description,reimb_receipt,reimb_author,"
					+ "reimb_resolver,reimb_status_id,reimb_type_id) values (?,?,?,?,?,?,?,?,?) returning reimb_id;";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setDouble(1, er.getReimbAmount());
			ps.setTimestamp(2, er.getReimbSubmitted());
			ps.setTimestamp(3, er.getReimbResolved());
			ps.setString(4, er.getReimbDescription());
			ps.setString(5, er.getReimbReceipt());
			ps.setInt(6, er.getReimbAuthor());
			ps.setInt(7, er.getReimbResolver());
			ps.setInt(8, er.getReimbStatusId());
			ps.setInt(9, er.getReimbTypeId());

			ResultSet rs = ps.executeQuery();

			if(rs.next()) {
				newReimb = er;
				newReimb.setReimbId(rs.getInt("reimb_id"));
			}
		} catch (SQLException e) {
			LogUtil.whatHappend("Exception in ErsReimbursementDao");
			e.printStackTrace();
		}
		
		return newReimb;
	}

	@Override
	public boolean updateErsReimbursement(ErsReimbursement er) throws IOException {
		String sql = "update ers.ers_reimbursement set reimb_amount = ?, "
				+ "reimb_resolved = ?, reimb_receipt = ?, reimb_resolver = ?, "
				+ "reimb_status_id = ?, reimb_type_id = ? where reimb_id = ?;";

		boolean result = false;

		try (Connection con = ConnectionUtil.getConnectionFromFile()){
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setDouble(1, er.getReimbAmount());
			ps.setTimestamp(2, er.getReimbResolved());
			ps.setString(3, er.getReimbReceipt());
			ps.setInt(4, er.getReimbResolver());
			ps.setInt(5, er.getReimbStatusId());
			ps.setInt(6, er.getReimbTypeId());
			ps.setInt(7, er.getReimbId());

			if(ps.executeUpdate()>0) result = true;

		} catch (SQLException e) {
			LogUtil.whatHappend("Exception in ErsReimbursementDao");
			e.printStackTrace();
		}
		return result;
	}

}
