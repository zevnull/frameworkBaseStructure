package com.ddn.autotest.test.CallHomeClient.Emails;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.utils.DMSLogger;
import com.ddn.autotest.utils.StringUtils;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class CheckDuplicateEventsFilterTest extends CallHomeTestBase
{
	
	@Test ( description = "Check duplicate events filter",  groups = { "CHEmail" } )
	public void TC8619() throws Exception
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
	    String OID = gen.getEventOID(mess);
	    
	    DMSLogger.info( "Sending message to the Rabbit" );
	    rabbit.publishDataMessage( RabbitQueues.SEND_EVENT, mess.toString(), correlationId );

	    DMSLogger.info( "Wait for response from Rabbit" );
	    String response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

	    Assert.assertTrue(StringUtils.isLineContainsInList(db.getDocuments(), OID) , "No new events received in MongoDB");
	    
	    Assert.assertNotNull( response, "No response from Call Home Client" );
	    DMSLogger.info( "Wait for email" );
	    Assert.assertTrue( email.waitForAnyEmail( CHECK_MAIL_WAIT_TIMEOUT, 30 ), "No any email recieved" );
	    DMSLogger.info( "Check  attachment size size should be more than 10K" );
	    Assert.assertTrue( email.getAttachmentsSize( email.getMessages().get( 0 ) ) > 10000, "Attachment size less than 10K" );

	    DMSLogger.info( "Delete all existent email's" );
	    DMSLogger.info( "Loading new messages" );
	    email.loadCHCMessages();
	    email.close();

	    DMSLogger.info( "Connect to RabbitMQ and create return queue " );
	    rabbit.createQueue( RabbitQueues.RECEIVE );
	    DMSLogger.info( "Read and delete all messages" );
	    rabbit.getAllMessages( RabbitQueues.RECEIVE );

	    OID = gen.getEventOID(mess);
	    
	    DMSLogger.info( "Sending message to the Rabbit" );
	    rabbit.publishDataMessage( RabbitQueues.SEND_EVENT, mess.toString(), correlationId );

	    DMSLogger.info( "Wait for response from Rabbit" );
	    response = rabbit.waitForMessageWithCorrelationId(RabbitQueues.RECEIVE, true, 4 * TIMEOUT, correlationId);

	    Assert.assertTrue(StringUtils.isLineContainsInList(db.getDocuments(), OID) , "No new events received in MongoDB");
	    
	    Assert.assertNotNull( response, "No response from Call Home Client" );
	    DMSLogger.info( "Wait for email" );
	    Assert.assertTrue( !email.waitForAnyEmail( CHECK_MAIL_WAIT_TIMEOUT*6, 30/6 ), "No any email recieved" );

	    DMSLogger.testFinished();
	}
}
