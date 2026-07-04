package com.seleniumFW.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
	
	public static Logger getLogger(Class<?> cl)
	{
		return LogManager.getLogger();
	}

}
