package com.gramercysoftware.utils;

import java.text.MessageFormat;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

/**
 * <p>Converter to turn <i>String</i>s back into <i>enum</i>s for use with the apache commons 
 * BeanUtils package. It is used in the PropertyCopier class.</p>
 * 
 * @see PropertyCopier
 * @author David Harcombe <david.harcombe@gmail.com>
 */
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
	public Object convert(Class klass, Object source) {
		try {
			return Enum.valueOf(klass, source.toString());
		} catch (Exception e) {
			logger.error(MessageFormat.format("Error converting \"{0}\" to enum type {1}.", new Object[]{
				source, ClassUtils.getShortClassName(klass)
			}));
			return null;
		}
	}

}
