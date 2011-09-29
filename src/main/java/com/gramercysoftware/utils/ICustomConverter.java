package com.gramercysoftware.utils;

import org.apache.commons.beanutils.Converter;

/**
 * Implement this interface on any classes you want the PropertyCopier to have
 * custom converters for.
 * 
 * @see com.enstream.utils.classutils.PropertyCopier
 * @author dharcombe
 */
public interface ICustomConverter {
	Converter getConverter();
}
