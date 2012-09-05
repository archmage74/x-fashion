package com.xfashion.shared;

import org.junit.Test;

import junit.framework.Assert;

public class BarcodeHelperTest {

	@Test
	public void barcodeTest1() {
		BarcodeHelper bh = new BarcodeHelper();
		Long input = 101008000037L;
		String actual = bh.generateEan(input);
		String expected = "1010080000370";
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void barcodeTest2() {
		BarcodeHelper bh = new BarcodeHelper();
		Long input = 567814569871L;
		String actual = bh.generateEan(input);
		String expected = "5678145698717";
		Assert.assertEquals(expected, actual);
	}
}
