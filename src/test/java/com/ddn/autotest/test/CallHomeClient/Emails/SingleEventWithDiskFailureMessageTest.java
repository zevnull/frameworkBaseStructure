package com.ddn.autotest.test.CallHomeClient.Emails;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;

import javax.mail.Message;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFADisckDriveEventComponent;
import com.ddn.autotest.utils.StringUtils;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class SingleEventWithDiskFailureMessageTest extends CallHomeTestBase
{

	@Test ( description = "Single event with Disk failure message", groups = { "CHEmail" } )
	public void TC10087() throws Exception
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

		Event event = new Event(new SFADisckDriveEventComponent(enclInd));

		JSONArray events = new JSONArray();
		events.put(event.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		RabbitMQ rabbit = new RabbitMQ();
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		
	    email.waitForCHCEmail(CHECK_MAIL_WAIT_TIMEOUT, 30);
	    
	    Assert.assertEquals(email.getMessagesCount(), 1, "Wrong count of emails");
	    
	    Message emailMess = email.getMessages().get(0);
	    List<String> body = email.getBody(emailMess);
	    
	    List<String> content = new ArrayList<String>();
	    content.add("Critical_Disk_Drive");
	    content.add("Controller Serial Number(s):");
	    content.add("SFAOS Version number(s):");
	    content.add("Disk Enclosure details:");
	    content.add("Index:");
	    content.add("Model Number:");
	    content.add("Firmware Version:");
	    content.add("Component failure name: Physical Disk");
	    content.add("Component failure time:");
	    content.add("Disk failure(s) details:");
	    content.add("Type:");
	    content.add("Disk Capacity:");
	    content.add("Component failure make:");
	    content.add("Component failure model:");
	    content.add("Serial Number:");
	    content.add("Encl-Slot:");
	    content.add("DEM2");

	    Assert.assertTrue(StringUtils.isAllLinesContainsInList(body, content), "Message contains not all required fields");
	}
}
