package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxPB extends DiskCapacityBase
{

	public DiskCapMaxPB() {
		super("PB", 1e15, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9923() throws Exception {
		run_test();
	}

}
