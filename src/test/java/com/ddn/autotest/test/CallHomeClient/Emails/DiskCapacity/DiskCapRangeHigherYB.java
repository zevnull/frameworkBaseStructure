package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import java.util.Random;

import org.testng.annotations.Test;

public class DiskCapRangeHigherYB extends DiskCapacityRangeBase
{

	public DiskCapRangeHigherYB() {
		super("YB", 1e24);
		Random rand = new Random();
		int diskSize = rand.nextInt(TEST_DISC_CAPACITY_UP - 1) * 1031;
		this.testBlockSize = rand.nextInt(diskSize - 1) + 1;
		this.testRawCapacity = Math.round(diskSize / this.testBlockSize);

		while (this.testBlockSize * this.testRawCapacity > diskSize) {
			--this.testRawCapacity;
		}
	}

	@Test ( groups = { "CHDiskCapacity" })
	public void TC9933() throws Exception {
		run_test();
	}
}
