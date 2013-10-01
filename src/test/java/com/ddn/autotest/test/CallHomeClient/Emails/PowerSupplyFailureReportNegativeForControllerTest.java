package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFAFanEventComponent;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFAPowerSupplyEnclosureComponent;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class PowerSupplyFailureReportNegativeForControllerTest extends CallHomeTestBase
{
	
	@Test ( description = "Power supply failure report negative for Controller", groups = { "CHEmail" } )
	public void TC10083() throws Exception
	{
	    CHCEmail email = new CHCEmail();
	    email.loadCHCMessages();
	    email.close();

		CHCRabbitRequestGenarator genr = new CHCRabbitRequestGenarator();
		JSONObject system = genr.generateSystem();
		int enclInd = 0;

		JSONObject sysLog = genr.generateSystemLogs();
		JSONObject custInfo = genr.generateCustomerInfo();
		JSONArray encl = new JSONArray();
		encl.put(genr.generateEnclosure(enclInd));

		JSONArray contr = new JSONArray();
		contr.put(genr.generateController(enclInd));
		
		Event eventPowerSupply = new Event(new SFAPowerSupplyEnclosureComponent(enclInd));
		Event eventFan = new Event(new SFAFanEventComponent(enclInd));
		
		JSONArray events = new JSONArray();
		events.put(eventPowerSupply.getObject());
		
		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		RabbitMQ rabbit = new RabbitMQ();
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		Thread.sleep(4 * ONE_MINUTE);

		events = new JSONArray();
		events.put(eventFan.getObject());
		mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		Thread.sleep(4 * ONE_MINUTE);

		events = new JSONArray();
		eventFan = new Event(new SFAFanEventComponent(enclInd));
		events.put(eventFan.getObject());
		mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		Thread.sleep(4 * ONE_MINUTE);
		
	    Assert.assertTrue(email.waitForEmailWithCountAppeared(3, TIMEOUT * 7), "Wrong count of emails");
	}
}
