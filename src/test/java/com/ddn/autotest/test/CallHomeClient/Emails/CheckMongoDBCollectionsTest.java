package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckMongoDBCollectionsTest extends CallHomeTestBase
{
	
	@Test ( description = "Check MongoDB collections", groups = { "CHEmail" })
	public void TC9901() throws Exception
	{
		db.switchCollection("event");
		db.removeCollection();
		
		db.switchCollection("subtask");
		db.removeCollection();
		
		db.switchCollection("task");
		db.removeCollection();
		
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
	    
	    Assert.assertNotNull(response, "No response from Call Home Client");

	    List<String> collections = new ArrayList<String>();
	    collections.add("event");
	    collections.add("subtask");
	    collections.add("task");
	    
	    Assert.assertTrue(db.getCollections().containsAll(collections), "Not all required collections have been created");
	}
}
