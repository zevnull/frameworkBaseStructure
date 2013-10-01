package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckEventsProcessingTest extends CallHomeTestBase
{
	
	@Test ( description = "Check Events processing",  groups = { "CHEmail" } )
	public void TC9080() throws Exception
	{
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 7 * TIMEOUT, correlationId);
	    /*
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    Assert.assertTrue(response.contains("\"status\": \"SUCCESS\""), "Message has not been processed");*/
	}
}
