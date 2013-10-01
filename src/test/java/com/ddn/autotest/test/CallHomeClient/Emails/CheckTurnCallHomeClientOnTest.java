package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckTurnCallHomeClientOnTest extends CallHomeTestBase
{
	
	@Test ( description = "Check turn CallHome client on", groups = { "CHEmail" })
	public void TC9012() throws Exception
	{
		RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);
		
	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    gen.turnCallHomeTo("OFF", rabbit);

	    String output = gen.turnCallHomeTo("ON", rabbit);
	    System.out.println(output);
	    Assert.assertTrue(output.contains("\"status\": \"SUCCESS\""), "Callhome have not been turned on");
	    
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);

	    output = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

	    Assert.assertTrue(output.contains("\"id\""));
	}
}
