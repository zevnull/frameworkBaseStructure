package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;

public interface IElementFactory 
{
	 <E extends IElement> E create(Class<E> elementClass, WebElement wrappedElement);
}
