package com.gramercysoftware.utils.helpers;

class ObjectUtilsNestedOuter {
	private String string;
	private ObjectUtilsNestedInner ouni;
	
	public void setString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setOuni(ObjectUtilsNestedInner ouni) {
		this.ouni = ouni;
	}

	public ObjectUtilsNestedInner getOuni() {
		return ouni;
	}
}
