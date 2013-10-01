package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxKB extends DiskCapacityBase
{

	public DiskCapMaxKB() {
		super("KB", 1e3, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9912() throws Exception {
		run_test();
	}

}
