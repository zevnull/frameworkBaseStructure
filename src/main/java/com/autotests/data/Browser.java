package com.autotests.data;

/**
 * Enum which consist all available types of browser.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public enum Browser 
{
	FIREFOX("firefox"), IE("ie"), CHROME("chrome"), SAFARI("safari"), NONE("none");
	
	private String browser;
	
	private Browser(String browser)
	{
		this.browser = browser;
	}
	
	public String getBrowserType()
	{
		return this.browser;
	}
};
