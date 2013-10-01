package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeGB extends DiskCapacityRangeBase
{

	public DiskCapRangeGB() {
		super("GB", 1e9);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9916() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
