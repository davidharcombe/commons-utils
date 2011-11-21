package com.gramercysoftware.utils.helpers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Calendar;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmptyObjectCheckerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void emptySimpleObject() throws Exception {
		ObjectUtilsSimpleTest simpleObject = new ObjectUtilsSimpleTest();
		EmptyObjectChecker<ObjectUtilsSimpleTest> objectUtils = new EmptyObjectChecker<ObjectUtilsSimpleTest>();
		objectUtils.append("string").append("date");
		assertEquals(2, objectUtils.getFieldsToCheck().size());
		assertTrue(objectUtils.isEmpty(simpleObject));
	}

	@Test
	public void emptySimpleObjectWithoutFieldsSelected() throws Exception {
		ObjectUtilsSimpleTest simpleObject = new ObjectUtilsSimpleTest();
		EmptyObjectChecker<ObjectUtilsSimpleTest> objectUtils = new EmptyObjectChecker<ObjectUtilsSimpleTest>();
		assertTrue(objectUtils.isEmpty(simpleObject));
		assertEquals(0, objectUtils.getNonEmptyFields().size());
	}
	
	@Test
	public void nonEmptySimpleObjectWithoutFieldsSelected() throws Exception {
		ObjectUtilsSimpleTest simpleObject = new ObjectUtilsSimpleTest();
		simpleObject.setString("Monkeys!");
		EmptyObjectChecker<ObjectUtilsSimpleTest> objectUtils = new EmptyObjectChecker<ObjectUtilsSimpleTest>();
		assertFalse(objectUtils.isEmpty(simpleObject));
		assertEquals(1, objectUtils.getNonEmptyFields().size());
	}
	
	@Test
	public void emptySimpleObjectIgnoresPrimitive() throws Exception {
		ObjectUtilsSimpleTest simpleObject = new ObjectUtilsSimpleTest();
		EmptyObjectChecker<ObjectUtilsSimpleTest> objectUtils = new EmptyObjectChecker<ObjectUtilsSimpleTest>();
		objectUtils.append("string").append("date").append("i1");
		assertTrue(objectUtils.isEmpty(simpleObject));
	}
	
	@Test
	public void simpleObjectWithStringNotEmpty() throws Exception {
		ObjectUtilsSimpleTest simpleObject = new ObjectUtilsSimpleTest();
		simpleObject.setString("Monkeys");

		EmptyObjectChecker<ObjectUtilsSimpleTest> objectUtils = new EmptyObjectChecker<ObjectUtilsSimpleTest>();
		objectUtils.append("string").append("date");
		assertFalse(objectUtils.isEmpty(simpleObject));
		assertFalse(CollectionUtils.isEmpty(objectUtils.getNonEmptyFields()));
		assertTrue(1 == objectUtils.getNonEmptyFields().size());
		assertTrue(objectUtils.getNonEmptyFields().contains("string"));
		assertEquals("Non-empty fields: string", objectUtils.getErrorMessage());
	}

	@Test
	public void simpleObjectWithDateNotEmpty() throws Exception {
		ObjectUtilsSimpleTest simpleObject = new ObjectUtilsSimpleTest();
		simpleObject.setDate(Calendar.getInstance().getTime());
		
		EmptyObjectChecker<ObjectUtilsSimpleTest> objectUtils = new EmptyObjectChecker<ObjectUtilsSimpleTest>();
		objectUtils.append("string").append("date");
		assertFalse(objectUtils.isEmpty(simpleObject));
		assertFalse(CollectionUtils.isEmpty(objectUtils.getNonEmptyFields()));
		assertTrue(1 == objectUtils.getNonEmptyFields().size());
		assertTrue(objectUtils.getNonEmptyFields().contains("date"));
		assertEquals("Non-empty fields: date", objectUtils.getErrorMessage());
	}

	@Test
	public void simpleObjectWithBothStringAndDateNotEmpty() throws Exception {
		ObjectUtilsSimpleTest simpleObject = new ObjectUtilsSimpleTest();
		simpleObject.setString("Monkeys");
		simpleObject.setDate(Calendar.getInstance().getTime());
		
		EmptyObjectChecker<ObjectUtilsSimpleTest> objectUtils = new EmptyObjectChecker<ObjectUtilsSimpleTest>();
		objectUtils.append("string").append("date");
		assertFalse(objectUtils.isEmpty(simpleObject));
		assertFalse(CollectionUtils.isEmpty(objectUtils.getNonEmptyFields()));
		assertTrue(2 == objectUtils.getNonEmptyFields().size());
		assertTrue(objectUtils.getNonEmptyFields().contains("date"));
		assertTrue(objectUtils.getNonEmptyFields().contains("string"));
		assertEquals("Non-empty fields: string, date", objectUtils.getErrorMessage());
	}
}
