package com.gramercysoftware.utils;

public class EnumUtils {
	public static <E extends Enum<E>> E fromValue(Class<E> enumClass, String enumValue) {
		for(E value : enumClass.getEnumConstants()) {
			if(value.toString().equals(enumValue)) {
				return value;
			}
		}
		return null;
	}

	public static <E extends Enum<E>> E fromValueIgnoreCase(Class<E> enumClass, String enumValue) {
		for(E value : enumClass.getEnumConstants()) {
			if(value.toString().equalsIgnoreCase(enumValue)) {
				return value;
			}
		}
		return null;
	}
}
