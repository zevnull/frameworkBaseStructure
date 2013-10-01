package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.Filters;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class UIResultSortingInTwoDirectionsTest extends CallHomeTestBase
{
	
	@Test ( description = "UI result sorting in two directions", groups = { "CHEmail" } )
	public void TC9021() throws Exception
	{
		RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);
		
	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    Filters filter = new Filters("component", 1);
	    filter.put("id", -1);
	    
	    JSONObject req = gen.sortTasksList(filter);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, req.toString());
	    String output = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    
	    Assert.assertTrue(gen.checkDoubleSorting(output, "component", 1, "id", -1), "Sorting has not enabled");
	}
}
