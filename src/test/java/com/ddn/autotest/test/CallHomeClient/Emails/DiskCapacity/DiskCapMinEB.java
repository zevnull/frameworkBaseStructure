package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinEB extends DiskCapacityBase
{

	public DiskCapMinEB() {
		super("EB", 1e18, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9924() throws Exception {
		run_test();
	}

}
