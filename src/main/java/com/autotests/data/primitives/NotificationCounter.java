package com.autotests.data.primitives;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ddn.autotest.utils.DMLogger;

/**
 * Custom NotificationCounter element for Landing page
 * 
 * @author Andrei_Tsiarenia
 * 
 */
public class NotificationCounter extends AbstractContainer
{

  @FindBy( className = "btn-group" )
  private WebElement notificationCounter;

  @FindBy( id = "notifyCriticalHandle" )
  private Button     critical;

  @FindBy( id = "notifyWarningHandle" )
  private Button     warning;

  @FindBy( id = "notifyInfoHandle" )
  private Button     info;

  @FindBy( id = "notifyAllHandle" )
  private Button     all;

  /**
   * 
   * Types of existing notification
   */
  public enum NotificationType
  {
      CRITICAL,
      WARNING,
      INFO,
      ALL

  };





  @Override
  /**
   * Check that NotificationCounter component displayed
   */
  public boolean isDisplayed()
  {
    DMLogger.methodStarted();
    boolean result = notificationCounter.isDisplayed();
    DMLogger.methodFinished( result );
    return result;
  }





  /**
   * Get specific notification count
   * 
   * @param notificationType
   * @return int - count
   */
  public int getNotificationCount( NotificationType notificationType )
  {
    DMLogger.methodStarted( notificationType );
    int result = 0;
    switch( notificationType )
    {
      case CRITICAL:
        result = Integer.valueOf( critical.getText() );
        break;

      case WARNING:
        result = Integer.valueOf( warning.getText() );
        break;

      case INFO:
        result = Integer.valueOf( info.getText() );
        break;

      case ALL:
        result = Integer.valueOf( all.getText() );
        break;

    }

    DMLogger.methodFinished( result );
    return result;
  }

}
