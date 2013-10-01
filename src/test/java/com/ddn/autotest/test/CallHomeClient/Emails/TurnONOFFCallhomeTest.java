package com.ddn.autotest.test.CallHomeClient.Emails;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

import com.ddn.autotest.mongodb.*;

public class TurnONOFFCallhomeTest extends CallHomeTestBase
{
	
	@Test ( description = "Turn ON/OFF Callhome", groups = { "CHEmail" })
	public void TC9902() throws Exception
	{
		db.switchCollection("config");

		DBRecord rec = new DBRecord("key", "chc_enabled");
		Assert.assertTrue(db.findRecord(rec).contains("\"value\" : true"), "chc_enabled is false");
		
		RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue(RabbitQueues.RECEIVE);
	    rabbit.getAllMessages(RabbitQueues.RECEIVE);
		
	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    
	    gen.turnCallHomeTo("OFF", rabbit);
		Assert.assertTrue(db.findRecord(rec).contains("\"value\" : false"), "chc_enabled is true");
	    
	    gen.turnCallHomeTo("ON", rabbit);
		Assert.assertTrue(db.findRecord(rec).contains("\"value\" : true"), "chc_enabled is false");
	}
}
