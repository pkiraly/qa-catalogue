package de.gwdg.metadataqa.marc.definition.general.parser;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Control008All00DateParser implements SubfieldContentParser {

	private String dateFormat = "yyMMdd";
	private DateTimeFormatter marc;
	DateTimeFormatter iso = DateTimeFormatter.ISO_DATE;

	public Control008All00DateParser() {
		initialize();
	}

	public Control008All00DateParser(String dateFormat) {
		this.dateFormat = dateFormat;
		initialize();
	}

	private void initialize() {
		// marc = DateTimeFormatter.ofPattern(dateFormat, Locale.getDefault());

		marc = new DateTimeFormatterBuilder()
			.appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2,
				LocalDate.now().minusYears(80))
			.appendPattern("MMdd")
			.toFormatter();
	}

	@Override
	public Map<String, String> parse(String content) throws ParserException {
		Map<String, String> extra = new HashMap<>();

		try {
			LocalDate date = LocalDate.parse(content, marc);
			extra.put("normalized", date.format(iso));
		} catch(DateTimeParseException e) {
			throw new ParserException(String.format(
				"Invalid content: '%s' at %d. %s", content, e.getErrorIndex(), e.getMessage()));
		}
		return extra;
	}
}
