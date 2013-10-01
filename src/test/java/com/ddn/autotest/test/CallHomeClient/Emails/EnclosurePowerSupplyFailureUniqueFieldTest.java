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

public class EnclosurePowerSupplyFailureUniqueFieldTest extends CallHomeTestBase
{
	
	@Test ( description = "Enclosure power supply failure unique field", groups = { "CHEmail" } )
	public void TC10074() throws Exception
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
		
		Event event = new Event(new SFAPowerSupplyEnclosureComponent(enclInd));

		JSONArray events = new JSONArray();
		events.put(event.getObject());

		JSONObject mess = genr.generateMessage(system, sysLog, custInfo, events, contr, encl);
		
		rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
		rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

	    Assert.assertTrue(email.waitForEmailWithCountAppeared(1, TIMEOUT * 3), "Wrong count of emails");
	    
	    Message emailMess = email.getMessages().get(0);
	    List<String> body = email.getBody(emailMess);
		
	    String serialNumberTitle = "Serial number: ";
	    
	    int indexOfLine = StringUtils.getIndexOfLineInList(body, serialNumberTitle);
	    Assert.assertTrue((indexOfLine != -1), "Message doesn't contain Serial number field");
	    
	    String serialNumberLine = body.get(indexOfLine);
	    
	    serialNumberLine = serialNumberLine.substring(serialNumberTitle.length(), serialNumberLine.length());
	    
	    Assert.assertTrue(!serialNumberLine.isEmpty(), "Serial number is empty");
	}
}
