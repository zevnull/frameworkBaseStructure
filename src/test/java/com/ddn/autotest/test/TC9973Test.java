package com.ddn.autotest.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pages.SolutionsOverviewPage;
import com.ddn.autotest.utils.DMLogger;
import com.utils.SolutionsOverviewUtils;

public class TC9973Test extends LoginTestBase
{

  @Test( description = "Check solutions name in the Solutions Overview page", groups = { "DMWeb" } )
  public void TC9973() throws Exception
  {
    DMLogger.methodStartedTest();
    SolutionsOverviewPage solutionsOverview = loginPage.loginAs( admin );
    List<String> solutionsNamesFromUI = solutionsOverview.getSolutionsContainer().getSolutionsName();
    SolutionsOverviewUtils solutionsOverviewUtils = new SolutionsOverviewUtils();
    List<String> solutionsCountFromBackEnd = solutionsOverviewUtils.getSolutionsOverviewNamesFromConfig();

    Assert.assertEquals( solutionsNamesFromUI, solutionsCountFromBackEnd,
      "Solutions count from UI and BackEnd not equals" );

  }
}
