package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangePB extends DiskCapacityRangeBase
{

	public DiskCapRangePB() {
		super("PB", 1e15);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9922() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
