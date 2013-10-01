package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckSwitchCallHomeClientToFakeStateTest extends CallHomeTestBase
{
	
	@Test ( description = "Check switch CallHome client to Fake state", groups = { "CHEmail" } )
	public void TC9011() throws Exception
	{
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject request = gen.getRequestTurnCallHomeTo("FAKE");
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, request.toString());
	    String output = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    
	    Assert.assertTrue(output.contains("\"status\": \"FAIL\""), "Fake request has been accepted");
	}
}
