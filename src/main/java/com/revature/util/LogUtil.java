package com.revature.util;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LogUtil {
	private static Logger log = LogManager.getRootLogger();
	public static void whatHappend(String note){
        log.error(note);
    }
}
