package com.ddn.autotest.test.CallHomeClient.Emails;

import org.testng.annotations.BeforeMethod;

import com.pages.LoginPage;
import com.ddn.autotest.utils.browser.BrowserName;
import com.ddn.autotest.utils.settings.Settings;
import com.ddn.autotest.utils.settings.SettingsKeys;

public class CallHomeWebUITestBase extends CallHomeTestBase
{
	protected CallHomeWebUITestBase()
	{
		Settings.setProperty(SettingsKeys.BROWSER, BrowserName.FIREFOX.toString());
	}
	
	@BeforeMethod
	protected void beforeMethod()
	{
	    loginPage = new LoginPage( browser.getWebDriver() );
	    loginPage.open();
	}
}
