package com.ddn.autotest.test;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.pages.SolutionsOverviewPage;
import com.autotests.data.primitives.NotificationCounter.NotificationType;
import com.ddn.autotest.utils.DMLogger;

public class TC9091Test extends LoginTestBase
{

  @Test( description = "Check notification counter sum for landing page", groups = { "DMWeb" } )
  public void TC9091() throws Exception
  {
    DMLogger.methodStartedTest();
    SolutionsOverviewPage solutionsOverview = loginPage.loginAs( admin );
    final int critical = solutionsOverview.getNotificationCount( NotificationType.CRITICAL );
    final int warning = solutionsOverview.getNotificationCount( NotificationType.WARNING );
    final int info = solutionsOverview.getNotificationCount( NotificationType.INFO );
    final int all = solutionsOverview.getNotificationCount( NotificationType.ALL );
    assertTrue( critical + warning + info == all, "Notification counter sum not correct" );
  }
}
