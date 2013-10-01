package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class LogBundelsTest extends CallHomeTestBase
{
	
	@Test ( description = "Log Bundels", groups = { "CHEmail" } )
	public void TC8527() throws Exception
	{
	    CHCEmail email = new CHCEmail();
	    email.loadCHCMessages();
	    email.close();

	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
	    
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    Assert.assertTrue(email.waitForAnyEmail( CHECK_MAIL_WAIT_TIMEOUT, 30 ), "No any email recieved");
	    Assert.assertTrue(email.getAttachmentsSize(email.getMessages().get(0)) > 10000, "Attachment size less than 10K");
	    Assert.assertTrue(email.getAttachmentsNames(email.getMessages().get(0)).toString().contains("tar.gz"), "Attachment not in archive");
	}
}
