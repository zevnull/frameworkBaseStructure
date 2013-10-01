package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import org.testng.annotations.Test;

public class DiskCapMinKB extends DiskCapacityBase
{

	public DiskCapMinKB() {
		super("KB", 1e3, 1, 1);
	}
	
	@Test ( groups = { "CHDiskCapacity" })
	public void TC9911() throws Exception {
		run_test();
	}

}
