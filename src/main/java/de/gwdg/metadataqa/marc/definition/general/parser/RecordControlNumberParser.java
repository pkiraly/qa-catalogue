package de.gwdg.metadataqa.marc.definition.general.parser;

import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecordControlNumberParser implements SubfieldContentParser {

	private static final Pattern REGEX = Pattern.compile("^\\((.*?)\\)(.*?)$");
	private CodeList codeList = OrganizationCodes.getInstance();

	@Override
	public Map<String, String> parse(String content) {
		Map<String, String> extra = new HashMap<>();
		Matcher matcher = REGEX.matcher(content);
		if (matcher.find()) {
			extra.put("organizationCode", matcher.group(1));
			extra.put("recordNumber", matcher.group(2));
			if (codeList.isValid(matcher.group(1))) {
				extra.put("organization", codeList.getCode(matcher.group(1)).getLabel());
			}
		}
		return extra;
	}

	private static RecordControlNumberParser uniqueInstance;

	private RecordControlNumberParser() {}

	public static RecordControlNumberParser getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new RecordControlNumberParser();
		return uniqueInstance;
	}

}
