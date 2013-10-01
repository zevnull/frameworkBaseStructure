package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.StringUtils;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckThatDBRecordCorrespondsToTheEventTest extends CallHomeTestBase
{
	@Test ( description = "Check that DB record corresponds to the Event", groups = { "CHEmail" } )
	public void TC8531() throws Exception
	{
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue( RabbitQueues.RECEIVE );
	    rabbit.getAllMessages( RabbitQueues.RECEIVE );

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    String OID = gen.getEventOID(mess);
	    rabbit.publishDataMessage(RabbitQueues.SEND_EVENT, mess.toString(), correlationId);

	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
	    Assert.assertNotNull(response, "No response from Call Home Client");
	    Assert.assertTrue(StringUtils.isLineContainsInList(db.getDocuments(), OID) , "No new events received in MongoDB");
	}
}
