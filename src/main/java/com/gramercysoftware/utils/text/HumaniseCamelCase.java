package com.gramercysoftware.utils.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class HumaniseCamelCase {
	public static final int CASE_UPPER = 0;
	public static final int CASE_LOWER = 1;
	public static final int CASE_CAPITALISE = 2;
	public static final int CASE_CAPITALISE_FIRST = 3;
	public static final int CASE_LEAVE = 4;

	private static final String CAMEL_CASE_PATTERN = "([A-Z]|[a-z])[a-z0-9:@]*";
	
	private final String separator;
	private final int capitalization;

	private String acronym;
	private List<String> humanised;
	
	public HumaniseCamelCase() {
		this(" ", CASE_CAPITALISE_FIRST);
	}

	public HumaniseCamelCase(String separator) {
		this(separator, CASE_CAPITALISE_FIRST);
	}

	public HumaniseCamelCase(int capitalization) {
		this(" ", capitalization);
	}

	public HumaniseCamelCase(String separator, int capitalization) {
		this.separator = separator;
		this.capitalization = capitalization;
	}

	/**
	 * Converts a camelCase to a more human form, with spaces. E.g. 'Camel case'
	 * 
	 * @param camelCaseString
	 * @return a humanised version of a camelCaseString if it is indeed
	 *         camel-case. Returns the original string if it isn't camel-case.
	 *         We assume anything with a " " in it is NOT camelCase.
	 */
	public String humanise(String camelCaseString) {
		reset();
		if (camelCaseString.indexOf(' ') == -1) {
			Matcher wordMatcher = camelCaseWordMatcher(camelCaseString);
			while (wordMatcher.find()) {
				String word = wordMatcher.group();
				boolean wordIsSingleCapitalLetter = word.matches("^[A-Z]$");
				if (wordIsSingleCapitalLetter) {
					addToAcronym(word);
				} else {
					appendAcronymIfThereIsOne();
					appendWord(word);
				}
			}
			appendAcronymIfThereIsOne();
		}
		return humanised.size() > 0 ? StringUtils.join(humanised.iterator(), getSeparator()) : camelCaseString;
	}

	private Matcher camelCaseWordMatcher(String camelCaseString) {
		return Pattern.compile(CAMEL_CASE_PATTERN).matcher(camelCaseString);
	}

	private void reset() {
		humanised = new ArrayList<String>();
		acronym = "";
	}

	private void addToAcronym(String word) {
		acronym += word;
	}

	private void appendWord(String word) {
		humanised.add(capitalise(word));
	}

	private String capitalise(String word) {
		switch (capitalization) {
		case CASE_UPPER:
			return word.toUpperCase();

		case CASE_LOWER:
			return word.toLowerCase();
			
		case CASE_CAPITALISE:
			return word.substring(0, 1).toUpperCase() + word.substring(1);
			
		case CASE_CAPITALISE_FIRST:
			if(humanised.size() > 0) {
				return word.toLowerCase();
			} else {
				return word.substring(0, 1).toUpperCase() + word.substring(1);
			}
			
		case CASE_LEAVE:
		default:
			return word;
		}
	}

	private String capitaliseAcronym() {
		switch (capitalization) {
		case CASE_UPPER:
			return acronym.toUpperCase();
			
		case CASE_LOWER:
			return acronym.toLowerCase();
			
		case CASE_CAPITALISE:
		case CASE_CAPITALISE_FIRST:
		case CASE_LEAVE:
		default:
			return acronym;
		}
	}
	
	private void appendAcronymIfThereIsOne() {
		if (acronym.length() > 0) {
			humanised.add(capitaliseAcronym());
			acronym = "";
		}
	}

	public String getSeparator() {
		return separator;
	}
}