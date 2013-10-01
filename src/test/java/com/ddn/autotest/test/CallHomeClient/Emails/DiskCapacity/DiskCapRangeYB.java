package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeYB extends DiskCapacityRangeBase
{

	public DiskCapRangeYB() {
		super("YB", 1e24);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9931() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
