package com.gramercysoftware.utils.numberencoder;

import junit.framework.TestCase;

public class HexNumberEncoderTest extends TestCase {
	private INumberEncoder fixture;

	protected void setUp() throws Exception {
		super.setUp();
		fixture = new HexNumberEncoder();
	}
	
	public void testEncode_negative_1() {
		try {
			fixture.encode(-1);
			fail();
		} catch(NumberEncodingException e) {
		}
	}
	
	public void testEncode_0() throws Exception {
		assertEquals("0", fixture.encode(0));
	}
	
	public void testEncode_10()throws Exception  {
		assertEquals("A", fixture.encode(10));
	}

	public void testEncode_1()throws Exception  {
		assertEquals("1", fixture.encode(1));
	}
	
	public void testEncode_15()throws Exception  {
		assertEquals("F", fixture.encode(15));
	}
	
	public void testEncode_16()throws Exception  {
		assertEquals("10", fixture.encode(16));
	}

	public void testEncode_127()throws Exception  {
		assertEquals("7F", fixture.encode(127));
	}
	
	public void testEncode_maxLong()throws Exception  {
		assertEquals("7FFFFFFFFFFFFFFF", fixture.encode(Long.MAX_VALUE));
	}
	
	public void testEncode_128()throws Exception  {
		assertEquals("80", fixture.encode(128));
	}
	
	public void testEncode_4096()throws Exception  {
		assertEquals("1000", fixture.encode(4096));
	}
	
	public void testDecode_A() {
		assertEquals(10, fixture.decode("A"));
	}

	public void testDecode_10() {
		assertEquals(16, fixture.decode("10"));
	}

	public void testDecode_F() {
		assertEquals(15, fixture.decode("F"));
	}
	
	public void testDecode_1000() {
		assertEquals(4096, fixture.decode("1000"));
	}
	
	public void testDecode_7FFFFFFFFFFFFFFF() {
		assertEquals(Long.MAX_VALUE, fixture.decode("7FFFFFFFFFFFFFFF"));
	}
}
