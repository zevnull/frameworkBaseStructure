package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;

/**
 * AbstractElement class which provides common methods for elements.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public class AbstractElement implements IElement
{

	protected final WebElement wrappedElement;

	/**
	 * Constructor for AbstractElement. It's simple wrapper for WebElement
	 * 
	 * @param wrappedElement - WebElement
	 */
	protected AbstractElement(final WebElement wrappedElement) 
	{
		this.wrappedElement = wrappedElement;
	}

	/**
	 * Check is element displayed.
	 * 
	 * @return true/false
	 */
	public boolean isDisplayed() 
	{
		return wrappedElement.isDisplayed();
	}

}
