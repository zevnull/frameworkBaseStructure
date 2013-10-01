package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.autotests.data.UserData;
import com.autotests.data.primitives.ExtendedFieldDecorator;
import com.utils.ConfigProperties;

/**
 * Class which describes Login page elements and provides functionality for working with this page.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public class LoginPage extends Page 
{

//	@FindBy(linkText = "Sign in")
//	public WebElement linkSignIn;
	
	@FindBy(id = "username")
	public WebElement fieldUsername;

	@FindBy(id = "password")
	public WebElement fieldPassword;

	@FindBy(id = "loginBtn")
	public WebElement buttonLogin;
	
	/**
	 * LoginPage constructor
	 * 
	 * @param driver - WebDriver instance
	 * 
	 */
	public LoginPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	/**
	 * Login in the application with specific credentials.
	 * 
	 * @param admin - user data "entity" which will be used for login 
	 * @return
	 */
	public HomePage loginAs(UserData admin) 
	{
//		linkSignIn.click();
		type(fieldUsername, admin.name);
		type(fieldPassword, admin.password);
		buttonLogin.click();
		return PageFactory.initElements(driver, HomePage.class);
	}
	
	/**
	 * Open Login page
	 */
	@Override
	public void open() 
	{
		driver.get(ConfigProperties.getProperty("login.url"));
	}
	

}
