package com.gramercysoftware.utils.numberencoder;

import org.apache.commons.lang.StringUtils;

/**
 * A base version of a simple number encoder capable of converting a long into a
 * String, using only the characters contained in the <b>characterSet</b> field.
 * 
 * The constructor of any concrete implementations of this should include:
 * 
 * <ol>
 * <li>a constructor that sets the character set to be used. The default
 * character set is "0123456789"</li>
 * <li>optionally, the maximum length of string that is permitted as an encoded
 * value.<br />
 * A <b>maxEncodedLength</b> <= 0 is considered unlimited.</li>
 * </ol>
 * 
 * @author dharcombe
 */
public abstract class BaseNumberEncoder implements INumberEncoder {
	private String characterSet = "0123456789";
	private int maxEncodedLength = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gramercysoftware.utils.numberencoder.INumberEncoder#encode(long)
	 */
	public String encode(long number) throws NumberEncodingException {
		validateInputNumber(number);

		StringBuffer encoded = new StringBuffer();
		do {
			encoded.insert(0, getCharacterSet().charAt((int) (number % getCharacterSet().length())));
			number /= getCharacterSet().length(); 
		} while(number != 0);
		
		return encoded.toString();
	}

	private void validateInputNumber(long number) throws NumberEncodingException {
		long maxNumber = maxEncodedLength == 0 ? Long.MAX_VALUE : (long) Math.pow(characterSet.length(), maxEncodedLength);
		if (number < 0) {
			throw new NumberTooSmallException();
		}
		if (number > maxNumber) {
			throw new NumberTooLargeException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gramercysoftware.utils.numberencoder.INumberEncoder#decode(java.lang.String)
	 */
	public long decode(String encoded) {
		if (StringUtils.isEmpty(encoded)) {
			return 0;
		}
		
		String dedocne = StringUtils.reverse(encoded);
		long decoded = 0;
		for(int i = 0 ; i < dedocne.length(); i++) {
			decoded += toLong(dedocne.charAt(i), i);
		}
		return decoded;
	}

	private long toLong(char unit, int power) {
		int charPos = StringUtils.indexOf(getCharacterSet(), unit);
		long decoded = (long) (charPos * Math.pow(getCharacterSet().length(), power));
		return decoded;
	}

	/**
	 * Fetch the character set used in this encoder/decoder
	 * 
	 * @return
	 */
	public String getCharacterSet() {
		return characterSet;
	}

	/**
	 * Check the maximum permitted length of the String that can be output by
	 * this encoder.
	 * 
	 * @return
	 */
	public int getMaxEncodedLength() {
		return maxEncodedLength;
	}

	/**
	 * Set the permissible character set for the encoder. The default is the
	 * regular decimal character list, or "0123456789"
	 * 
	 * @param characterSet
	 */
	protected void setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
	}

	/**
	 * Set the maximum permitted length of an encoded String. The default is
	 * '0', or unlimited.
	 * 
	 * @param maxEncodedLength
	 */
	protected void setMaxEncodedLength(int maxEncodedLength) {
		this.maxEncodedLength = maxEncodedLength;
	}
}
