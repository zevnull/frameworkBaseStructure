package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxB extends DiskCapacityBase
{

	public DiskCapMaxB() {
		super("B", 1e0, 111, 9);
	}
	
	@Test ( groups = "CHDiskCapacity" )
	public void TC9910() throws Exception {
		run_test();
	}

}
