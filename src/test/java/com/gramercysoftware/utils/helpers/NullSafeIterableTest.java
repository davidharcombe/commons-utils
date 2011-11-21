package com.gramercysoftware.utils.helpers;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class NullSafeIterableTest {
	@Test
	public void validFoos() {
		List<String> foos = Arrays.asList("foo", "bar", "baz");
		StringWriter writer = new StringWriter();
		
		for (String foo : NullSafeIterable.safe(foos)) {
			writer.append(foo);
		}
		
		Assert.assertEquals("foobarbaz", writer.toString());
	}

	@Test
	public void emptyFoos() {
		List<String> foos = new ArrayList<String>();
		StringWriter writer = new StringWriter();
		
		for (String foo : NullSafeIterable.safe(foos)) {
			writer.append(foo);
		}
		
		Assert.assertEquals(StringUtils.EMPTY, writer.toString());
	}

	@Test
	public void nullFoos() {
		List<String> foos = null;
		StringWriter writer = new StringWriter();
		
		for (String foo : NullSafeIterable.safe(foos)) {
			writer.append(foo);
		}
		
		Assert.assertEquals(StringUtils.EMPTY, writer.toString());
	}
}
