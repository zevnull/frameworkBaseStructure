package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinYB extends DiskCapacityBase
{

	public DiskCapMinYB() {
		super("YB", 1e24, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9930() throws Exception {
		run_test();
	}

}
