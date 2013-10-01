package com.ddn.autotest.test.CallHomeClient.Emails.DiskCapacity;

import java.util.Random;

public class DiskCapacityRangeBase extends DiskCapacityBase {

	public static int TEST_DISC_CAPACITY_UP = 999;

	public DiskCapacityRangeBase(String testDimesnsion,double testDimesnsionMultiplicator) {
		super(testDimesnsion, testDimesnsionMultiplicator, 0, 0);
		Random rand = new Random();
		int diskSize = rand.nextInt(TEST_DISC_CAPACITY_UP - 1) + 1;
		this.testBlockSize = rand.nextInt(diskSize - 1) + 1;
		this.testRawCapacity = Math.round(diskSize / this.testBlockSize);

		while (this.testBlockSize * this.testRawCapacity > diskSize) {
			--this.testRawCapacity;
		}
	}

}