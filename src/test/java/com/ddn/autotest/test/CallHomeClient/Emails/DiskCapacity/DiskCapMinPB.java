package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinPB extends DiskCapacityBase
{

	public DiskCapMinPB() {
		super("PB", 1e15, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9921() throws Exception {
		run_test();
	}

}
