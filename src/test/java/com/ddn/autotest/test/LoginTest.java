package com.ddn.autotest.test;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import com.pages.HomePage;
import com.pages.LoginPage;



public class LoginTest extends BasicTestCase 
{

	private HomePage homePage;

	@Test
	public void testLogin() throws Exception 
	{
		//your can create a private filed
		LoginPage loginPage = new LoginPage(getWebDriver());
		loginPage.open();
		homePage = loginPage.loginAs(admin);
		assertTrue(homePage.isLoggedIn());
	}

}
