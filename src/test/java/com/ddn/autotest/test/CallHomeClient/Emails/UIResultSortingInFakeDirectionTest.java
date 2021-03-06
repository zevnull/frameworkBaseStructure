package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.Filters;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class UIResultSortingInFakeDirectionTest extends CallHomeTestBase
{
	
	@Test ( description = "UI result sorting in fake direction", groups = { "CHEmail" } )
	public void TC9018() throws Exception
	{
		RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);
		
	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    Filters filter = new Filters("component", 0);
	    JSONObject req = gen.sortTasksList(filter);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, req.toString());
	    String output = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    
	    Assert.assertTrue(output.contains("\"status\": \"FAIL\""));
	}
}
