package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;

/**
 * Class which provides common methods for work with custom control Button.
 * 
 * @author Andrei_Tsiarenia
 *
 */
public class Button extends AbstractElement implements IButton 
{
	/**
	 * Constructor for Button. Simple wrapper for WebElement
	 * 
	 * @param wrappedElement - WebElement
	 */
	 protected Button(final WebElement wrappedElement) 
	 {
		 super(wrappedElement);
	 }

	 /**
	  * Click to the button.
	  */
	 public void click() 
	 {
	        wrappedElement.click();
	 }

}
