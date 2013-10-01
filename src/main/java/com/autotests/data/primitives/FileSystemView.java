package com.autotests.data.primitives;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ddn.autotest.data.Point;
import com.ddn.autotest.data.exceptions.InvalidGraphException;
import com.ddn.autotest.utils.DMLogger;
import com.ddn.autotest.utils.browser.BrowserFactory;

/**
 * Custom FileSystemView component for Solution Overview page
 * 
 * @author Andrei_Tsiarenia
 * 
 */
public class FileSystemView extends AbstractContainer
{

  private final static String METRIC_SCREEN_NAME = "curentMetricScreenshot.png";

  @FindBy( id = "frameHandle" )
  private WebElement          fileSystemView;

  private static StringBuffer content            = new StringBuffer();

  private String              eol                = System.getProperty( "line.separator" );

  private List<Point>         rgb                = new ArrayList<Point>();

  /**
   * Types of solution
   * 
   */
  public enum SolutionType
  {
    GS, STORAGE
  }

  /**
   * 
   * Types of existing File System View GS metrics
   */
  public enum FileSystemViewGSMetrics
  {
    NETWORK_THROUGHPUT( 1 ), READ_WRITE_THROUGHPUT( 2 ), APP_READ_WRITE_THROUGHPUT( 3 ),
    META_OPEN_CLOSE_THROUGHPUT( 4 ), META_INODE_UPDATE_THROUGHPUT( 5 ), META_READ_DIRECTORY_THROUGHPUT( 6 );

    private int position;

    private FileSystemViewGSMetrics( int position )
    {
      this.position = position;
    }

    /**
     * Get necessary locator for specific metric
     * 
     * @return String - necessary locator
     */
    private String getLocator()
    {
      String locator = "#tabListID1 > li ";
      for( int i = 1; i < position; i++ )
      {
        locator += " + li ";
      }
      locator += " > a ";

      return locator;
    }

  }

  /**
   * 
   * Types of existing File System View Storage metrics
   */
  public enum FileSystemViewStorageMetrics
  {
    HOST_CHANNEL_READ_IOPS( 1 ), HOST_CHANNEL_WRITE_IOPS( 2 ), HOST_CHANNEL_TOTAL_THROUGHPUT( 3 );

    private int position;

    private FileSystemViewStorageMetrics( int position )
    {
      this.position = position;
    }

    /**
     * Get necessary locator for specific metric
     * 
     * @return String - necessary locator
     */
    private String getLocator()
    {
      String locator = ".nav.nav-tabs > li ";
      for( int i = 1; i < position; i++ )
      {
        locator += " + li ";
      }
      locator += " > a ";

      return locator;
    }

  }

  /**
   * 
   * Types of existing File System View NSDs metrics
   */
  public enum FileSystemViewNSDMetrics
  {
    CPU( 1 ), MEMORY( 2 ), NETWORK_THROUGHPUT( 3 ), READ_WRITE_THROUGHPUT( 4 ), APP_READ_WRITE_THROUGHPUT( 5 ),
    META_OPEN_CLOSE_THROUGHPUT( 6 ), META_INODE_UPDATE_THROUGHPUT( 7 ), META_READ_DIRECTORY_THROUGHPUT( 8 );

    private int position;

    private FileSystemViewNSDMetrics( int position )
    {
      this.position = position;
    }

    /**
     * Get necessary locator for specific metric
     * 
     * @return String - necessary locator
     */
    private String getLocator()
    {
      String locator = "#tabListID1 > li ";
      for( int i = 1; i < position; i++ )
      {
        locator += " + li ";
      }
      locator += " > a ";

      return locator;
    }

  }

  @Override
  public boolean isDisplayed()
  {
    DMLogger.methodStarted();
    boolean result = fileSystemView.isDisplayed();
    DMLogger.methodFinished( result );
    return result;
  }

  /**
   * Open specific GS metric tab
   * 
   * @param metric - metric name
   * @throws Exception
   * 
   * @return FileSystemView - open specific metric and return new instance of FileSystemView object
   */
  public FileSystemView openSpecificGSMetric( FileSystemViewGSMetrics metric ) throws Exception
  {
    BrowserFactory.getExistentWebDriver().switchTo().frame( "outerFrame" );
    DMLogger.methodStarted( metric );
    switch( metric )
    {
      case NETWORK_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewGSMetrics.NETWORK_THROUGHPUT.getLocator() ) ).click();
        break;

