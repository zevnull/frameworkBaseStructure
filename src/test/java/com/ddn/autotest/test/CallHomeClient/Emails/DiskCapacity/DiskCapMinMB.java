package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinMB extends DiskCapacityBase
{

	public DiskCapMinMB() {
		super("MB", 1e6, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9913() throws Exception {
		run_test();
	}

}
