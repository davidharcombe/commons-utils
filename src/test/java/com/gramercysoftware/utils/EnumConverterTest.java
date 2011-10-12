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
		Object convert = fixture.convert(Foo.class, "FOO");
		assertNotNull(convert);
		assertEquals(Foo.class, convert.getClass());
		assertEquals(Foo.FOO, convert);
	}

	@Test
	public void testConvert_BadCase() {
		Object convert = fixture.convert(Foo.class, "foo");
		assertNull(convert);
	}
	
	@Test
	public void testConvert_BadValue() {
		Object convert = fixture.convert(Foo.class, "WAHOONIE");
		assertNull(convert);
	}
	
	@Test
	public void testConvert_NotAnEnum() {
		Object convert = fixture.convert(EnumConverter.class, "WAHOONIE");
		assertNull(convert);
	}
	
	@Test
	public void testConvert_Null() {
		Object convert = fixture.convert(Foo.class, null);
		assertNull(convert);
	}
}
