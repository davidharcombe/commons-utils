package com.gramercysoftware.utils.helpers;

class ObjectUtilsNestedInner {
	private String string;

	public void setString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
	
	public boolean isEmpty() throws IllegalArgumentException, IllegalAccessException {
		EmptyObjectChecker<ObjectUtilsNestedInner> objectUtils = new EmptyObjectChecker<ObjectUtilsNestedInner>();
		objectUtils.append("String");
		return objectUtils.isEmpty(this);
	}
}
