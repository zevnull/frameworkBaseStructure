package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckTurnCallHomeClientOffTest extends CallHomeTestBase
{
	
	@Test ( description = "Check turn CallHome client off", groups = { "CHEmail" } )
	public void TC9010() throws Exception
	{
		RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);
		
	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    gen.turnCallHomeTo("ON", rabbit);

	    String output = gen.turnCallHomeTo("OFF", rabbit);
	    
	    Assert.assertTrue(output.contains("\"status\": \"SUCCESS\""), "Callhome have not been turned off");
	    
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
	    output = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

	    gen.turnCallHomeTo("ON", rabbit);
	    
	    Assert.assertEquals(mess.toString(), output);
	}
}
