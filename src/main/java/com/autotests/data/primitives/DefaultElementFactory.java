package com.autotests.data.primitives;

import java.lang.reflect.InvocationTargetException;
import org.openqa.selenium.WebElement;


public class DefaultElementFactory implements IElementFactory 
{

	public <E extends IElement> E create(final Class<E> elementClass, final WebElement wrappedElement) 
	{
			try
			{
	            return findImplementationFor(elementClass)
	                    .getDeclaredConstructor(WebElement.class)
	                    .newInstance(wrappedElement);
	        }
			catch (InstantiationException e) 
			{//TODO add specific Exception
	            throw new RuntimeException(e);
	        }
			catch (IllegalAccessException e) 
			{//TODO add specific Exception
	            throw new RuntimeException(e);
	        }
			catch (InvocationTargetException e) 
			{//TODO add specific Exception
	            throw new RuntimeException(e);
	        }
			catch (NoSuchMethodException e) 
			{//TODO add specific Exception
	            throw new RuntimeException(e);
	        }
	}

	    @SuppressWarnings("unchecked")
		private <E extends IElement> Class<? extends E> findImplementationFor(final Class<E> elementClass) 
	    {
	        try 
	        {
	        	//*********************
	            //return (Class<? extends E>) Class.forName(format("{0}.{1}Impl", getClass().getPackage().getName(), elementClass.getSimpleName()));
	        	//**********************
	        	return (Class<? extends E>) Class.forName(elementClass.getName());
	        }
	        catch (ClassNotFoundException e) 
	        {//TODO add specific Exception
	            throw new RuntimeException(e);
	        }
	    }

}
