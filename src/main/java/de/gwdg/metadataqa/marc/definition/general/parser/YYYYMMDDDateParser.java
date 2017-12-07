package de.gwdg.metadataqa.marc.definition.general.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class YYYYMMDDDateParser implements SubfieldContentParser {

	private String dateFormat = "yyyyMMdd";
	private DateTimeFormatter marc;
	DateTimeFormatter iso = DateTimeFormatter.ISO_DATE;

	private static YYYYMMDDDateParser uniqueInstance;

	private YYYYMMDDDateParser() {
		initialize();
	}

	public static YYYYMMDDDateParser getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new YYYYMMDDDateParser();
		return uniqueInstance;
	}

	public YYYYMMDDDateParser(String dateFormat) {
		this.dateFormat = dateFormat;
		initialize();
	}

	private void initialize() {
		marc = DateTimeFormatter.ofPattern(dateFormat, Locale.getDefault());
	}

	@Override
	public Map<String, String> parse(String content) throws ParserException {
		Map<String, String> extra = new HashMap<>();

		try {
			LocalDate date = LocalDate.parse(content, marc);
			extra.put("normalized", date.format(iso));
		} catch(DateTimeParseException e) {
			throw new ParserException(String.format(
				"Invalid content: '%s'. %s", content, e.getMessage()));
		}
		return extra;
	}
}
