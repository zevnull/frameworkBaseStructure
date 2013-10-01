package com.ddn.autotest.test.CallHomeClient.Emails;

import org.testng.Assert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFAPowerSupplyControllerComponent;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class PowerSupplyFailureReportOnDifferentControllersTest extends CallHomeTestBase
{
	
	@Test ( description = "Power supply failure report on different controllers", groups = { "CHEmail" } )
	public void TC10089() throws Exception
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
		
		Event event = new Event(new SFAPowerSupplyControllerComponent(enclInd));

		JSONArray events = new JSONArray();
		events.put(event.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);

		system = genr.generateSystem();
		enclInd = 1;

		sysLog = genr.generateSystemLogs();
		custInfo = genr.generateCustomerInfo();
		encl = new JSONArray();
		encl.put(genr.generateEnclosure(enclInd));

		contr = new JSONArray();
		contr.put(genr.generateController(enclInd));
		
		event = new Event(new SFAPowerSupplyControllerComponent(enclInd));

		events = new JSONArray();
		events.put(event.getObject());

		mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		
	    Assert.assertTrue(email.waitForEmailWithCountAppeared(2, TIMEOUT * 7), "Wrong count of emails");
	}
}
