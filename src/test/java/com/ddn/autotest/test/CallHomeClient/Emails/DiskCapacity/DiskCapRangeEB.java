package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeEB extends DiskCapacityRangeBase
{

	public DiskCapRangeEB() {
		super("EB", 1e18);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9925() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
