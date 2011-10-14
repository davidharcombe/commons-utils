package com.gramercysoftware.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EnumUtilsTest {
	@Test
	public void fromValue() {
		assertTrue(EUTFoo.FOO == EnumUtils.fromValue(EUTFoo.class, "FOO"));
		assertFalse(EUTFoo.FOO == EnumUtils.fromValue(EUTFoo.class, "foo"));
	}

	@Test
	public void fromValueIgnoreCase() {
		assertTrue(EUTFoo.FOO == EnumUtils.fromValueIgnoreCase(EUTFoo.class, "FOO"));
		assertTrue(EUTFoo.FOO == EnumUtils.fromValueIgnoreCase(EUTFoo.class, "foo"));
	}
}
