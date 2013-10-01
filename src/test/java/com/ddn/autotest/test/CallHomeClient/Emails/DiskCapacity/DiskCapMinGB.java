package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinGB extends DiskCapacityBase
{

	public DiskCapMinGB() {
		super("GB", 1e9, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9915() throws Exception {
		run_test();
	}

}
