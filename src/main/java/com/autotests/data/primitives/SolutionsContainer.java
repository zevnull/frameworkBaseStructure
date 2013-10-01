package com.autotests.data.primitives;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ddn.autotest.utils.DMLogger;
import com.ddn.autotest.utils.browser.BrowserFactory;

/**
 * SolutionsContainer custom element for Solutions Overview page
 * 
 * @author Andrei_Tsiarenia
 * 
 */
public class SolutionsContainer extends AbstractContainer
{

  @FindBy( id = "solutionsContainer" )
  private WebElement          solutionsContainer;

  @FindBy( xpath = "//a[contains(@href,'/dmview/solutions-overview')]" )
  private Link                navigationTreeName;

  private final static String SOLUTIONS_CONTAINER_CSS_LOCATOR = "div#solutionsContainer > div ";

  private final static String EXPAND_NSDS_TREE_LOCATOR        = ".icon12.entypo-icon-plus-2.gray";

  /**
   * Enum which consist all available types of solutions.
   * 
   * 
   */
  public static enum SOLUTION_TYPE
  {
    SFA( "sfa" ), GRID_SCALER( "gs" );
    private String type;

    SOLUTION_TYPE( String type )
    {
      this.type = type;
    }

    @Override
    public String toString()
    {
      return "SOLUTION_TYPE{" + "type='" + type + '\'' + '}';
    }

    public String getType()
    {
      return this.type;
    }
  }

  @Override
  public boolean isDisplayed()
  {
    DMLogger.methodStarted();
    boolean result = solutionsContainer.isDisplayed();
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Get "Solutions" (Filesystems) count
   * 
   * @return counts of "Solutions" (Filesystems)
   * @throws Exception - if WebDriver not initialized yet
   */
  public int getSolutionsCount() throws Exception
  {
    DMLogger.methodStarted();
    int result = -1;
    result =
      BrowserFactory.getExistentWebDriver().findElements( By.cssSelector( SOLUTIONS_CONTAINER_CSS_LOCATOR ) ).size();
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Get "Solutions" (Filesystems) names
   * 
   * @return list of of "Solutions" (Filesystems) name.
   * @throws Exception - if WebDriver not initialized yet
   */
  public List<String> getSolutionsName() throws Exception
  {
    DMLogger.methodStarted();
    List<String> result = new ArrayList<String>();
    final int solutionsCount = getSolutionsCount();
    StringBuffer tmpLocator = new StringBuffer();
    tmpLocator.append( SOLUTIONS_CONTAINER_CSS_LOCATOR );
    for( int i = 0; i < solutionsCount; i++ )
    {
      result.add( BrowserFactory.getExistentWebDriver().findElement( By.cssSelector( tmpLocator.toString() ) )
        .getAttribute( "id" ) );
      tmpLocator.append( " + div " );
    }
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Open File System View page by solution name
   * 
   * @param solutionName - the name of solution
   * @throws Exception
   */
  public FileSystemView openFileSystemViewBySolutionName( String solutionName ) throws Exception
  {

    DMLogger.methodStarted( solutionName );
    BrowserFactory.getExistentWebDriver().findElement( By.xpath( "//a[contains(text(),'" + solutionName + "')]" ) )
      .click();
    // TODO add wait element for iframe and wait values
    Thread.sleep( 5000 );
    FileSystemView result = new FileSystemView();
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Open File System View page by NSD name
   * 
   * @param nsdName - the name of NSD
   * @throws Exception
   */
  public FileSystemView openFileSystemViewByNSDName( String nsdName ) throws Exception
  {

    DMLogger.methodStarted( nsdName );
    // expand NSDs tree
    List<WebElement> plus =
      BrowserFactory.getExistentWebDriver().findElements( By.cssSelector( EXPAND_NSDS_TREE_LOCATOR ) );
    for( int i = 0; i < plus.size(); i++ )
    {
      plus.get( i ).click();
    }

    BrowserFactory.getExistentWebDriver().findElement( By.xpath( "//a[contains(@oldtitle,'" + nsdName + "')]" ) )
      .click();
    // TODO add wait element for iframe and wait values
    Thread.sleep( 5000 );
    FileSystemView result = new FileSystemView();
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Get navigation tree name
   * 
   * @return String - navigation tree name
   */
  public String getNavigationTreeName()
  {
    DMLogger.methodStarted();
    String result = "";
    result = navigationTreeName.getText();
    DMLogger.methodFinished( result );
    return result;
  }
}
