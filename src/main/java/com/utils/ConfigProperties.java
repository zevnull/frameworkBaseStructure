package com.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Class for work with system properties.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public class ConfigProperties
{
	
	private static Properties PROPERTIES;

	static 
	{		
		PROPERTIES = new Properties();		
		URL props = ClassLoader.getSystemResource("config.properties");
		try
		{						
			PROPERTIES.load(props.openStream());
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}	
	
	/**
	 * Find propertie by key.
	 * 
	 * @param key - propertie key.
	 * @return value of this propertie.
	 */
	public static String getProperty(String key) 
	{
		return PROPERTIES.getProperty(key);
	}
}
