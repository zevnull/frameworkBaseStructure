package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinZB extends DiskCapacityBase
{

	public DiskCapMinZB() {
		super("ZB", 1e21, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9927() throws Exception {
		run_test();
	}

}
