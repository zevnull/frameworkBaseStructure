package com.autotests.data.primitives;

import java.lang.reflect.Field;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * ExtendedFieldDecorator it's wrapper for DefaultFieldDecorator. This is class will be using
 * to initialize the class fields of our ui pages (PageFactory.initElements)
 * 
 * @author Andrei_Tsiarenia
 *
 */
public class ExtendedFieldDecorator extends DefaultFieldDecorator 
{
    private IElementFactory elementFactory = new DefaultElementFactory();
    private IContainerFactory containerFactory = new DefaultContainerFactory();

    public ExtendedFieldDecorator(final SearchContext searchContext) 
    {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    @Override
    public Object decorate(final ClassLoader loader, final Field field) 
    {
        if (IContainer.class.isAssignableFrom(field.getType())) 
        {
            return decorateContainer(loader, field);
        }
        if (IElement.class.isAssignableFrom(field.getType())) 
        {
            return decorateElement(loader, field);
        }
        return super.decorate(loader, field);
    }

    @SuppressWarnings("unchecked")
	private Object decorateElement(final ClassLoader loader, final Field field) 
    {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        return elementFactory.create((Class<? extends IElement>) field.getType(), wrappedElement);
    }

    private ElementLocator createLocator(final Field field)
    {
        return factory.createLocator(field);
    }

    @SuppressWarnings("unchecked")
	private Object decorateContainer(final ClassLoader loader, final Field field)
    {
        final WebElement wrappedElement = proxyForLocator(loader, createLocator(field));
        final IContainer container = containerFactory.create((Class<? extends IContainer>) field.getType(), wrappedElement);

        PageFactory.initElements(new ExtendedFieldDecorator(wrappedElement), container);
        return container;
    }
}
