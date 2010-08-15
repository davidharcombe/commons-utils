package com.gramercysoftware.utils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class PackageClassListerTest {

	@Test
	public void testDirectory() {
		List<Class<?>> classes = PackageClassLister.getClasses("com.gramercysoftware.utils.pcltest");
		assertNotNull(classes);
		assertEquals(4, classes.size());
	}
	
	@Test
	public void testDirectoryWithPattern() {
		List<Class<?>> classes = PackageClassLister.getClasses("com.gramercysoftware.utils.pcltest", ".*Class");
		assertNotNull(classes);
		assertEquals(3, classes.size());
	}
	
	@Test
	public void testJar() {
		List<Class<?>> classes = PackageClassLister.getClasses("org.apache.commons.lang.enums");
		assertNotNull(classes);
		assertEquals(4, classes.size());
	}

	@Test
	public void testJarWithPattern() {
		List<Class<?>> classes = PackageClassLister.getClasses("org.apache.commons.lang.enums", ".*Enum");
		assertNotNull(classes);
		assertEquals(2, classes.size());
	}
}
