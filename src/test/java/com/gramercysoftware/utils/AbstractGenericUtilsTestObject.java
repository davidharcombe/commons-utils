package com.gramercysoftware.utils;


public abstract class AbstractGenericUtilsTestObject<T> {
	private int foo;

	public int getFoo() {
		return foo;
	}

	public void setFoo(int foo) {
		this.foo = foo;
	}

	public abstract T doSomething();
}