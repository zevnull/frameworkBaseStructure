package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.mongodb.DBRecord;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CHCUITaskDetailRequestTest extends CallHomeTestBase
{
	
	@Test ( description = "CHC UI task detail request",  groups = { "CHEmail" } )
	public void TC9874() throws Exception
	{
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
	    
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    Assert.assertTrue(response.contains("\"status\": \"SUCCESS\""), "Message has not been processed");
	    
		String id = gen.getIdFromEvent(response);
	    gen.waitForTaskSuccessed(id);
	    DBRecord eventRec = new DBRecord("_id", id);
	    
	    String event = db.findRecord(eventRec);
	    String taskId = db.getTaskIdFromEvent(event);

	    mess = gen.getTaskInfoQuery(taskId);
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, mess.toString());
	    response = null;
	    response = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    Assert.assertTrue(response.contains("events"), "Event is not found");
	    Assert.assertTrue(response.contains("task"), "Task is not found");
	    Assert.assertTrue(response.contains("subtasks"), "Subtask is not found");
	    Assert.assertTrue(response.contains("enclosure"), "Enclosure is not found");
	    Assert.assertTrue(response.contains("controller"), "Controller is not found");
	}
}
