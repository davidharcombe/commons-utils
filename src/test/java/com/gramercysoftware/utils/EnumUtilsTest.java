package com.gramercysoftware.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EnumUtilsTest {
	private EnumUtils fixture;
	
	enum Foo {
		FOO, BAR, BAZ;
	}
	
	enum Bar {
		FOO("Foo"), BAR("Bar"), BAZ("Baz");
		
		private final String value;

		private Bar(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	}

	@Before
	public void setUp() throws Exception {
		fixture = new EnumUtils();
	}

	@SuppressWarnings("static-access")
	@Test
	public void fromValue() {
		assertTrue(Foo.FOO == fixture.fromValue(Foo.class, "FOO"));
		assertFalse(Foo.FOO == fixture.fromValue(Foo.class, "foo"));
	}

	@SuppressWarnings("static-access")
	@Test
	public void fromValueIgnoreCase() {
		assertTrue(Foo.FOO == fixture.fromValueIgnoreCase(Foo.class, "FOO"));
		assertTrue(Foo.FOO == fixture.fromValueIgnoreCase(Foo.class, "foo"));
	}
}
