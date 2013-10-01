package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxZB extends DiskCapacityBase
{

	public DiskCapMaxZB() {
		super("ZB", 1e21, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9928() throws Exception {
		run_test();
	}

}
