package com.gramercysoftware.utils;

/**
 * @author David Harcombe <david.harcombe@gmail.com>
 */
public final class EnumUtils {
	private EnumUtils() {
	}
	
	/**
	 * @param enumClass
	 * @param enumValue
	 * @return
	 */
	public static <E extends Enum<E>> E fromValue(Class<E> enumClass, String enumValue) {
		for(E value : enumClass.getEnumConstants()) {
			if(value.toString().equals(enumValue)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * @param enumClass
	 * @param enumValue
	 * @return
	 */
	public static <E extends Enum<E>> E fromValueIgnoreCase(Class<E> enumClass, String enumValue) {
		for(E value : enumClass.getEnumConstants()) {
			if(value.toString().equalsIgnoreCase(enumValue)) {
				return value;
			}
		}
		return null;
	}
}
