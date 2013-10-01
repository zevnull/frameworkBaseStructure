package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import java.util.List;

import javax.mail.Message;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.ddn.autotest.application.utils.CHCEmail;
import com.ddn.autotest.application.utils.generators.CHCRabbitRequestGenarator;
import com.ddn.autotest.application.utils.generators.messageComponents.event.Event;
import com.ddn.autotest.application.utils.generators.messageComponents.event.SFADisckDriveEventComponent;
import com.ddn.autotest.test.CallHomeClient.Emails.CallHomeTestBase;
import com.ddn.autotest.utils.RegEx;
import com.ddn.autotest.utils.rabbitmq.RabbitMQ;
import com.ddn.autotest.utils.rabbitmq.RabbitQueues;

public class DiskCapacityBase extends CallHomeTestBase {

	public static final int TEST_ACCURACY = 5;
	public static final int WAIT_TIMEOUT = 60;
	public static final String DISK_SIZE_REGEX = "^(\\s*Disk\\s+Capacity:\\s+)(\\d+[\\d\\.]*)(\\s+)(\\w+)(\\s*)$";
	public static final int REGEX_CAPACITY_VALUE_ID = 2;
	public static final int REGEX_CAPACITY_DIMENSION_ID = 4;
	
	private String testDimesnsion = "";
	private double testDimesnsionMultiplicator = 0;

	protected int testBlockSize = 0;
	protected int testRawCapacity = 0;

	public DiskCapacityBase(String testDimesnsion, double testDimesnsionMultiplicator, int testBlockSize, int testRawCapacity) {
		super();
		this.testDimesnsion = testDimesnsion;
		this.testDimesnsionMultiplicator = testDimesnsionMultiplicator;
		this.testBlockSize = testBlockSize;
		this.testRawCapacity = testRawCapacity;
	}

	public void run_test() throws Exception {
	    RegEx regex = new RegEx();
	
	    CHCEmail email = new CHCEmail();
	    email.loadNewMessages();
	    email.close();
	
	    CHCRabbitRequestGenarator genr = new CHCRabbitRequestGenarator();
	
	    JSONObject system = genr.generateSystem();
	    int enclInd = 0;
	
	    JSONObject sysLog = genr.generateSystemLogs();
	
	    JSONObject custInfo = genr.generateCustomerInfo();
	
	    JSONArray encl = new JSONArray();
	    encl.put( genr.generateEnclosure( enclInd ) );
	
	    JSONArray contr = new JSONArray();
	    contr.put( genr.generateController( enclInd ) );
	
	    Event event = new Event( new SFADisckDriveEventComponent( enclInd ) );
	    SFADisckDriveEventComponent comp = ( SFADisckDriveEventComponent ) event.getEventComp();
	    comp.setBlockSize( testBlockSize );
	    comp.setRawCapacity( testRawCapacity * testDimesnsionMultiplicator );
	
	    JSONArray events = new JSONArray();
	    events.put( event.getObject() );
	
	    JSONObject mess = genr.generateMessage( system, sysLog, custInfo, events, contr, encl );
	
	    RabbitMQ rabbit = new RabbitMQ();
	
	    rabbit.publishDataMessage( RabbitQueues.SEND_EVENT, mess.toString() );
	
	    // wait for message
	    email.waitForCHCEmail( CHECK_MAIL_WAIT_TIMEOUT, 30 );
	    Message emailMess = email.getMessages().get( 0 );
	    List<String> body = email.getBody( emailMess );
	
	    List<List<String>> sizeLine = regex.applyRegEx( DISK_SIZE_REGEX, body );
	
	    Assert.assertTrue( sizeLine.size() == 1, "Disk Capacity not founded" );
	
	    // get values
	    String dimension = sizeLine.get( 0 ).get( REGEX_CAPACITY_DIMENSION_ID );
	    String diskSiseStr = sizeLine.get( 0 ).get( REGEX_CAPACITY_VALUE_ID );
	
	    // check dimension
	    Assert.assertTrue( dimension.equals( testDimesnsion ), "Dimension is '" + dimension + "' instead of '"
	      + testDimesnsion + "'" );
	
	    // check disc capacity
	    double lowerBorder =
	    		testBlockSize * testRawCapacity * testDimesnsionMultiplicator * ( 100 - TEST_ACCURACY ) / 100;
	    double upperBorder =
	    		testBlockSize * testRawCapacity * testDimesnsionMultiplicator * ( 100 + TEST_ACCURACY ) / 100;
	
	    double discCapacity = Double.valueOf( diskSiseStr ) * testDimesnsionMultiplicator;
	
	    Assert.assertTrue( discCapacity >= lowerBorder, "Disk capacity '" + discCapacity + "' less than minimal '"
	      + lowerBorder + "'" );
	
	    Assert.assertTrue( discCapacity <= upperBorder, "Disk capacity '" + discCapacity + "' larger than maximal '"
	      + upperBorder + "'" );
	  }

}