package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMaxGB extends DiskCapacityBase
{

	public DiskCapMaxGB() {
		super("GB", 1e9, 111, 9);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9917() throws Exception {
		run_test();
	}

}
