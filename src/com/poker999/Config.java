package com.poker999;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @author Valloon Project
 * @version 1.0 @2019-07-03
 */
public class Config {

	public static final boolean DEV_MODE = true;
	private static final String CONFIG_PROPERTIES = "/config.properties";
//	public static final String JDBC_PROPERTIES = "/jdbc.properties";
	public static final String JDBC_PROPERTIES = "/jdbc-dev.properties";
	public static int DEBUG = 0;
	public static int INTERVAL_STATUS = 10;

	static {
		try {
			Properties configProperties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(CONFIG_PROPERTIES));
			String debugString = configProperties.getProperty("debug");
			String intervalStatusString = configProperties.getProperty("interval_status");
			if (debugString != null)
				DEBUG = Integer.parseInt(debugString);
			if (intervalStatusString != null)
				INTERVAL_STATUS = Integer.parseInt(intervalStatusString);

		} catch (IOException e) {
			System.out.println(">>> Error in reading config.properties ...\r\n" + e.getMessage());
		}
	}

}