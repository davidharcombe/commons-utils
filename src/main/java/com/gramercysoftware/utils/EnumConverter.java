package com.gramercysoftware.utils;

import java.text.MessageFormat;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

public class EnumConverter implements Converter {
	private Logger logger = Logger.getLogger(EnumConverter.class);

	/**
	 * Convert a string to an enum
	 * 
	 * @param klass The Enum type to create
	 * @param source the string to be converted
	 * @return the converted object
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
	public Object convert(Class klass, Object source) {
		if(source == null) {
			return null;
		}
		
		try {
			try {
				return Enum.valueOf(klass, source.toString());
			} catch(IllegalArgumentException e) {
				return EnumUtils.fromValue(klass, source.toString());
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format("Error converting {0} to {1}.", new Object[]{
				source, ClassUtils.getShortClassName(klass)
			}));
			return null;
		}
	}

}
