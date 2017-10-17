package de.gwdg.metadataqa.marc.definition.general.parser;

import de.gwdg.metadataqa.marc.definition.general.Linkage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkageParser implements SubfieldContentParser {

	private static final Pattern REGEX = Pattern.compile("^(\\d{3})-(\\d{2})(?:/(.*?))?(?:/(.*?))?$");

	public Map<String, String> parse(String input) {
		Linkage linkage = create(input);
		return linkage.getMap();
	}

	public Linkage create(String input) {
		Matcher matcher = REGEX.matcher(input);
		if (matcher.find()) {
			if (matcher.group(1) != null) {
				Linkage linkage = new Linkage(matcher.group(1), matcher.group(2));
				if (matcher.group(3) != null) {
					linkage.setScriptIdentificationCode(matcher.group(3));
					if (matcher.group(4) != null)
						linkage.setFieldOrientationCode(matcher.group(4));
				}
				return linkage;
			}
		}

		return null;
	}

	private static LinkageParser uniqueInstance;

	private LinkageParser() {}

	public static LinkageParser getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new LinkageParser();
		return uniqueInstance;
	}

}
