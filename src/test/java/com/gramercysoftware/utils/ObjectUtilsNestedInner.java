package com.gramercysoftware.utils;

class ObjectUtilsNestedInner {
	private String string;

	public void setString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
	
	public boolean isEmpty() throws IllegalArgumentException, IllegalAccessException {
		ObjectUtils<ObjectUtilsNestedInner> objectUtils = new ObjectUtils<ObjectUtilsNestedInner>();
		objectUtils.append("String");
		return objectUtils.isEmpty(this);
	}
}
