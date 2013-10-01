package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeMB extends DiskCapacityRangeBase
{

	public DiskCapRangeMB() {
		super("MB", 1e6);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9909() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
