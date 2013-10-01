package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapRangeKB extends DiskCapacityRangeBase
{

	public DiskCapRangeKB() {
		super("KB", 1e3);
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9908() throws Exception {
		// initializing of disk capacity
		run_test();
	}
}