      case READ_WRITE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewGSMetrics.READ_WRITE_THROUGHPUT.getLocator() ) ).click();
        break;

      case APP_READ_WRITE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewGSMetrics.APP_READ_WRITE_THROUGHPUT.getLocator() ) ).click();
        break;

      case META_OPEN_CLOSE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewGSMetrics.META_OPEN_CLOSE_THROUGHPUT.getLocator() ) ).click();
        break;

      case META_INODE_UPDATE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewGSMetrics.META_INODE_UPDATE_THROUGHPUT.getLocator() ) ).click();
        break;

      case META_READ_DIRECTORY_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewGSMetrics.META_READ_DIRECTORY_THROUGHPUT.getLocator() ) ).click();
        break;

    }

    // TODO wait until graph loaded
    Thread.sleep( 2000 );
    DMLogger.methodFinished();
    return new FileSystemView();
  }

  /**
   * Open specific storage metric tab
   * 
   * @param metric - metric name
   * @throws Exception
   * 
   * @return FileSystemView - open specific metric and return new instance of FileSystemView object
   */
  public FileSystemView openSpecificStorageMetric( FileSystemViewStorageMetrics metric ) throws Exception
  {
    BrowserFactory.getExistentWebDriver().switchTo().frame( "outerFrame" );
    DMLogger.methodStarted( metric );
    switch( metric )
    {
      case HOST_CHANNEL_READ_IOPS:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewStorageMetrics.HOST_CHANNEL_READ_IOPS.getLocator() ) ).click();
        break;

      case HOST_CHANNEL_WRITE_IOPS:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewStorageMetrics.HOST_CHANNEL_WRITE_IOPS.getLocator() ) ).click();
        break;

      case HOST_CHANNEL_TOTAL_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewStorageMetrics.HOST_CHANNEL_TOTAL_THROUGHPUT.getLocator() ) )
          .click();
        break;

    }

    // TODO wait until graph loaded
    Thread.sleep( 2000 );
    DMLogger.methodFinished();
    return new FileSystemView();
  }

  /**
   * Open specific NSD metric tab
   * 
   * @param metric - metric name
   * @throws Exception
   * 
   * @return FileSystemView - open specific metric and return new instance of FileSystemView object
   */
  public FileSystemView openSpecificNSDMetric( FileSystemViewNSDMetrics metric ) throws Exception
  {
    BrowserFactory.getExistentWebDriver().switchTo().frame( "outerFrame" );
    DMLogger.methodStarted( metric );

    switch( metric )
    {
      case CPU:
        BrowserFactory.getExistentWebDriver().findElement( By.cssSelector( FileSystemViewNSDMetrics.CPU.getLocator() ) )
          .click();
        break;
      case MEMORY:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewNSDMetrics.MEMORY.getLocator() ) ).click();
        break;
      case NETWORK_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewNSDMetrics.NETWORK_THROUGHPUT.getLocator() ) ).click();
        break;
      case READ_WRITE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewNSDMetrics.READ_WRITE_THROUGHPUT.getLocator() ) ).click();
        break;
      case APP_READ_WRITE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewNSDMetrics.APP_READ_WRITE_THROUGHPUT.getLocator() ) ).click();
        break;
      case META_OPEN_CLOSE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewNSDMetrics.META_OPEN_CLOSE_THROUGHPUT.getLocator() ) ).click();
        break;
      case META_INODE_UPDATE_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewNSDMetrics.META_INODE_UPDATE_THROUGHPUT.getLocator() ) ).click();
        break;
      case META_READ_DIRECTORY_THROUGHPUT:
        BrowserFactory.getExistentWebDriver()
          .findElement( By.cssSelector( FileSystemViewNSDMetrics.META_READ_DIRECTORY_THROUGHPUT.getLocator() ) )
          .click();
        break;
    }

    // TODO wait until graph loaded
    Thread.sleep( 2000 );
    DMLogger.methodFinished();
    return new FileSystemView();
  }

  /**
   * Check that graph present and not "zero"
   * 
   */
  public void checkGraph( SolutionType solutionType ) throws Exception
  {
    List<Point> points = getPointsFromCurrentMetric( solutionType );
    int lowerLimit = 0;
    if( solutionType == SolutionType.GS )
    {
      lowerLimit = 194;
    }
    else if( solutionType == SolutionType.STORAGE )
    {
      lowerLimit = 192;
    }

    // TODO was 'points.size() == 0'
    String errorText;
    if( points.size() < 22 )
    {
      errorText = " Graph not present ";
      DMLogger.errorInMethod( errorText );
      throw new InvalidGraphException( errorText );
    }

    int zeroCounts = 0;
    for( Point point : points )
    {
      if( point.getY() > lowerLimit )
      {
        zeroCounts++;
      }
    }

    if( Math.abs( points.size() - zeroCounts ) < 21 )
    {
      errorText = " Graph is null";
      DMLogger.errorInMethod( errorText );
      throw new InvalidGraphException( errorText );
    }

    if( ( zeroCounts > 1 ) && ( points.size() % zeroCounts > 200 ) )
    {
      errorText = " One of graphs is null";
      DMLogger.errorInMethod( errorText );
      throw new InvalidGraphException( errorText );
    }

  }

  /**
   * Get Points entity from selected metric
   * 
   * @param solutionType - type of solution
   * @return List<Point>
   * @throws Exception
   */
  public List<Point> getPointsFromCurrentMetric( SolutionType solutionType ) throws Exception
  {
    File screenshot = ( ( TakesScreenshot ) BrowserFactory.getExistentWebDriver() ).getScreenshotAs( OutputType.FILE );
    BufferedImage fullImg = ImageIO.read( screenshot );
    BufferedImage eleScreenshot = null;
    switch( solutionType )
    {
      case GS:
        eleScreenshot = fullImg.getSubimage( 281, 381, 884, 198 );
        break;
      case STORAGE:
        eleScreenshot = fullImg.getSubimage( 587, 452, 1256, 197 );
        break;
    }
    ImageIO.write( eleScreenshot, "png", screenshot );
    FileUtils.copyFile( screenshot, new File( METRIC_SCREEN_NAME ) );
    rgb = new ArrayList<Point>();
    return parseImg( solutionType );
  }

  private List<Point> parseImg( SolutionType solutionType )
  {
    try
    {
      BufferedImage image = ImageIO.read( new File( METRIC_SCREEN_NAME ) );
      marchThroughImage( image, solutionType );
    }
    catch( IOException e )
    {
      throw new RuntimeException( "Can't read screenshot file " + METRIC_SCREEN_NAME, e );
    }
    return rgb;
  }

  private void marchThroughImage( BufferedImage image, SolutionType solutionType )
  {
    int w = image.getWidth();
    int h = image.getHeight();

    for( int i = 0; i < h; i++ )
    {
      for( int j = 0; j < w; j++ )
      {
        int pixel = image.getRGB( j, i );
        if( printPixelARGB( j, i, pixel, solutionType ) )
        {
          content.append( "x,y: " ).append( j ).append( ", " ).append( i ).append( eol );
        }
      }
    }
  }

  private boolean printPixelARGB( int x, int y, int pixel, SolutionType solutionType )
  {
    boolean result = false;
    int alpha = ( pixel >> 24 ) & 0xff;
    int red = ( pixel >> 16 ) & 0xff;
    int green = ( pixel >> 8 ) & 0xff;
    int blue = ( pixel ) & 0xff;
    if( ( red == 237 && green == 122 && blue == 83 ) || ( red == 136 && green == 187 && blue == 200 )
      || ( red == 188 && green == 166 && blue == 156 ) || ( red == 231 && green == 137 && blue == 105 )
      || ( red == 120 && green == 160 && blue == 170 ) )
    {
      // skip colors from agenda
      boolean condition = false;

      switch( solutionType )
      {
        case GS:
          condition = ( ( x < 5 || x > 19 ) && ( y < 7 || y > 17 ) ) || ( ( x < 5 || x > 19 ) && ( y < 29 || y > 39 ) );
          break;
        case STORAGE:
          condition = ( ( x < 5 || x > 19 ) && ( y < 7 || y > 17 ) ) || ( ( x < 5 || x > 19 ) && ( y < 29 || y > 39 ) );
          break;
      }
      if( condition )
      {
        content.append( "argb: " ).append( alpha ).append( ", " ).append( red ).append( ", " ).append( green )
          .append( ", " ).append( blue ).append( eol );

        rgb.add( new Point( x, y, red, green, blue ) );

        result = true;
      }
    }
    return result;
  }

}
