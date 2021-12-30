package com.revature.controller;

import java.io.IOException;
import java.util.List;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import com.revature.model.ErsUsers;
import com.revature.service.AuthService;
import com.revature.service.ErsUsersService;

public class ErsUsersController {
	
	private static ErsUsersService eus = new ErsUsersService();
	private static AuthService as = new AuthService();

	public static void getErsUsers(Context ctx) throws IOException {
		List<ErsUsers> ersUsers = eus.getErsUsers();

		ctx.json(ersUsers);
		ctx.status(HttpCode.OK);
	}

	public static void getErsUsersById(Context ctx) throws IOException {
		int id = Integer.parseInt(ctx.pathParam("id"));

		ErsUsers employee = eus.getErsUserById(id);

		if (employee != null) {
			ctx.json(employee);
			ctx.status(HttpCode.OK);
		} else {
			ctx.status(HttpCode.NOT_FOUND);
		}
	}
public static void updateErsUserInfo(Context ctx) throws IOException {
		
		String token = ctx.header("Authorization");
		
		if(!as.checkPermission(token)) {
			ctx.status(HttpCode.UNAUTHORIZED);
			return;
		}
		
		int id = Integer.parseInt(ctx.pathParam("id"));
		ErsUsers ersUser = ctx.bodyAsClass(ErsUsers.class);
		ersUser.setErsUserId(id);
		
		if (eus.updateErsUserInfo(ersUser)) {
			ctx.status(HttpCode.OK);
		} else {
			ctx.status(400);
		}
	}
}
