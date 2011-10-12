package com.gramercysoftware.utils;

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