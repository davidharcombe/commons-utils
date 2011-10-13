package com.gramercysoftware.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GenericsUtilsTest {
	@Test
	public void testClassOfT_ByObject_Valid() throws GenericsUtilsException {
		Class<?> classOfT = GenericsUtils.classOfT(new ValidGenericUtilsTestObject());
		assertEquals("String", classOfT.getSimpleName());
	}
	
	@Test
	public void testClassOfT_ByClass_Valid() throws GenericsUtilsException {
		Class<?> classOfT = GenericsUtils.classOfT(ValidGenericUtilsTestObject.class);
		assertEquals("String", classOfT.getSimpleName());
	}
	
	@Test
	public void testCreateT_ByObject_Valid() throws GenericsUtilsException {
		Object t = GenericsUtils.createT(new ValidGenericUtilsTestObject());
		assertEquals("String", t.getClass().getSimpleName());
	}

	@Test
	public void testCreateT_ByClass_Valid() throws Exception {
		Object t = GenericsUtils.createT(ValidGenericUtilsTestObject.class);
		assertEquals("String", t.getClass().getSimpleName());
	}

	@Test(expected=GenericsUtilsException.class)
	public void testClassOfT_ByObject_Invalid() throws GenericsUtilsException {
		GenericsUtils.classOfT(new InvalidGenericUtilsTestObject());
	}
	
	@Test(expected=GenericsUtilsException.class)
	public void testClassOfT_ByClass_Invalid() throws GenericsUtilsException {
		GenericsUtils.classOfT(InvalidGenericUtilsTestObject.class);
	}
	
	@Test(expected=GenericsUtilsException.class)
	public void testCreateT_ByObject_Invalid() throws GenericsUtilsException {
		GenericsUtils.createT(new InvalidGenericUtilsTestObject());
	}

	@Test(expected=GenericsUtilsException.class)
	public void testCreateT_ByClass_Invalid() throws GenericsUtilsException {
		GenericsUtils.createT(InvalidGenericUtilsTestObject.class);
	}
	
	@Test(expected=NullPointerException.class)
	public void testClassOfT_null() throws GenericsUtilsException {
		GenericsUtils.classOfT(null);
	}

	@Test(expected=GenericsUtilsException.class)
	public void testClassOfT_NoDefaultConstructor() throws GenericsUtilsException {
		GenericsUtils.classOfT(InvalidGenericUtilsTestObjectNoDefaultConstructor.class);
	}
	
	@Test(expected=GenericsUtilsException.class)
	public void testClassOfT_PrivateDefaultConstructor() throws GenericsUtilsException {
		GenericsUtils.classOfT(InvalidGenericUtilsTestObjectPrivateDefaultConstructor.class);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCreateT_null() throws GenericsUtilsException {
		GenericsUtils.createT(null);
	}
}
