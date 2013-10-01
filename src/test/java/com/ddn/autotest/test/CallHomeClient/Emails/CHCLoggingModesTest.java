package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

public class CHCLoggingModesTest extends CallHomeTestBase
{
	
	@Test ( description = "CHC Logging modes",  groups = { "CHEmail" } )
	public void TC9937() throws Exception
	{
	    String output = ssh.runCommand("cat " + Settings.getProperty(SettingsKeys.CHC_SETTINGS_LOG_FILE));
	    Assert.assertTrue(output.contains("level=ERROR"), "Default log level is not ERROR");
		
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
	    
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    Assert.assertTrue(response.contains("\"status\": \"SUCCESS\""), "Event message has not been processed");
	    
		String id = gen.getIdFromEvent(response);
	    gen.waitForTaskSuccessed(id);
	    
	    List<String> ids = new ArrayList<String>();
	    ids.add(id);
	    
	    JSONObject request = gen.deleteTasks(ids);
	    rabbit.publishDataMessage(RabbitQueues.SEND_UI, request.toString());
	    output = rabbit.waitForMessage(RabbitQueues.RECEIVE, 3 * TIMEOUT);
	    Assert.assertTrue(output.contains("\"status\": \"SUCCESS\""), "Task not removed");

	    gen.turnCallHomeTo("FAKE", rabbit);
	    
	    output = ssh.runCommand("cat " + Settings.getProperty(SettingsKeys.CHC_LOG_FILE));
	    
	    Assert.assertTrue(output.contains("DEBUG"), "DEBUG is not found");
	    Assert.assertTrue(output.contains("INFO"), "INFO is not found");
	    Assert.assertTrue(output.contains("ERROR"), "ERROR is not found");
	}
}
