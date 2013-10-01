package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeTB extends DiskCapacityRangeBase
{

	public DiskCapRangeTB() {
		super("TB", 1e12);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9919() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
