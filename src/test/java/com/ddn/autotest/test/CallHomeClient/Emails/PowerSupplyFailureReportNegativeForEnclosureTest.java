package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFADisckDriveEventComponent;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFAFanEventComponent;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFAPowerSupplyEnclosureComponent;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class PowerSupplyFailureReportNegativeForEnclosureTest extends CallHomeTestBase
{
	
	@Test ( description = "Power supply failure report negative for Enclosure", groups = { "CHEmail" } )
	public void TC10066() throws Exception
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
		
		Event sfaDiskDriveEvent = new Event(new SFADisckDriveEventComponent(enclInd));
		Event sfaPowerSupplyEnclosureEvent = new Event(new SFAPowerSupplyEnclosureComponent(enclInd));
		Event sfaFanEventEvent = new Event(new SFAFanEventComponent(enclInd));
		
		JSONArray events = new JSONArray();
		events.put(sfaDiskDriveEvent.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

		events = new JSONArray();
		events.put(sfaDiskDriveEvent.getObject());

		mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

		events = new JSONArray();
		events.put(sfaPowerSupplyEnclosureEvent.getObject());
		events.put(sfaFanEventEvent.getObject());

		mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

		events = new JSONArray();
		events.put(sfaFanEventEvent.getObject());

		mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

	    Assert.assertTrue(email.waitForEmailWithCountAppeared(3, TIMEOUT * 7), "Wrong count of emails");
	}
}
