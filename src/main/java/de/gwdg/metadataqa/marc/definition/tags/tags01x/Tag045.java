package de.gwdg.metadataqa.marc.definition.tags.tags01x;

import de.gwdg.metadataqa.marc.definition.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time Period of Content
 * http://www.loc.gov/marc/bibliographic/bd045.html
 */
public class Tag045 extends DataFieldDefinition {

	private static final Pattern BC = Pattern.compile("^([a-d])(-)$");
	private static final Pattern CE = Pattern.compile("^([e-y])(\\d|-)$");

	private static Tag045 uniqueInstance;

	private Tag045() {
		initialize();
		postCreation();
	}

	public static Tag045 getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Tag045();
		return uniqueInstance;
	}

	private void initialize() {

		tag = "045";
		label = "Time Period of Content";
		bibframeTag = "TemporalCoverage";
		cardinality = Cardinality.Nonrepeatable;

		ind1 = new Indicator("Type of time period in subfield $b or $c").setCodes(
			" ", "Subfield $b or $c not present",
			"0", "Single date/time",
			"1", "Multiple single dates/times",
			"2", "Range of dates/times"
		).setMqTag("type");
		ind2 = new Indicator();

		setSubfieldsWithCardinality(
			"a", "Time period code", "R",
			"b", "Formatted 9999 B.C. through C.E. time period", "R",
			"c", "Formatted pre-9999 B.C. time period", "R",
			"6", "Linkage", "NR",
			"8", "Field link and sequence number", "R"
		);

		// TODO: set a parser, move the codes there
		getSubfield("a").setCodes(
			"a0", "before 2999",
			"b0", "2999-2900",
			"b1", "2899-2800",
			"b2", "2799-2700",
			"b3", "2699-2600",
			"b4", "2599-2500",
			"b5", "2499-2400",
			"b6", "2399-2300",
			"b7", "2299-2200",
			"b8", "2199-2100",
			"b9", "2099-2000",
			"c0", "1999-1900",
			"c1", "1899-1800",
			"c2", "1799-1700",
			"c3", "1699-1600",
			"c4", "1599-1500",
			"c5", "1499-1400",
			"c6", "1399-1300",
			"c7", "1299-1200",
			"c8", "1199-1100",
			"c9", "1099-1000",
			"d0", "999-900",
			"d1", "899-800",
			"d2", "799-700",
			"d3", "699-600",
			"d4", "599-500",
			"d5", "499-400",
			"d6", "399-300",
			"d7", "299-200",
			"d8", "199-100",
			"d9", "99-1",
			"e", "1-99",
			"f", "100-199",
			"g", "200-299",
			"h", "300-399",
			"i", "400-499",
			"j", "500-599",
			"k", "600-699",
			"l", "700-799",
			"m", "800-899",
			"n", "900-999",
			"o", "1000-1099",
			"p", "1100-1199",
			"q", "1200-1299",
			"r", "1300-1399",
			"s", "1400-1499",
			"t", "1500-1599",
			"u", "1600-1699",
			"v", "1700-1799",
			"w", "1800-1899",
			"x", "1900-1999",
			"y", "2000-2099"
		);
		getSubfield("a").setValidator(new SubfieldAValidator());

		getSubfield("a").setMqTag("rdf:value");
		getSubfield("b").setMqTag("timePeriod");
		getSubfield("c").setMqTag("preBC9999TimePeriod");
		getSubfield("6").setBibframeTag("linkage");
		getSubfield("8").setMqTag("fieldLink");
	}

	class SubfieldAValidator implements Validator {

		private List<String> errors;
		private String subfieldCode;
		private SubfieldDefinition subfield;
		Map<String, String> bc = new HashMap<>();

		public SubfieldAValidator() {
			subfieldCode = "a";
			subfield = getSubfield(subfieldCode);

			/*
			Map<String, String> bc = new HashMap<>();
			bc.put("a", "-3000");
			bc.put("b", "2999-2000");
			bc.put("c", "1999-1000");
			bc.put("d", "999-1");
			*/
		}

		@Override
		public boolean isValid(String value) {

			boolean isValid = true;
			errors = new ArrayList<>();

			if (value.length() != 4) {
				errors.add(String.format("%s$%s error in '%s': length is not 4 char",
					tag, subfieldCode, value));
				isValid = false;
			} else {
				List<String> parts = Arrays.asList(
					value.substring(0, 2),
					value.substring(2, 4)
				);

				Matcher matcher;
				for (String part : parts) {
					if (subfield.getCode(part) == null) {
						matcher = BC.matcher(part);
						if (!matcher.find()) {
							matcher = CE.matcher(part);
							if (!matcher.find()) {
								errors.add(String.format("%s$%s error in '%s': '%s' does not match any patterns\n",
									tag, subfieldCode, value, part));
							}
						}
					}
				}
			}
			return isValid;
		}

		@Override
		public List<String> getErrors() {
			return errors;
		}
	}
}
