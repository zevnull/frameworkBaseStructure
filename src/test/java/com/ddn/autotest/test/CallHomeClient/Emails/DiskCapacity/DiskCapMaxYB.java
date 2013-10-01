package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxYB extends DiskCapacityBase
{

	public DiskCapMaxYB() {
		super("YB", 1e24, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9932() throws Exception {
		run_test();
	}

}
