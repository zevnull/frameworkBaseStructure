package com.ddn.autotest.test.CallHomeClient.Emails;

import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;

import javax.mail.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFAFanEventComponent;
import com.ddn.autotest.utils.StringUtils;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class SingleEventWithFanFailureMessageTest extends CallHomeTestBase
{

	@Test ( description = "Single event with Fan failure message", groups = { "CHEmail" } )
	public void TC10086() throws Exception
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

		Event event = new Event(new SFAFanEventComponent(enclInd));

		JSONArray events = new JSONArray();
		events.put(event.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		RabbitMQ rabbit = new RabbitMQ();
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
		
	    email.waitForCHCEmail(CHECK_MAIL_WAIT_TIMEOUT, 30);
	    
	    Assert.assertEquals(email.getMessagesCount(), 1, "Wrong count of emails");
	    
	    Message emailMess = email.getMessages().get(0);
	    List<String> body = email.getBody(emailMess);
	    
	    List<String> content = new ArrayList<String>();
	    content.add("Controller Serial Number(s):");
	    content.add("SFAOS Version number(s):");
	    content.add("Controller Enclosure details:");
	    content.add("Index:");
	    content.add("Model Number:");
	    content.add("BMC Configuration:");
	    content.add("BIOS Version:");
	    content.add("Component failure name: Fan");
	    content.add("Component failure time:");
	    content.add("Fan failure(s) details:");
	    content.add("Enclosure Serial Number:");
	    content.add("Position:");

	    Assert.assertTrue(StringUtils.isAllLinesContainsInList(body, content), "Message contains not all required fields");
	}
}
