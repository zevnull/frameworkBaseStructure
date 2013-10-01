package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.Date;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.mongodb.DBRecord;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

public class CleaningNegativeTest extends CallHomeTestBase
{
	@SuppressWarnings("deprecation")
	@Test ( description = "Cleaning Negative", groups = { "CHEmail" } )
	public void TC9935() throws Exception
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
	    
	    db.switchCollection("task");
	    
	    Date beginDate = new Date();
	    beginDate.setDate(beginDate.getDate() - 3);
	    
	    DBRecord findBy = new DBRecord("_id", taskId);
	    db.updateDateInDocument(findBy, "begin_time", beginDate);
		
	    ssh.runCommand(Settings.getProperty(SettingsKeys.CHC_ENV_PATH) + "bin/celery call --queue consolidate ddn.directmon.chc.task.tasks.clean_events");
	    
	    Assert.assertTrue(db.isRecordExists(eventRec), "Event has been removed");
	}
}
