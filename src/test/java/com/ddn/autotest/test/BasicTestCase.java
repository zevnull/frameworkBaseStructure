package com.ddn.autotest.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;

import com.autotests.data.Browser;
import com.autotests.data.UserData;
import com.utils.ConfigProperties;


public class BasicTestCase 
{

	protected static WebDriver driver;
	
	public UserData admin = new UserData("alex", "qwerty");
//TODO !!!! can be moved to @BeforeTest ;)
	protected WebDriver getWebDriver() 
	{
	
		
		
		if (driver == null) 
		{
			//TODO browser type
			if (Browser.FIREFOX.getBrowserType().equals(ConfigProperties.getProperty("browser")))
			{
				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(Long.parseLong(ConfigProperties.getProperty("imp.wait")), TimeUnit.SECONDS);
			}
			
			//TODO add other browser
			
			if (Browser.NONE.getBrowserType().equals(ConfigProperties.getProperty("browser")))
			{
				//TODO without browser instance ...
				
			}
		}
		
				
		return driver;
	}

	@AfterTest
	public void tearDown() throws Exception 
	{
		driver.quit();
	}
	
	
}