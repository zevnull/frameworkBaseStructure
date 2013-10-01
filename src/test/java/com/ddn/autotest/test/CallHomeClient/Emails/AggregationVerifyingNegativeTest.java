package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFADisckDriveEventComponent;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class AggregationVerifyingNegativeTest  extends CallHomeTestBase
{
	
	@Test ( description = "Aggregation verifying negative",  groups = { "CHEmail" } )
	public void TC10069() throws Exception
	{
	    CHCEmail email = new CHCEmail();
	    email.loadCHCMessages();
	    email.close();
	    
	    RabbitMQ rabbit = new RabbitMQ();
		CHCRabbitRequestGenarator genr = new CHCRabbitRequestGenarator();
		JSONObject system = genr.generateSystem();
		
		int enclInd = 0;

		JSONObject sysLog = genr.generateSystemLogs();
		JSONObject custInfo = genr.generateCustomerInfo();
		
		JSONArray encl = new JSONArray();
		encl.put(genr.generateEnclosure(enclInd));
		
		JSONArray contr = new JSONArray();
		contr.put(genr.generateController(enclInd));

		Event sfaDiskDriveEvent1 = new Event(new SFADisckDriveEventComponent(enclInd));
		Event sfaDiskDriveEvent2 = new Event(new SFADisckDriveEventComponent(enclInd));
		Event sfaDiskDriveEvent3 = new Event(new SFADisckDriveEventComponent(enclInd));
		
		JSONArray events = new JSONArray();
		events.put(sfaDiskDriveEvent1.getObject());
		events.put(sfaDiskDriveEvent2.getObject());
		events.put(sfaDiskDriveEvent3.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString());
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString());

	    Assert.assertTrue(email.waitForEmailWithCountAppeared(1, TIMEOUT * 7), "Wrong count of emails");
	}
}
