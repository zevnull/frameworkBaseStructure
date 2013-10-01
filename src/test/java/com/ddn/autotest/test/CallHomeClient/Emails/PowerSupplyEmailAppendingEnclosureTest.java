package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

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
import com.ddn.autotest.utils.StringUtils;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class PowerSupplyEmailAppendingEnclosureTest extends CallHomeTestBase
{
	
	@Test ( description = "Power supply email appending Enclosure", groups = { "CHEmail" } )
	public void TC10072() throws Exception
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
		Event eventDiskFailure = new Event(new SFADisckDriveEventComponent(enclInd));
		Event eventFanFailure = new Event(new SFAFanEventComponent(enclInd));
		
		JSONArray events = new JSONArray();
		events.put(eventPowerSupply.getObject());
		events.put(eventDiskFailure.getObject());
		events.put(eventDiskFailure.getObject());
		events.put(eventFanFailure.getObject());
		events.put(eventFanFailure.getObject());
		events.put(eventFanFailure.getObject());
		
		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		RabbitMQ rabbit = new RabbitMQ();
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
		
	    Assert.assertTrue(email.waitForEmailWithCountAppeared(1, TIMEOUT * 7), "Wrong count of emails");
		
		List<String> content = new ArrayList<String>();
		content.add("Critical_Fan");
		content.add("Critical_Disk_Drive");
		content.add("Critical_Power_Supply");
		
	    Message emailMess = email.getMessages().get(0);
	    List<String> body = email.getBody(emailMess);

	    Assert.assertTrue(StringUtils.isAllLinesContainsInList(body, content), "Message contains not all required fields");
	}
}
