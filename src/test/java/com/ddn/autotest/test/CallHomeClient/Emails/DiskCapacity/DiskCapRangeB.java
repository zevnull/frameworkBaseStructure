package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeB extends DiskCapacityRangeBase
{

	public DiskCapRangeB() {
		super("B", 1e0);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9907() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
