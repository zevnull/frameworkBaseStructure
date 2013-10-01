package com.ddn.autotest.test.CallHomeClient.Emails;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckConfigCollectionCreationTest extends CallHomeTestBase
{
	
	@Test ( description = "Check Config collection creation",  groups = { "CHEmail" })
	public void TC9938() throws Exception
	{
		db.switchCollection("config");
		db.removeCollection();
		
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);

	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    gen.turnCallHomeTo("OFF", rabbit);
	    gen.turnCallHomeTo("ON", rabbit);

	    Assert.assertTrue(db.getCollections().contains("config"), "Config collection has not been created");
	    Assert.assertTrue(db.getDocuments().get(0).contains("chc_enabled"), "chc_enabled document has not been created");
	}
}
