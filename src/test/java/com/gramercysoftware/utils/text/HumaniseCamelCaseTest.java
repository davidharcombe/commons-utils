package com.gramercysoftware.utils.text;

import junit.framework.TestCase;

import org.junit.Test;

public class HumaniseCamelCaseTest extends TestCase {
	private HumaniseCamelCase humaniseCamelCase;
	private String[][] testStrings;
	private String[][] _TestStrings;
	private String[][] lowercaseTestStrings;
	private String[][] uppercaseTestStrings;
	private String[][] capitaliseAllTestStrings;
	private String[][] _lowercaseTestStrings;

	public void setUp() {
		testStrings = new String[][] { { "camel", "Camel" }, { "Camel", "Camel" }, { "camelCase", "Camel case" },
				{ "CamelCase", "Camel case" }, { "doubleCamelCase", "Double camel case" },
				{ "DoubleCamelCase", "Double camel case" }, { "somethingRandomInTheHouse", "Something random in the house" },
				{ "aTLA", "A TLA" }, { "aTLAAndSomeMore", "A TLA and some more" }, { "NOTCAMELCASE", "NOTCAMELCASE" },
				{ "Certainly not camel case", "Certainly not camel case" } };
		capitaliseAllTestStrings = new String[][] { { "camel", "Camel" }, { "Camel", "Camel" },
				{ "camelCase", "Camel Case" }, { "CamelCase", "Camel Case" }, { "doubleCamelCase", "Double Camel Case" },
				{ "DoubleCamelCase", "Double Camel Case" }, { "somethingRandomInTheHouse", "Something Random In The House" },
				{ "aTLA", "A TLA" }, { "aTLAAndSomeMore", "A TLA And Some More" }, { "NOTCAMELCASE", "NOTCAMELCASE" },
				{ "Certainly not camel case", "Certainly not camel case" } };
		lowercaseTestStrings = new String[][] { { "camel", "camel" }, { "Camel", "camel" },
				{ "camelCase", "camel case" }, { "CamelCase", "camel case" }, { "doubleCamelCase", "double camel case" },
				{ "DoubleCamelCase", "double camel case" }, { "somethingRandomInTheHouse", "something random in the house" },
				{ "aTLA", "a tla" }, { "aTLAAndSomeMore", "a tla and some more" }, { "NOTCAMELCASE", "notcamelcase" },
				{ "Certainly not camel case", "Certainly not camel case" } };
		_lowercaseTestStrings = new String[][] { { "camel", "camel" }, { "Camel", "camel" },
				{ "camelCase", "camel_case" }, { "CamelCase", "camel_case" }, { "doubleCamelCase", "double_camel_case" },
				{ "DoubleCamelCase", "double_camel_case" }, { "somethingRandomInTheHouse", "something_random_in_the_house" },
				{ "aTLA", "a_tla" }, { "aTLAAndSomeMore", "a_tla_and_some_more" }, { "NOTCAMELCASE", "notcamelcase" },
				{ "addrLine1", "addr_line1" }, { "Certainly not camel case", "Certainly not camel case" } };
		uppercaseTestStrings = new String[][] { { "camel", "CAMEL" }, { "Camel", "CAMEL" },
				{ "camelCase", "CAMEL CASE" }, { "CamelCase", "CAMEL CASE" }, { "doubleCamelCase", "DOUBLE CAMEL CASE" },
				{ "DoubleCamelCase", "DOUBLE CAMEL CASE" }, { "somethingRandomInTheHouse", "SOMETHING RANDOM IN THE HOUSE" },
				{ "aTLA", "A TLA" }, { "aTLAAndSomeMore", "A TLA AND SOME MORE" }, { "NOTCAMELCASE", "NOTCAMELCASE" },
				{ "Certainly not camel case", "Certainly not camel case" } };
		_TestStrings = new String[][] { { "camel", "Camel" }, { "Camel", "Camel" }, { "camelCase", "Camel_case" },
				{ "CamelCase", "Camel_case" }, { "doubleCamelCase", "Double_camel_case" },
				{ "DoubleCamelCase", "Double_camel_case" }, { "somethingRandomInTheHouse", "Something_random_in_the_house" },
				{ "aTLA", "A_TLA" }, { "aTLAAndSomeMore", "A_TLA_and_some_more" }, { "NOTCAMELCASE", "NOTCAMELCASE" },
				{ "Certainly not camel case", "Certainly not camel case" } };
	}

	String input;
	String expectedOutcome;

	@Test
	public void testHumanise() {
		humaniseCamelCase = new HumaniseCamelCase();
		for (String[] test : testStrings) {
			assertEquals(test[1], humaniseCamelCase.humanise(test[0]));
		}
	}

	@Test
	public void testHumaniseAndLower() {
		humaniseCamelCase = new HumaniseCamelCase(HumaniseCamelCase.CASE_LOWER);
		for (String[] test : lowercaseTestStrings) {
			assertEquals(test[1], humaniseCamelCase.humanise(test[0]));
		}
	}

	@Test
	public void testHumaniseAndUpper() {
		humaniseCamelCase = new HumaniseCamelCase(HumaniseCamelCase.CASE_UPPER);
		for (String[] test : uppercaseTestStrings) {
			assertEquals(test[1], humaniseCamelCase.humanise(test[0]));
		}
	}

	@Test
	public void testHumaniseAndCaptialiseAll() {
		humaniseCamelCase = new HumaniseCamelCase(HumaniseCamelCase.CASE_CAPITALISE);
		for (String[] test : capitaliseAllTestStrings) {
			assertEquals(test[1], humaniseCamelCase.humanise(test[0]));
		}
	}

	@Test
	public void testHumaniseWithSeparator() {
		humaniseCamelCase = new HumaniseCamelCase("_");
		for (String[] test : _TestStrings) {
			assertEquals(test[1], humaniseCamelCase.humanise(test[0]));
		}
	}

	@Test
	public void testLowercaseWithSeparator() {
		humaniseCamelCase = new HumaniseCamelCase("_", HumaniseCamelCase.CASE_LOWER);
		for (String[] test : _lowercaseTestStrings) {
			assertEquals(test[1], humaniseCamelCase.humanise(test[0]));
		}
	}
}
