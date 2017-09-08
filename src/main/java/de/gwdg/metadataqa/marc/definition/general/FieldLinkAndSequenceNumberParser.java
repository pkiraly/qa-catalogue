package de.gwdg.metadataqa.marc.definition.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldLinkAndSequenceNumberParser {

	public static final Pattern TWO_NUMBERS_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)\\\\([acprux])$");
	public static final Pattern ONE_NUMBER_PATTERN = Pattern.compile("^(\\d+)\\\\([acprux])$");

	private Integer linkingNumber;
	private Integer sequenceNumber;
	private String fieldLinkTypeChar;
	private LinkType fieldLinkType;
	private String input;

	public FieldLinkAndSequenceNumberParser(String input) {
		this.input = input;
		parse();
	}

	private void parse() {
		Matcher matcher = TWO_NUMBERS_PATTERN.matcher(input);
		if (matcher.matches()) {
			linkingNumber = Integer.parseInt(matcher.group(1));
			sequenceNumber = Integer.parseInt(matcher.group(2));
			fieldLinkTypeChar = matcher.group(3);
		} else {
			matcher = ONE_NUMBER_PATTERN.matcher(input);
			if (matcher.matches()) {
				linkingNumber = Integer.parseInt(matcher.group(1));
				fieldLinkTypeChar = matcher.group(2);
			}
		}
		if (fieldLinkTypeChar != null)
			fieldLinkType = LinkType.byCode(fieldLinkTypeChar);
	}

	public Integer getLinkingNumber() {
		return linkingNumber;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public String getFieldLinkTypeChar() {
		return fieldLinkTypeChar;
	}

	public LinkType getFieldLinkType() {
		return fieldLinkType;
	}
}
