package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;

/**
 * Class which provides common methods for work with custom control TextField.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public class TextField extends AbstractElement implements ITextField 
{

	/**
	 *  Constructor for TextField. Simple wrapper for WebElement
	 * 
	 * @param wrappedElement - WebElement
	 */
	protected TextField(final WebElement wrappedElement) 
	{
		super(wrappedElement);
	}

	/**
	 * Input text.
	 * 
	 * @param text - text for input.
	 */
	public void type(final String text) 
	{
		wrappedElement.sendKeys(text);
	}

	/**
	 * Clear TextField. All text will be deleted.
	 */
	public void clear() 
	{
		wrappedElement.clear();
	}

	/**
	 * Clear TextField and input necessary text.
	 * 
	 * @param text - text for input.
	 */
	public void clearAndType(final String text) 
	{
		clear();
		type(text);
	}

}
