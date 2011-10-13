package com.gramercysoftware.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EnumUtilsTest {
	private EnumUtils fixture;
	
	@Before
	public void setUp() throws Exception {
		fixture = new EnumUtils();
	}

	@SuppressWarnings("static-access")
	@Test
	public void fromValue() {
		assertTrue(EUTFoo.FOO == fixture.fromValue(EUTFoo.class, "FOO"));
		assertFalse(EUTFoo.FOO == fixture.fromValue(EUTFoo.class, "foo"));
	}

	@SuppressWarnings("static-access")
	@Test
	public void fromValueIgnoreCase() {
		assertTrue(EUTFoo.FOO == fixture.fromValueIgnoreCase(EUTFoo.class, "FOO"));
		assertTrue(EUTFoo.FOO == fixture.fromValueIgnoreCase(EUTFoo.class, "foo"));
	}
}
