package com.gramercysoftware.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class PropertyCopierTest {
	private PropertyCopier fixture;

	@Before
	public void setUp() throws Exception {
		fixture = new PropertyCopier();
		Logger.getLogger(PropertyCopier.class).setLevel(Level.ERROR );
	}

	@Test
	public void testCopyProperties_all() {
		SimpleOne source = new SimpleOne("one", "two");
		SimpleOne destination = new SimpleOne();
		fixture.copyProperties(source, destination);
		assertEquals(source, destination);
	}

	@Test
	public void testCopyProperties_Valid() {
		SimpleOne source = new SimpleOne("one", "two");
		SimpleOne destination = new SimpleOne();
		fixture.copyProperties(source, destination, new String[]{"one","two"});
		assertEquals(source.getOne(), destination.getOne());
		assertEquals(source.getTwo(), destination.getTwo());
	}

	@Test
	public void testCopyProperties_Invalid() {
		SimpleOne source = new SimpleOne("one", "two");
		SimpleOne destination = new SimpleOne();
		fixture.copyProperties(source, destination, new String[]{"one","three"});
		assertEquals(source.one, destination.one);
		assertNull(destination.two);
	}
	
	@Test
	public void testCopyProperties_differentNames() throws Exception {
		SimpleOne source = new SimpleOne("one", "two");
		SimpleTwo destination = new SimpleTwo();
		fixture.copyProperties(source, destination, new String[][]{ {"one","three"}, {"two","four"}});
		assertEquals(source.one, destination.three);
		assertEquals(source.two, destination.four);
	}

	@Test
	public void testCopyProperties_differentGraph() throws Exception {
		SimpleOne source = new SimpleOne("one", "two");
		NestedOne destination = new NestedOne();
		destination.setInnerOne(new SimpleOne());
		fixture.copyProperties(source, destination, new String[][]{ {"one","innerOne.one"}, {"two","innerOne.two"}});
		assertEquals(source.one, destination.innerOne.one);
		assertEquals(source.two, destination.innerOne.two);
	}
	
	@Test
	public void testCopyProperties_inflation() throws Exception {
		SimpleOne source = new SimpleOne("one", "two");
		NestedOne destination = new NestedOne();
		fixture.copyProperties(source, destination, new String[][]{ {"one","innerOne.one"}, {"two","innerOne.two"}});
		assertEquals(source.one, destination.innerOne.one);
		assertEquals(source.two, destination.innerOne.two);
	}
	
	@Test
	public void testCopyProperties_deeperInflation() throws Exception {
		SimpleOne source = new SimpleOne("one", "two");
		NestedTwo destination = new NestedTwo();
		fixture.copyProperties(source, destination, new String[][]{ {"one","first.innerOne.one"}, {"two","first.innerOne.two"}});
		assertEquals(source.one, destination.first.innerOne.one);
		assertEquals(source.two, destination.first.innerOne.two);
	}

	@Test
	public void testCopyIndexedProperty() {
		String zeroth = "zeroth";
		String first = "first";
		WithList source = new WithList(Arrays.asList(new String [] {zeroth, first}));
		SimpleOne dest = new SimpleOne("wrong", "wrong");
		fixture.copyIndexedProperty(source, dest, "list", "one", 0);
		assertEquals(zeroth, dest.getOne());

		fixture.copyIndexedProperty(source, dest, "list", "two", 1);
		assertEquals(first, dest.getTwo());
	}

	@Test
	public void testSetProperty() {
		NestedOne destination = new NestedOne();
		
		String expectedValue = "Looks like I picked the wrong week to quit sniffing glue.";
		
		fixture.setProperty(destination, "innerOne.one", expectedValue);
		
		assertEquals(expectedValue, destination.getInnerOne().getOne());
	}

	/* ********************************************
	 * INNER CLASSES
	 */
	public static class SimpleOne {
		private String one;
		private String two;

		public SimpleOne() {
		}
		
		public SimpleOne(String one, String two) {
			this.one = one;
			this.two = two;
		}

		public String getOne() {
			return one;
		}

		public void setOne(String one) {
			this.one = one;
		}

		public String getTwo() {
			return two;
		}

		public void setTwo(String two) {
			this.two = two;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((one == null) ? 0 : one.hashCode());
			result = prime * result + ((two == null) ? 0 : two.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SimpleOne other = (SimpleOne) obj;
			if (one == null) {
				if (other.one != null)
					return false;
			} else if (!one.equals(other.one))
				return false;
			if (two == null) {
				if (other.two != null)
					return false;
			} else if (!two.equals(other.two))
				return false;
			return true;
		}
	}
	
	public static class SimpleTwo {
		private String three;
		private String four;

		public SimpleTwo() {
		}
		
		public SimpleTwo(String three, String four) {
			this.three = three;
			this.four = four;
		}

		public String getThree() {
			return three;
		}

		public void setThree(String three) {
			this.three = three;
		}

		public String getFour() {
			return four;
		}

		public void setFour(String four) {
			this.four = four;
		}

	}

	public static class NestedOne {
		private SimpleOne innerOne;
		
		public NestedOne() {
			
		}

		public void setInnerOne(SimpleOne innerOne) {
			this.innerOne = innerOne;
		}

		public SimpleOne getInnerOne() {
			return innerOne;
		}
	}
	
	public static class NestedTwo {
		private NestedOne first;

		public NestedOne getFirst() {
			return first;
		}

		public void setFirst(NestedOne first) {
			this.first = first;
		}
	}
	
	public static class WithList {
		private List<String> list;

		public WithList(List<String> asList) {
			this.list = asList;
		}

		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}
	}
}