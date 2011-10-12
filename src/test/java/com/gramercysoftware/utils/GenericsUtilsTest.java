package com.gramercysoftware.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GenericsUtilsTest {
	interface Baz<T> {
		void doSomething();
	}
	
	class Bar<T> implements Baz<T> {
		private int foo;

		public int getFoo() {
			return foo;
		}

		public void setFoo(int foo) {
			this.foo = foo;
		}

		public void doSomething() {
		}
	}

	class Foo extends Bar<String> {
		public void doSomething() {
			System.out.println("Did something.");
		}
	}

	@Test
	public void testClassOfT() {
		Class<?> classOfT = GenericsUtils.classOfT(new Foo());
		assertEquals("String", classOfT.getSimpleName());
	}
	
	@Test
	public void testCreateT() {
		Object t = GenericsUtils.createT(new Foo());
		assertEquals("String", t.getClass().getSimpleName());
	}
}
