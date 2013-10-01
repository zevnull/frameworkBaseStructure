package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckFailedDeleteTaskTest extends CallHomeTestBase
{
	
	@Test ( description = "Check failed delete task", groups = { "CHEmail" } )
	public void TC8922() throws Exception
	{
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    List<String> ids = new ArrayList<String>();
	    ids.add("123456789");
	    
	    JSONObject request = gen.deleteTasks(ids);
		
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, request.toString());
	    String output = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    
	    Assert.assertTrue(output.contains("\"status\": \"FAIL\""), "Error in responce: fail not found");
	    Assert.assertTrue(output.contains("\"count\": 0"), "Error in responce: 0 not found");
	}
}
