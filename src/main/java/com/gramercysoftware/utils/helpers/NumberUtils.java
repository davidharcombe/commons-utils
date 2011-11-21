package com.gramercysoftware.utils.helpers;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class NumberUtils {
	public BigDecimal createBigDecimal(String s) throws ParseException {
		// 1. Strip all spaces
		s = StringUtils.replace(s, " ", "");
		
		// 2. Is it only numbers (ie integer)? If so, just convert and return, we're good.
		if(StringUtils.isNumeric(s)) {
			// No problem; it's all numbers
			return new BigDecimal(s);
		}
		
		// 3. Does it contain anything other than "-0123456789.,"? If it does, throw an error, it ain't valid.
		if(!StringUtils.containsOnly(s, "-0123456789.,")) {
			throw new ParseException(MessageFormat.format("{0} is not valid", s), 0);
		}

		// Now the nasty bit
		boolean hasComma = StringUtils.contains(s, ",");
		boolean hasPoint = StringUtils.contains(s, ".");
		boolean pointAfterComma = (StringUtils.lastIndexOf(s, ",") < StringUtils.indexOf(s, "."));
		
		if(hasComma && hasPoint) {
			if(pointAfterComma) {
				s = StringUtils.replace(s, ",", "");
			} else {
				throw new ParseException(MessageFormat.format("{0} is not valid", s), 0);
			}
		} else if(hasComma && !hasPoint) {
			// ASSUMPTION: if only 1 comma, it must be within 2 characters of the end (ie a decimal separator)
			//           : if more than 1, they must be every 3 characters
			if(Pattern.matches("[0-9]{0,3},([0-9]{3},)*[0-9]{3}", s)) {
				s = StringUtils.replace(s, ",", "");
			} else if(StringUtils.countMatches(s, ",") == 1) {
				s = StringUtils.replace(s, ",", ".");
			} else {
				throw new ParseException(MessageFormat.format("{0} is not valid", s), 0);
			}
		}
		return new BigDecimal(getStandardNumberFormat().parse(s).toString());
	}

	public NumberFormat getStandardNumberFormat() {
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
		formatter.setGroupingUsed(false);
		return formatter;
	}
}
