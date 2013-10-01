package com.ddn.autotest.test;

import java.util.Random;
import java.util.Set;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import com.pages.LoginPage;
import com.pages.SolutionsOverviewPage;
import com.autotests.data.primitives.FileSystemView;
import com.ddn.autotest.utils.DMLogger;
import com.utils.SolutionsOverviewUtils;
import com.ddn.autotest.utils.browser.BrowserName;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

/**
 * Basic class for NSDs metrics tests
 * 
 * User: Andrei_Tsiarenia
 */
public class NSDsMetricsBase extends BasicTestCase
{
  public NSDsMetricsBase()
  {
    Settings.setProperty( SettingsKeys.BROWSER, BrowserName.FIREFOX.toString() );
  }

  @BeforeMethod ( alwaysRun = true, groups = { "NSDsMetricsBase" } )
  protected void beforeMethod() throws Exception
  {
    loginPage = new LoginPage( browser.getWebDriver() );
    loginPage.open();

    SolutionsOverviewPage solutionsOverview = loginPage.loginAs( admin );

    SolutionsOverviewUtils solutionsOverviewUtils = new SolutionsOverviewUtils();
    Set<String> setOfNSDs = solutionsOverviewUtils.getNSDsSetFromConfig();

    int size = setOfNSDs.size();
    int item = new Random().nextInt( size );
    int i = 0;
    String randomValueFromSet = "";
    for( Object obj : setOfNSDs )
    {
      if( i == item )
      {
        randomValueFromSet = ( String ) obj;
        break;
      }
      i = i + 1;
    }

    FileSystemView fileSystemView = solutionsOverview.openSpecificNsdByName( randomValueFromSet );
  }

}
