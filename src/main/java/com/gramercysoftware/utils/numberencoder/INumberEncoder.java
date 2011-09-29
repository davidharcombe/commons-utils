package com.gramercysoftware.utils.numberencoder;

/**
 * @author dharcombe
 */
public interface INumberEncoder {
	/**
	 * Convert the given long into the String representation of it using only
	 * the characters specified in the implementation.
	 * 
	 * @param number
	 * @return
	 * @throws NumberEncodingException
	 */
	String encode(long number) throws NumberEncodingException;

	/**
	 * Decode the String supplied into the equivalent long.
	 * 
	 * @param encoded
	 * @return
	 */
	long decode(String encoded);
}
