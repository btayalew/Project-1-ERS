package com.revature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.exceptions.UserNotFoundException;

public class LoggingDriver {

	private static Logger log = LogManager.getRootLogger();
	
	public static void main(String[] args) {
		log.trace("This is a trace!");
		log.debug("This is a debug!");
		log.info("This is an info!");
		log.warn("This is a warning!");
		log.error("This is an error!");
		log.fatal("This is fatal!");
		
		try {
			throwErsException();
		} catch (UserNotFoundException e) {
			log.error("Exception was thrown and handled: " + e.getMessage());
		}
	}

	public static void throwErsException() throws UserNotFoundException {
		throw new UserNotFoundException("My Exception is being thrown here");
	}
}