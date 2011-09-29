package com.gramercysoftware.utils.numberencoder;

/**
 * Concrete implementation of the BaseNumberEncoder to encode/decode hexadecimal
 * numbers.
 * 
 * Yes, I know this comes as standard with Java, but it is a useful test case to
 * prove the algorithm works for more complex versions.
 * 
 * @author dharcombe
 */
public class HexNumberEncoder extends BaseNumberEncoder {
	public HexNumberEncoder() {
		setCharacterSet("0123456789ABCDEF");
	}
}
