package com.gramercysoftware.utils;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.lang.enums.Enum;

import com.gramercysoftware.utils.pcltest.Blue;
import com.gramercysoftware.utils.pcltest.ColourClass;
import com.gramercysoftware.utils.pcltest.OneClass;
import com.gramercysoftware.utils.pcltest.RedClass;
import com.gramercysoftware.utils.pcltest.TwoClass;

public class PackageClassListerTest extends TestCase {

	private PackageClassLister fixture;

	protected void setUp() throws Exception {
		super.setUp();
		fixture = new PackageClassLister();
	}
	
	public void testGetClasses_inDirectory() {
		List<Class<?>> classes = fixture.getClasses("com.gramercysoftware.utils.pcltest");
		assertEquals(5, classes.size());
		assertTrue(classes.contains(OneClass.class));
		assertTrue(classes.contains(TwoClass.class));
		assertTrue(classes.contains(RedClass.class));
		assertTrue(classes.contains(Blue.class));
		assertTrue(classes.contains(ColourClass.class));
	}
	
	public void testGetClasses_inDirectory_subclassOf() {
		List<Class<?>> classes = fixture.getClasses("com.gramercysoftware.utils.pcltest", ColourClass.class);
		assertEquals(3, classes.size());
		assertFalse(classes.contains(OneClass.class));
		assertFalse(classes.contains(TwoClass.class));
		assertTrue(classes.contains(RedClass.class));
		assertTrue(classes.contains(Blue.class));
		assertTrue(classes.contains(ColourClass.class));
	}
	
	public void testGetClasses_inJar() throws Exception {
		List<Class<?>> classes = fixture.getClasses("org.apache.commons.lang.enums");
		assertEquals(4, classes.size());
		assertTrue(classes.contains(Enum.class));
		assertFalse(classes.contains(PackageClassListerTest.class));
	}

	public void testDirectoryWithPattern() {
		List<Class<?>> classes = fixture.getClasses("com.gramercysoftware.utils.pcltest", ".*Class");
		assertNotNull(classes);
		assertEquals(4, classes.size());
		assertTrue(classes.contains(OneClass.class));
		assertTrue(classes.contains(TwoClass.class));
		assertTrue(classes.contains(RedClass.class));
		assertTrue(classes.contains(ColourClass.class));
		assertFalse(classes.contains(Blue.class));
	}
	
	public void testDirectoryWithPattern_subclassOf() {
		List<Class<?>> classes = fixture.getClasses("com.gramercysoftware.utils.pcltest", ".*Class", ColourClass.class);
		assertNotNull(classes);
		assertEquals(2, classes.size());
		assertFalse(classes.contains(OneClass.class));
		assertFalse(classes.contains(TwoClass.class));
		assertTrue(classes.contains(RedClass.class));
		assertTrue(classes.contains(ColourClass.class));
		assertFalse(classes.contains(Blue.class));
	}
	
	public void testJarWithPattern() {
		List<Class<?>> classes = fixture.getClasses("org.apache.commons.lang.enum", ".*Enum");
		assertNotNull(classes);
		assertEquals(2, classes.size());
	}
	
	public void testGetClasses_acrossDirectories() throws Exception {
		Enumeration<URL> enumeration = this.getClass().getClassLoader().getResources("com/gramercysoftware/utils");
		assertTrue(enumeration.hasMoreElements());
	}
}
