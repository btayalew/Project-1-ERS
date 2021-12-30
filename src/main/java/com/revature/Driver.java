package com.revature;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;
import java.io.IOException;
import com.revature.controller.AuthController;
import com.revature.controller.ErsController;
import com.revature.controller.ErsUsersController;

public class Driver {
	public static void main(String[] args) throws IOException {

		Javalin app = Javalin.create((config) -> {
     		config.enableCorsForAllOrigins();
     		config.defaultContentType = "application/json";
     	});
     	app.start(7000);

     		/*
     		 * Headers to tell the browser that the Authorization header that we're using for our "token" 
     		 * in the Response body is safe to use, otherwise the browser may not accept it
     		 */
     	app.before(ctx -> {
     		   ctx.header("Access-Control-Allow-Headers", "Authorization");
     		   ctx.header("Access-Control-Expose-Headers", "Authorization");
     	});
     		
     	app.routes(() -> {
            path("reimbursements", () -> {
            	get(ErsController::getReimbursements);
            	post(ErsController::registerReimbursementClaim);
            	path("reimbAuthId", () -> {
            		get(ErsController::getReimbursements);
            		});
	        	path("{reimbId}", () -> {
	        		get(ErsController::getReimbursementByClaimId);
	        		put(ErsController::updateReimbursementClaim);
	        		});
            	});

     		path("auth", () ->{
     			post(AuthController::login);
     		});
     		path("ersUsers", () -> {
     			get(ErsUsersController::getErsUsers);
     			put(ErsUsersController::updateErsUserInfo);
     			path("{id}", () -> {
     				get(ErsUsersController::getErsUsersById);
     				put(ErsUsersController::updateErsUserInfo);
     			});

     		});
		});
	}
}

