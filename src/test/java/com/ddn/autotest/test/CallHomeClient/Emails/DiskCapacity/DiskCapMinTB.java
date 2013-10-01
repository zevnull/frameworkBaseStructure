package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinTB extends DiskCapacityBase
{

	public DiskCapMinTB() {
		super("TB", 1e12, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9918() throws Exception {
		run_test();
	}

}
