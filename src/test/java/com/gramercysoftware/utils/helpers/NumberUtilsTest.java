package com.gramercysoftware.utils.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class NumberUtilsTest {
	private NumberUtils fixture;

	@Before
	public void setup() {
		fixture = new NumberUtils();
	}
	
	@Test
	public void createBigDecimal() throws Exception {
		assertEquals("1001.5", fixture.createBigDecimal(new String("1,001.5")).toString());
		assertEquals("-1001.5", fixture.createBigDecimal(new String("-1,001.5")).toString());
		assertEquals("10", fixture.createBigDecimal(new String("10")).toString());
		assertEquals("-10", fixture.createBigDecimal(new String("-10")).toString());
		assertEquals("1001.5", fixture.createBigDecimal(new String("1 001,5")).toString());
		assertEquals("1001.5", fixture.createBigDecimal(new String("1001,5")).toString());
		assertEquals("1000000.5", fixture.createBigDecimal(new String("1,000,000.50")).toString());
		assertEquals("1000000", fixture.createBigDecimal(new String("1,000,000")).toString());
		assertEquals("10", fixture.createBigDecimal(new String("10,00")).toString());
		assertEquals("1", fixture.createBigDecimal(new String("1,000000")).toString());
		assertEquals("1000.0001", fixture.createBigDecimal(new String("1000,0001")).toString());

		try {
			fixture.createBigDecimal(new String("1,001,5"));
			fail();
		} catch(Exception e) {
		}

		try {
			fixture.createBigDecimal(new String("1.001,5"));
			fail();
		} catch(Exception e) {
		}
	}
}
