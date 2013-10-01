package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.Filters;
import com.ddn.autotest.application.utils.generators.Task;
import com.ddn.autotest.pages.CallHomeTasksPage;
import com.pages.SolutionsOverviewPage;
import com.autotests.data.primitives.UserIDElement.ExistingApplications;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CompareServiceAndWebUITaskListTest extends CallHomeWebUITestBase
{
	
	@Test ( description = "Compare service and WebUI Task List"/*, groups = { "CHEmail" }*/)
	public void TC10220() throws Exception
	{
		RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);
		
	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();

 	    JSONObject mess = gen.sortTasksList(new Filters("direction", 1));
   	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, mess.toString());
   	    String response = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
   	    
   	    List<Task> tasks = gen.getListOfTasksFromResponse(response);
   	    
   	    System.out.println(tasks);
   	    
		SolutionsOverviewPage solutionsOverview = loginPage.loginAs(admin);
	    Assert.assertTrue(solutionsOverview.isLoggedIn(), "ERROR in login");
	    
	    solutionsOverview.getUserIDElement().openApp(ExistingApplications.CALLHOME);
	    
   	    CallHomeTasksPage callHomePage = new CallHomeTasksPage(browser.getWebDriver());
   	    
   	    System.out.println("Ready");
   	    Thread.sleep(10000);
   	    System.out.println("Stady");
   	    Thread.sleep(10000);
   	    callHomePage.setAllPropertiesVisible();
   	    
   	    Assert.assertTrue(callHomePage.isAllTasksFromListExistsInTable(tasks), "Not all tasks found");
	}
}
