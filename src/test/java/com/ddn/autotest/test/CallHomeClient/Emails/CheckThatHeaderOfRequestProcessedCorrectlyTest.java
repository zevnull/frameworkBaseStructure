package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;
import com.rabbitmq.client.GetResponse;

public class CheckThatHeaderOfRequestProcessedCorrectlyTest extends CallHomeTestBase
{
	@Test ( description = "Check that header of request processed correctly", groups = { "CHEmail" } )
	public void TC9889() throws Exception
	{
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    String correlationId = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
	    
	    JSONObject request = gen.getRequestTurnCallHomeTo("ON");
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, request.toString(), 
	    							"x.ddn.directmon.adapter.return", correlationId);
	    
	    GetResponse response = rabbit.waitForResponce(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    Assert.assertTrue(response.getProps().getCorrelationId().equals(correlationId), "Wrong correlation id");
	}
}
