package com.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Base abstract class for all page classes. All page classes MUST extends this class.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public abstract class Page 
{

	protected WebDriver driver;

	/**
	 * Page constructor
	 * 
	 * @param driver - WebDriver instance
	 * 
	 */
	public Page(WebDriver driver) 
	{
		this.driver = driver;
	}

	/**
	 * Input text in the specific WebElement.
	 * 
	 * @param webElement - webElement 
	 * @param text - text for input
	 */
	protected void type(WebElement webElement, String text) 
	{
		webElement.clear();
		webElement.sendKeys(text);
	}
	
	public abstract void open();
	
	/**
	 * Check that specific element is present.
	 * 
	 * @param element - WebElement
	 * @return true/false
	 */
	public boolean isElementPresent(WebElement element) 
	{
		try 
		{
			element.isDisplayed();
			return true;
		} 
		catch (NoSuchElementException e) 
		{
			return false;
		}
	}
}
