package com.gramercysoftware.utils;

import java.lang.reflect.ParameterizedType;

public class GenericsUtils {
	@SuppressWarnings("unchecked")
	public static <T, H> T createT(H genericType) {
		T t = null;
		try {
			t = (T) ((Class<T>) ((ParameterizedType) genericType.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
		} catch (Exception e) {
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public static <T, H> Class<T> classOfT(H genericType) {
		Class<T> t = null;
		try {
			t = ((Class<T>) ((ParameterizedType) genericType.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		} catch (Exception e) {
		}
		return t;
	}
}
