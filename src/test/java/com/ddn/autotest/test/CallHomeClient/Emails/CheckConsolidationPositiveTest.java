package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.DMSLogger;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckConsolidationPositiveTest extends CallHomeTestBase
{
	
	@Test ( description = "Check consolidation positive",  groups = { "CHEmail" } )
	public void TC8634() throws Exception
	{
	    DMSLogger.setDebugOn();
	    DMSLogger.testStarted();

	    DMSLogger.info( "Delete all existent email's" );
	    CHCEmail email = new CHCEmail();
	    DMSLogger.info( "Loading new messages" );
	    email.loadCHCMessages();
	    email.close();

	    DMSLogger.info( "Connect to RabbitMQ and create return queue " );
	    RabbitMQ rabbit = new RabbitMQ();
	    rabbit.createQueue( RabbitQueues.RECEIVE );
	    DMSLogger.info( "Read and delete all messages" );
	    rabbit.getAllMessages( RabbitQueues.RECEIVE );

	    DMSLogger.info( "Generating Rabbit message" );
	    CHCRabbitRequestGenarator gen = new CHCRabbitRequestGenarator();
	    JSONObject mess = gen.generateMessage(1, 1, 1);
	    
	    DMSLogger.info( "Sending message to the Rabbit" );
	    rabbit.publishDataMessage( RabbitQueues.SEND_EVENT, mess.toString(), correlationId );

	    DMSLogger.info( "Wait for response from Rabbit" );
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);
	    Assert.assertNotNull( response, "No response from Call Home Client" );
	    
	    Thread.sleep(TIMEOUT * 1000);
	    
	    DMSLogger.info( "Connect to RabbitMQ and create return queue " );
	    RabbitMQ secondRabbit = new RabbitMQ();
	    secondRabbit.createQueue( RabbitQueues.RECEIVE );
	    DMSLogger.info( "Read and delete all messages" );
	    secondRabbit.getAllMessages( RabbitQueues.RECEIVE );

	    DMSLogger.info( "Sending message to the Rabbit" );
	    secondRabbit.publishDataMessage( RabbitQueues.SEND_EVENT, mess.toString(), correlationId );

	    DMSLogger.info( "Wait for email" );
	    Assert.assertTrue( email.waitForEmailWithCountAppeared(1, 5 * TIMEOUT ), "No any email recieved" );
	    
	    DMSLogger.info( "Check  messages count" );
	    Assert.assertTrue( email.getMessagesCount() == 1, "Wrong count of received emails" );
	    
	    DMSLogger.testFinished();
	}
}
