package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;

/**
 * AbstractContainer class which provides common methods for composite elements.
 * 
 * @author Andrei_Tsiarenia
 * 
 */
public abstract class AbstractContainer implements IContainer
{

  private WebElement wrappedElement;





  public final void init( final WebElement wrappedElement )
  {
    this.wrappedElement = wrappedElement;
  }
}
