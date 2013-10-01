package com.autotests.data.primitives;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ddn.autotest.data.exceptions.AppNotPresentException;
import com.ddn.autotest.utils.DMLogger;
import com.ddn.autotest.utils.browser.BrowserFactory;
import com.google.common.base.Predicate;

/**
 * Custom User ID element for Landing page
 * 
 * @author: Andrei_Tsiarenia
 */
public class UserIDElement extends AbstractContainer
{
  @FindBy( css = ".dropdown-toggle > span" )
  private static WebElement userIDElement;

  public UserIDElement() throws Exception
  {
    PageFactory.initElements( new ExtendedFieldDecorator( BrowserFactory.getExistentWebDriver() ), this );
  }
  /**
   * 
   * Types of existing applications (Cluster Definer, Callhome)
   */
  public enum ExistingApplications
  {
    CLUSTER_DEFINER( "Cluster Definer", ".menu>ul>li + li > a" ), CALLHOME( "Call Home", ".menu>ul>li:first-child > a" );

    @Override
    public String toString()
    {
      return "ExistingApplications{" + "appName='" + appName + '\'' + ", element=" + element + ", locator='" + locator
        + '\'' + '}';
    }

    private final String     appName;

    private final WebElement element;

    private final String     locator;

    private ExistingApplications( String appName, String locator )
    {
      this.appName = appName;
      this.locator = locator;
      try
      {
        element = BrowserFactory.getExistentWebDriver().findElement( By.cssSelector( locator ) );
      }
      catch( Exception e )
      {
        String errorText = "Element with locator '" + locator + "' not found ";
        DMLogger.errorInMethod( errorText );
        throw new RuntimeException( errorText );
      }

    }

    public String getAppName()
    {
      return appName;
    }

    public void openApp() throws Exception
    {
      userIDElement.click();
      element.click();
      if( ( CLUSTER_DEFINER.getAppName() ).equals( appName ) )
      {
        isClusterDefinerLoaded();
      }

    }

    private void isClusterDefinerLoaded() throws Exception
    {
      BrowserFactory.getExistentWebDriver().switchTo().frame( "outerFrame" );
      new WebDriverWait( BrowserFactory.getExistentWebDriver(), 10 ).until( new Predicate<WebDriver>()
      {
        public boolean apply( WebDriver arg0 )
        {
          return arg0.findElement( By.id( "ClusterDefiner_header_hd-textEl" ) ).isDisplayed();
        }
      } );
    }

  };

  @Override
  /**
   * Check that UserIDElement component displayed
   */
  public boolean isDisplayed()
  {
    DMLogger.methodStarted();
    boolean result = userIDElement.isDisplayed();
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Get list of available applications for current user
   * 
   * @return List <String> - list of available applications for current user
   */
  public List<String> getListOfAvailableApps()
  {
    DMLogger.methodStarted();
    userIDElement.click();
    List<String> availableApps = new ArrayList<String>();
    List<WebElement> availableAppsElements = new ArrayList<WebElement>();
    try
    {
      availableAppsElements = BrowserFactory.getExistentWebDriver().findElements( By.cssSelector( ".menu>ul>li>a" ) );
    }
    catch( Exception e )
    {
      String errorText =
        "Error in the 'BrowserFactory.getExistentWebDriver().findElements(By.cssSelector(\".menu>ul>li>a\"))'";
      DMLogger.errorInMethod( errorText, e );
      throw new RuntimeException( errorText, e );
    }

    for( WebElement availableAppsElement : availableAppsElements )
    {
      availableApps.add( availableAppsElement.getText() );
    }
    userIDElement.click();
    DMLogger.methodFinished( availableApps );
    return availableApps;
  }

  /**
   * Get ID for current user
   * 
   * @return String - current user ID
   */
  public String getUserID()
  {
    DMLogger.methodStarted();
    String result = "";
    result = userIDElement.getText();
    DMLogger.methodFinished( result );
    return result;
  }

  public void openApp( ExistingApplications app ) throws Exception
  {
    List<String> availableApps = new ArrayList<String>();
    availableApps = getListOfAvailableApps();

    if( !availableApps.contains( app.getAppName() ) )
    {
      String errorText = "app.getAppName() + \" not present for user: \" + getUserID()";
      DMLogger.errorInMethod( errorText );
      throw new AppNotPresentException( errorText );
    }

    switch( app )
    {
      case CLUSTER_DEFINER:
        ExistingApplications.CLUSTER_DEFINER.openApp();
        break;
      case CALLHOME:
        UserIDElement.ExistingApplications.CALLHOME.openApp();
        break;
    }

  }
}
