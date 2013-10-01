package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxEB extends DiskCapacityBase
{

	public DiskCapMaxEB() {
		super("EB", 1e18, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9926() throws Exception {
		run_test();
	}

}
