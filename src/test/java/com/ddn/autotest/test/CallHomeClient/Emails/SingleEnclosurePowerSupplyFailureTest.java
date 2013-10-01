package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import org.testng.Assert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFAPowerSupplyEnclosureComponent;
import com.ddn.autotest.utils.StringUtils;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class SingleEnclosurePowerSupplyFailureTest extends CallHomeTestBase
{
	
	@Test ( description = "Single enclosure power supply failure", groups = { "CHEmail" } )
	public void TC10085() throws Exception
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
		
		Event event = new Event(new SFAPowerSupplyEnclosureComponent(enclInd));

		JSONArray events = new JSONArray();
		events.put(event.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		RabbitMQ rabbit = new RabbitMQ();
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

	    Assert.assertTrue(email.waitForEmailWithCountAppeared(1, TIMEOUT * 3), "Wrong count of emails");
	    
	    Message emailMess = email.getMessages().get(0);
	    List<String> body = email.getBody(emailMess);
	    
	    List<String> content = new ArrayList<String>();
	    content.add("Controller Enclosure details:");
	    content.add("SFAOS Version number(s):");
	    content.add("Controller Serial Number(s):");
	    content.add("Index:");
	    content.add("Model Number:");
	    content.add("BMC Configuration:");
	    content.add("BIOS Version:");
	    content.add("Component failure name:");
	    content.add("Component failure time:");
	    content.add("Power Supply failure(s) details:");
	    content.add("Enclosure Serial Number:");
	    content.add("Serial number:");
	    content.add("Position:");

	    Assert.assertTrue(StringUtils.isAllLinesContainsInList(body, content), "Message contains not all required fields");
	}
}
