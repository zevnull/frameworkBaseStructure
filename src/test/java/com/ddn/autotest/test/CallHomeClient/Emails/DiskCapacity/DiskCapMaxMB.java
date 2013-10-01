package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxMB extends DiskCapacityBase
{

	public DiskCapMaxMB() {
		super("MB", 1e6, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9914() throws Exception {
		run_test();
	}

}
