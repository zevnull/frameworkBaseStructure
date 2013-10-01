package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxTB extends DiskCapacityBase
{

	public DiskCapMaxTB() {
		super("TB", 1e12, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9920() throws Exception {
		run_test();
	}

}
