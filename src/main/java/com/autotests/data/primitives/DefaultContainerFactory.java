package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;


public class DefaultContainerFactory implements IContainerFactory 
{

		public <C extends IContainer> C create(final Class<C> containerClass, final WebElement wrappedElement) 
		{
	        final C container = createInstanceOf(containerClass);
	        container.init(wrappedElement);
	        return container;
	    }

	    private <C extends IContainer> C createInstanceOf(final Class<C> containerClass)
	    {
	        try
	        {
	        	return containerClass.newInstance();
	        } 
	        catch (InstantiationException e)
	        {//TODO add specific Exception
	        	throw new RuntimeException(e);
	        }
	        catch (IllegalAccessException e)
	        {//TODO add specific Exception
	        	throw new RuntimeException(e);
	        }
	    }
}
