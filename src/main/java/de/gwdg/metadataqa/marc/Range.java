package de.gwdg.metadataqa.marc;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Range implements Serializable {

	private static final Pattern numericRangePattern = Pattern.compile("^(\\d+)-(\\d+)$");

	private String range;
	private int min;
	private int max;
	boolean validRange = false;

	public Range(String range) {
		Matcher matcher = numericRangePattern.matcher(range);
		if (matcher.find()) {
			min = Integer.parseInt(matcher.group(1));
			max = Integer.parseInt(matcher.group(2));
			validRange = true;
			this.range = range;
		} else {
			throw new InvalidParameterException("Invalid range: " + range);
		}
	}

	public boolean isValid(String value) {
		try {
			int number = Integer.parseInt(value);
			return (min <= number && number <= max);
		} catch(NumberFormatException e) {
			// e.printStackTrace();
		}
		return false;
	}

	public String getRange() {
		return range;
	}
}
