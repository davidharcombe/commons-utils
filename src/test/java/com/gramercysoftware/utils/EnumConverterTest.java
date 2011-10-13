package com.gramercysoftware.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class EnumConverterTest {
	private EnumConverter fixture;

	@Before
	public void before() {
//		Logger.getRootLogger().setLevel(Level.DEBUG);
		fixture = new EnumConverter();
	}
	
	@Test
	public void testConvert_Good() {
		Object convert = fixture.convert(EUTFoo.class, "FOO");
		assertNotNull(convert);
		assertEquals(EUTFoo.class, convert.getClass());
		assertEquals(EUTFoo.FOO, convert);
	}

	@Test
	public void testConvert_BadCase() {
		Object convert = fixture.convert(EUTFoo.class, "foo");
		assertNull(convert);
	}
	
	@Test
	public void testConvert_BadValue() {
		Object convert = fixture.convert(EUTFoo.class, "WAHOONIE");
		assertNull(convert);
	}
	
	@Test
	public void testConvert_NotAnEnum() {
		Object convert = fixture.convert(EnumConverter.class, "WAHOONIE");
		assertNull(convert);
	}
	
	@Test
	public void testConvert_Null() {
		Object convert = fixture.convert(EUTFoo.class, null);
		assertNull(convert);
	}
}
