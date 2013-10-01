package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeZB extends DiskCapacityRangeBase
{

	public DiskCapRangeZB() {
		super("ZB", 1e21);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9929() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
