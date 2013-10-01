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

public class AggregationVerifyingPositiveTest extends CallHomeTestBase
{
	
	@Test ( description = "Aggregation verifying positive",  groups = { "CHEmail" } )
	public void TC10068() throws Exception
	{
	    CHCEmail email = new CHCEmail();
	    email.loadCHCMessages();
	    email.close();
	    
	    RabbitMQ rabbit = new RabbitMQ();
		CHCRabbitRequestGenarator genr = new CHCRabbitRequestGenarator();
		JSONObject system = genr.generateSystem();
		
		int enclInd1 = 0;
		int enclInd2 = 1;

		JSONObject sysLog = genr.generateSystemLogs();
		JSONObject custInfo = genr.generateCustomerInfo();
		
		JSONArray encl1 = new JSONArray();
		encl1.put(genr.generateEnclosure(enclInd1));
		
		JSONArray encl2 = new JSONArray();
		encl2.put(genr.generateEnclosure(enclInd2));

		JSONArray contr1 = new JSONArray();
		contr1.put(genr.generateController(enclInd1));

		JSONArray contr2 = new JSONArray();
		contr2.put(genr.generateController(enclInd2));

		Event sfaDiskDriveEvent11 = new Event(new SFADisckDriveEventComponent(enclInd1));
		Event sfaDiskDriveEvent12 = new Event(new SFADisckDriveEventComponent(enclInd1));
		Event sfaDiskDriveEvent13 = new Event(new SFADisckDriveEventComponent(enclInd1));
		
		JSONArray events1 = new JSONArray();
		events1.put(sfaDiskDriveEvent11.getObject());
		events1.put(sfaDiskDriveEvent12.getObject());
		events1.put(sfaDiskDriveEvent13.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events1, contr1, encl1);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString());
		
		Event sfaDiskDriveEvent21 = new Event(new SFADisckDriveEventComponent(enclInd2));
		Event sfaDiskDriveEvent22 = new Event(new SFADisckDriveEventComponent(enclInd2));
		Event sfaDiskDriveEvent23 = new Event(new SFADisckDriveEventComponent(enclInd2));
		
		JSONArray events2 = new JSONArray();
		events2.put(sfaDiskDriveEvent21.getObject());
		events2.put(sfaDiskDriveEvent22.getObject());
		events2.put(sfaDiskDriveEvent23.getObject());
		
		mess = genr.generateMessage(system, sysLog, custInfo, events2, contr2, encl2);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString());

	    Assert.assertTrue(email.waitForEmailWithCountAppeared(2, TIMEOUT * 7), "Wrong count of emails");
	}
}
