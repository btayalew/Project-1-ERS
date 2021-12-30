package com.revature.controller;

import java.io.IOException;
import java.util.List;
import com.revature.model.ErsReimbursement;
import com.revature.service.AuthService;
import com.revature.service.ErsService;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class ErsController {

	private static ErsService ers = new ErsService();
//	private static AuthService as = new AuthService();

	public static void getReimbursements(Context ctx) throws IOException {
		
		String status = ctx.queryParam("status");
		String reimbAuthId = ctx.queryParam("reimbAuthId");
		
		if(status != null){
			List<ErsReimbursement> reimbByStatus = ers.getReimbByStatus(status);
			ctx.json(reimbByStatus);
			ctx.status(HttpCode.OK);
			
		}else if(reimbAuthId != null){
			int authId = Integer.parseInt(reimbAuthId);
			List<ErsReimbursement> claimByAuth = ers.getReimbByAuthId(authId);
			if (claimByAuth != null) {
				ctx.json(claimByAuth);
				ctx.status(HttpCode.OK);
			} else {
				ctx.status(HttpCode.NOT_FOUND);
			}
		}else{
			ctx.json(ers.getAllReimbursements());
		}
	}
	
	public static void getReimbursementByClaimId(Context ctx) throws IOException {
		int intId = Integer.parseInt(ctx.pathParam("reimbId"));
		ErsReimbursement reimbClaim = ers.getReimbById(intId);
		if (reimbClaim != null) {
			ctx.json(reimbClaim);
			ctx.status(HttpCode.OK);
		} else {
			ctx.status(HttpCode.NOT_FOUND);
		}
	}

	public static void registerReimbursementClaim(Context ctx) throws IOException {

		ErsReimbursement newClaim = ers.addReimbursement(ctx.bodyAsClass(ErsReimbursement.class)); 

		if (newClaim == null) {
			ctx.status(HttpCode.BAD_REQUEST);
		} else {
			ctx.status(HttpCode.CREATED);
		}

	}
	
	public static void updateReimbursementClaim(Context ctx) throws IOException {
		boolean newClaim = ers.updateReimbursement(ctx.bodyAsClass(ErsReimbursement.class)); 
		if (newClaim == false) {
			ctx.status(HttpCode.BAD_REQUEST);
		} else {
			ctx.status(HttpCode.CREATED);
		}
	}
}
