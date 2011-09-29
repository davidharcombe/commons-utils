package com.gramercysoftware.utils.numberencoder;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;

public class RomanNumeralEncoder implements INumberEncoder {
	private boolean strictRoman;

    private class Encoded {
    	long number;
    }
    
    private static class RomanValue {
        int    	intVal;
        String 	romVal;
        boolean strictRoman;

        RomanValue(int dec, String rom, boolean strictRoman) {
            this.intVal = dec;
            this.romVal = rom;
			this.strictRoman = strictRoman;
        }
    }

    final static List<RomanValue> ROMAN_VALUE_TABLE = 
    	Arrays.asList( new RomanValue[] {        
    			new RomanValue(1000, "M", true),
		        new RomanValue( 900, "CM", false),
		        new RomanValue( 500, "D", true),
		        new RomanValue( 400, "CD", false),
		        new RomanValue( 100, "C", true),
		        new RomanValue(  90, "XC", false),
		        new RomanValue(  50, "L", true),
		        new RomanValue(  40, "XL", false),
		        new RomanValue(  10, "X", true),
		        new RomanValue(   9, "IX", false),
		        new RomanValue(   5, "V", true),
		        new RomanValue(   4, "IV", false),
		        new RomanValue(   1, "I", true)
    });

    public RomanNumeralEncoder() {
		this(false);
	}
    
    public RomanNumeralEncoder(boolean strictRoman) {
    	this.strictRoman = strictRoman;
    }
    
    public String encode(long number) throws NumberEncodingException {
        if (number < 1) {
            throw new NumberTooSmallException();
        }
        
        final StringBuffer result = new StringBuffer();
        final Encoded n = new Encoded();
        n.number = number;
        
        CollectionUtils.forAllDo(ROMAN_VALUE_TABLE, new Closure() {
			public void execute(Object element) {
				RomanValue roman = (RomanValue) element;
				if(isStrictRoman(roman)) {
					return;
				}
				
	            while (n.number >= roman.intVal) {
	                n.number -= roman.intVal;   
	                result.append(roman.romVal);
	            }
			}
			
			private boolean isStrictRoman(RomanValue roman) {
				boolean foo = RomanNumeralEncoder.this.strictRoman && roman.strictRoman;
				boolean bar = !RomanNumeralEncoder.this.strictRoman || roman.strictRoman;
				return !(foo || bar);
			}
		});
        
        return result.toString();
	}

	public long decode(String encoded) {
		return 0;
	}

	public boolean isStrictRoman() {
		return strictRoman;
	}

	// jUnit only, please
	void setStrictRoman(boolean strictRoman) {
		this.strictRoman = strictRoman;
	}
}
