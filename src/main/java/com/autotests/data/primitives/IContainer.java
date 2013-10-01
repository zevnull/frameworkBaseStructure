package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;

public interface IContainer extends IElement 
{
	void init(WebElement wrappedElement);
}
