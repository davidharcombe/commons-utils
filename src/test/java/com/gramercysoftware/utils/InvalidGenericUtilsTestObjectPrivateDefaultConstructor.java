package com.gramercysoftware.utils;

public class InvalidGenericUtilsTestObjectPrivateDefaultConstructor extends AbstractGenericUtilsTestObject<String> {
	private final String bar;

	private InvalidGenericUtilsTestObjectPrivateDefaultConstructor() {
		bar = "BAR";
	}

	public InvalidGenericUtilsTestObjectPrivateDefaultConstructor(String bar) {
		this.bar = bar;
	}

	public String doSomething() {
		return "Did something.";
	}

	public String getBar() {
		return bar;
	}
}