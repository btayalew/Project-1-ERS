package com.revature.controller;

import java.io.IOException;

import com.revature.service.AuthService;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;

public class AuthController {

	private static AuthService as = new AuthService();
	
	public static void login(Context ctx) throws IOException {
		// leverages x-www-form-urlencoded which prevents the parameters from being displayed in the url
		String username = ctx.formParam("username");
		String password = ctx.formParam("password");
		System.out.println(username);
		System.out.println(password);
		String token = null;
		
		if(username != null && password != null) {
			token = as.login(username, password);
		}
		
		if(token != null) {
			ctx.header("Authorization", token);
			ctx.status(HttpCode.OK);
		} else {
			ctx.status(HttpCode.NOT_FOUND);
		}
	}
	public static void logout(Context ctx) {
		ctx.header("Authorization", null);
		ctx.status(HttpCode.OK);
	}

}
