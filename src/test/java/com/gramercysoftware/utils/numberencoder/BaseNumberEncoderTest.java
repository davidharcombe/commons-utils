package com.gramercysoftware.utils.numberencoder;

import junit.framework.TestCase;

public class BaseNumberEncoderTest extends TestCase {
	private INumberEncoder fixture;

	protected void setUp() throws Exception {
		super.setUp();
		fixture = new BaseNumberEncoder() {
		};
	}

	public void testEncode_1() throws Exception {
		assertEquals("1", fixture.encode(1));
	}

	public void testEncode_10() throws Exception {
		assertEquals("10", fixture.encode(10));
	}

	public void testEncode_5101() throws Exception {
		assertEquals("5101", fixture.encode(5101));
	}

	public void testDecode_1() throws Exception {
		assertEquals(1, fixture.decode("1"));
	}
	
	public void testDecode_10() throws Exception {
		assertEquals(10, fixture.decode("10"));
	}
	
	public void testDecode_5101() throws Exception {
		assertEquals(5101, fixture.decode("5101"));
	}

	public void testEncode_maxLong() throws Exception {
		assertEquals(Long.toString(Long.MAX_VALUE), fixture.encode(Long.MAX_VALUE));
	}

	public void testDencode_maxLong() throws Exception {
		assertEquals(Long.MAX_VALUE, fixture.decode(Long.toString(Long.MAX_VALUE)));
	}
}
