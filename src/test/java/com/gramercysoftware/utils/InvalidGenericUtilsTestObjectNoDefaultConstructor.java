package com.gramercysoftware.utils;


public class InvalidGenericUtilsTestObjectNoDefaultConstructor extends AbstractGenericUtilsTestObject<String> {
	private final String bar;
	
	public InvalidGenericUtilsTestObjectNoDefaultConstructor(String bar) {
		this.bar = bar;
	}
	public String doSomething() {
		return "Did something.";
	}
	
	public String getBar() {
		return bar;
	}
}