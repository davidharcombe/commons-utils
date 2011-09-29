package com.gramercysoftware.utils.numberencoder;

import junit.framework.TestCase;

public class RomanNumeralEncoderTest extends TestCase {
	private RomanNumeralEncoder fixture;

	protected void setUp() throws Exception {
		super.setUp();
		fixture = new RomanNumeralEncoder();
	}
	
	public void testEncode_1() throws Exception {
		assertEquals("I", fixture.encode(1));
	}

	public void testEncode_10() throws Exception {
		assertEquals("X", fixture.encode(10));
	}
	
	public void testEncode_9() throws Exception {
		assertEquals("IX", fixture.encode(9));
	}
	
	public void testEncode_Strict9() throws Exception {
		fixture.setStrictRoman(true);
		assertEquals("VIIII", fixture.encode(9));
	}
	
	public void testEncode_151() throws Exception {
		assertEquals("CLI", fixture.encode(151));
	}
	
	public void testEncode_Strict151() throws Exception {
		fixture.setStrictRoman(true);
		assertEquals("CLI", fixture.encode(151));
	}
	
	public void testEncode_1999() throws Exception {
		assertEquals("MCMXCIX", fixture.encode(1999));
	}

	public void testEncode_Strict1999() throws Exception {
		fixture.setStrictRoman(true);
		assertEquals("MDCCCCLXXXXVIIII", fixture.encode(1999));
	}
}
