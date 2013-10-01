package com.ddn.autotest.test.CallHomeClient.Emails;

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

public class EnclosureModelNumberTest extends CallHomeTestBase
{
	
	@Test ( description = "Enclosure model number", groups = { "CHEmail" } )
	public void TC10076() throws Exception
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

	    Assert.assertTrue(email.waitForEmailWithCountAppeared(1, TIMEOUT * 2), "Wrong count of emails");
	    
	    Message emailMess = email.getMessages().get(0);
	    List<String> body = email.getBody(emailMess);
		
	    String enclosureSerialNumberTitle = "Enclosure Serial Number: ";
	    
	    int indexOfLine = StringUtils.getIndexOfLineInList(body, enclosureSerialNumberTitle);
	    Assert.assertTrue((indexOfLine != -1), "Message doesn't contain Enclosure Serial Number field");
	    
	    String enclosureSerialNumberLine = body.get(indexOfLine);
	    
	    enclosureSerialNumberLine = enclosureSerialNumberLine.substring(enclosureSerialNumberTitle.length(),
	    																enclosureSerialNumberLine.length());
	    
	    Assert.assertTrue(!enclosureSerialNumberLine.isEmpty(), "Enclosure Serial Number is empty");
	}
}
