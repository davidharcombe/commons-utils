package com.gramercysoftware.utils.helpers;

import java.util.Collections;

/**
 * A simple helper to ensure that you can safely iterate over any
 * <i>Iterable</i> without many nasty null-checks before your loops.
 * 
 * @author dharcombe
 */
public class NullSafeIterable {
	/**
	 * This will return either the iterable or a validly typed empty version of
	 * the iterable. It ensures that <b>for(Foo foo : foos) {}</b> will always
	 * function correctly, even if the iterable 'foos' is null.
	 * 
	 * @param <T>
	 * @param iterable
	 * @return
	 */
	public static <T> Iterable<T> safe(Iterable<T> iterable) {
		return iterable == null ? Collections.<T> emptyList() : iterable;
	}
}
