package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

/**
 * 
 * IMPORTANT: For this test uploader should be disabled
 *
 */
public class CheckCancelTaskTest extends CallHomeTestBase
{
	
	@Test ( description = "Check Cancel task",  groups = { "CHEmail" } )
	public void TC8924() throws Exception
	{
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
	    
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    
		String id = gen.getIdFromEvent(response);
	    List<String> ids = new ArrayList<String>();
	    ids.add(id);
	    
	    JSONObject request = gen.cancelTasks(ids);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, request.toString());
	    String output = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    
	    Assert.assertTrue(output.contains("\"status\": \"SUCCESS\""), "Task not cancelled");
	}
}
