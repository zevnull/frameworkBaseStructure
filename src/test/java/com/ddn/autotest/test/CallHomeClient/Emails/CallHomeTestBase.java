package com.ddn.autotest.test.CallHomeClient.Emails;

import org.testng.annotations.BeforeMethod;

import com.ddn.autotest.test.BasicTestCase;
import com.ddn.autotest.utils.DMLogger;
import com.utils.SSH;
import com.ddn.autotest.utils.browser.BrowserName;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

public class CallHomeTestBase extends BasicTestCase
{
	protected SSH ssh;
	protected final int TIMEOUT = 60;
	protected final int INTERVAL = 1;
	protected final int ONE_MINUTE = 60000;
	protected final int CHECK_MAIL_WAIT_TIMEOUT = 10;
	protected String correlationId = "da39a3ee5e6b4b0d3255bfef95601890afd80709";
	
	protected CallhomeDB db;
	
	protected CallHomeTestBase()
	{
		Settings.setProperty(SettingsKeys.BROWSER, BrowserName.NONE.toString());
	}

	
	@BeforeMethod (alwaysRun = true, groups = {"CHTestBase"})
	protected void beforeMethod()
	{
		ssh = new SSH();

	    ssh.setHost(Settings.getProperty(SettingsKeys.NODE_IP));
	    ssh.setCredentials(Settings.getProperty(SettingsKeys.NODE_SSH_USER),
	    					Settings.getProperty(SettingsKeys.NODE_SSH_PASSWORD));
	    /*
		try
		{
			db = new CallhomeDB("callhome", "event", Settings.getProperty(SettingsKeys.NODE_IP), Settings.getProperty(SettingsKeys.MONGODB_PORT));
			
		    db.switchCollection("subtask");
		    db.clearCollection();
		    
		    db.switchCollection("task");
		    db.clearCollection();
		    
		    db.switchCollection("event");
		    db.clearCollection();
		}
		catch(Exception e)
		{
			DMLogger.errorInMethod(e);
		}*/
	}
}
