package com.ddn.autotest.test;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import com.pages.LoginPage;
import com.ddn.autotest.utils.DMLogger;
import com.ddn.autotest.utils.browser.BrowserName;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

/**
 * Base test class for Login
 * 
 * @author Andrei_Tsiarenia
 * 
 */
public class LoginTestBase extends BasicTestCase
{

  public LoginTestBase()
  {
    Settings.setProperty( SettingsKeys.BROWSER, BrowserName.FIREFOX.toString() );
  }

  @BeforeMethod ( alwaysRun = true, groups = { "LoginTestBase" } )
  protected void beforeMethod() throws Exception
  {
    loginPage = new LoginPage( browser.getWebDriver() );
    loginPage.open();
  }

}
