package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinB extends DiskCapacityBase
{

	public DiskCapMinB() {
		super("B", 1e0, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC10034() throws Exception {
		run_test();
	}

}
