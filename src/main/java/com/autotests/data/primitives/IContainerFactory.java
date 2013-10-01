package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;

public interface IContainerFactory 
{
	 <C extends IContainer> C create(Class<C> containerClass, WebElement wrappedElement);
}
