package com.ddn.autotest.test;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.pages.SolutionsOverviewPage;
import com.ddn.autotest.utils.DMLogger;
import com.ddn.autotest.utils.browser.BrowserFactory;

public class TC8949Test extends LoginTestBase
{

  @Test( description = "Check that session has expired", groups = { "DMWeb" } )
  public void TC8949() throws Exception
  {
    DMLogger.methodStartedTest();
    SolutionsOverviewPage solutionsOverview = loginPage.loginAs( admin );
    BrowserFactory.getExistentWebDriver().get( "ya.ru" );
    Thread.sleep( 10 * ONE_MIN_INTERVAL_MSEC );
    BrowserFactory.getExistentWebDriver().get( "https://192.168.111.151:8100/dmview/dashboard" );
    assertTrue( loginPage.isLoginPageOpened() );
  }
}
