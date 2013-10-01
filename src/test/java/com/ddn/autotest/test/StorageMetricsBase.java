package com.ddn.autotest.test;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import com.pages.LoginPage;
import com.pages.SolutionsOverviewPage;
import com.autotests.data.primitives.FileSystemView;
import com.autotests.data.primitives.SolutionsContainer;
import com.ddn.autotest.utils.DMLogger;
import com.utils.SolutionsOverviewUtils;
import com.ddn.autotest.utils.browser.BrowserName;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

/**
 * Basic class for Storage metrics tests
 * 
 * User: Andrei_Tsiarenia
 */
public class StorageMetricsBase extends BasicTestCase
{
  public StorageMetricsBase()
  {
    Settings.setProperty( SettingsKeys.BROWSER, BrowserName.FIREFOX.toString() );
  }

  @BeforeMethod ( alwaysRun = true, groups = { "StorageMetricsBase" } )
  protected void beforeMethod() throws Exception
  {
    loginPage = new LoginPage( browser.getWebDriver() );
    loginPage.open();

    SolutionsOverviewPage solutionsOverview = loginPage.loginAs( admin );

    SolutionsOverviewUtils solutionsOverviewUtils = new SolutionsOverviewUtils();
    Map<SolutionsContainer.SOLUTION_TYPE, List<String>> allSolutions =
      solutionsOverviewUtils.getSolutionsMapFromConfig();

    List<String> storageSolutions = allSolutions.get( SolutionsContainer.SOLUTION_TYPE.SFA );

    Random r = new Random();
    int num = 0;
    if( storageSolutions.size() > 1 )
    {
      num = r.nextInt( storageSolutions.size() - 1 );
    }

    FileSystemView fileSystemView = solutionsOverview.openSpecificSolutionByName( storageSolutions.get( num ) );
  }
}
