package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.general.codelist.CountryCodes;
import de.gwdg.metadataqa.marc.definition.general.codelist.LanguageCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Implementation of the scoring algorithm described in
 * Leveraging Python to improve ebook metadata selection, ingest, and management
 * by Kelly Thompson and Stacie Traill
 * Code4Lib Journal, Issue 38, 2017-10-18
 * http://journal.code4lib.org/articles/12828
 */
public class ThompsonTraillAnalysis {

	private static final Logger logger = Logger.getLogger(ThompsonTraillAnalysis.class.getCanonicalName());
	private static final Pattern datePattern = Pattern.compile("^(14[5-9]\\d|1[5-9]\\d\\d|200\\d|201[0-7])$");

	public static List<String> getHeader() {
		return Arrays.asList(
			"id", "ISBN", "Authors", "Alternative Titles", "Edition",
			"Contributors", "Series", "TOC", "Date 008", "Date 26X", "LC/NLM",
			"LoC", "Mesh", "Fast", "GND", "Other", "Online", "Language of Resource",
			"Country of Publication", "noLanguageOrEnglish", "RDA", "total"
		);
	}

	public static List<Integer> getScores(MarcRecord marcRecord) {
		List<Integer> scores = new ArrayList<>();

		// ISBN
		scores.add(countFields(marcRecord, Arrays.asList("020")));

		// Authors
		scores.add(countFields(marcRecord, Arrays.asList("100", "110", "111")));

		// Alternative Titles
		scores.add(countFields(marcRecord, Arrays.asList("246")));

		// Edition
		scores.add(countFields(marcRecord, Arrays.asList("250")));

		// Contributors
		scores.add(countFields(marcRecord, Arrays.asList("700", "710", "711", "720")));

		// Series
		scores.add(countFields(marcRecord, Arrays.asList("440", "490", "800", "810", "830")));

		// Table of Contents and Abstract
		// 505, 520	2 points if both fields exist; 1 point if either field exists
		int score = 0;
		score += exists(marcRecord, "505") ? 1 : 0;
		score += exists(marcRecord, "520") ? 1 : 0;
		scores.add(score);

		// Date (MARC 008)	008/7-10	1 point if valid coded date exists
		String date008 = marcRecord.getControl008().getTag008all07().getValue();
		score = datePattern.matcher(date008).matches() ? 1 : 0;
		scores.add(score);

		// Date (MARC 26X)
		// 	260$c or 264$c
		// 	1 point if 4-digit date exists; 1 point if matches 008 date.
		score = 0;
		if (exists(marcRecord, "260")) {
			List<DataField> fields = marcRecord.getDatafield("260");
			for (DataField field : fields) {
				List<MarcSubfield> subfields = field.getSubfield("c");
				if (subfields != null && !subfields.isEmpty()) {
					for (MarcSubfield subfield : subfields) {
						if (score == 0)
							score = 1;
						if (score < 2 && subfield.getValue().contains(date008))
							score = 2;
					}
				}
			}
		}
		if (exists(marcRecord, "264")) {
			List<DataField> fields = marcRecord.getDatafield("264");
			for (DataField field : fields) {
				List<MarcSubfield> subfields = field.getSubfield("c");
				if (subfields != null && !subfields.isEmpty()) {
					for (MarcSubfield subfield : subfields) {
						if (score == 0)
							score = 1;
						if (score < 2 && subfield.getValue().contains(date008))
							score = 2;
					}
				}
			}
		}
		scores.add(score);


		// LC/NLM Classification	050, 060, 090	1 point if any field exists
		score = (exists(marcRecord, "050") || exists(marcRecord, "060") || exists(marcRecord, "090")) ? 1 : 0;
		scores.add(score);

		// 600 - Personal Name
		// 610 - Corporate Name
		// 611 - Meeting Name
		// 630 - Uniform Title
		// 650 - Topical Term
		// 651 - Geographic Name
		// 653 - Uncontrolled Index Term
		// Subject Headings: Library of Congress
		// 600, 610, 611, 630, 650, 651 second indicator 0
		// 	1 point for each field up to 10 total points
		// Subject Headings: MeSH	600, 610, 611, 630, 650, 651 second indicator 2	1 point for each field up to 10 total points
		// Subject Headings: FAST	600, 610, 611, 630, 650, 651 second indicator 7, $2 fast	1 point for each field up to 10 total points
		// Subject Headings: Other	600, 610, 611, 630, 650, 651, 653 if above criteria are not met	1 point for each field up to 5 total points
		int lcScore = 0;
		int meshScore = 0;
		int fastScore = 0;
		int gndScore = 0;
		int otherScore = 0;
		for (String tag : Arrays.asList("600", "610", "611", "630", "650", "651", "653")) {
			if (exists(marcRecord, tag)) {
				List<DataField> fields = marcRecord.getDatafield(tag);
				for (DataField field : fields) {
					if (field.getInd2().equals("0"))
						lcScore++;
					else if (field.getInd2().equals("2"))
						meshScore++;
					else if (field.getInd2().equals("7")) {
						List<MarcSubfield> subfield2 = field.getSubfield("2");
						if (subfield2 == null) {
							logger.severe(String.format(
								"Error in %s: ind2 = 7, but there is no $2",
								marcRecord.getControl001().getContent()));
						} else
							switch (field.getSubfield("2").get(0).getValue()) {
								case "fast": fastScore++; break;
								case "gnd": gndScore++; break;
								default: otherScore++; break;
							}
					}
					else {
						otherScore++;
					}
				}
			}
		}
		scores.add(Math.min(lcScore, 10));
		scores.add(Math.min(meshScore, 10));
		scores.add(Math.min(fastScore, 10));
		scores.add(Math.min(gndScore, 10));
		scores.add(Math.min(otherScore, 15));

		// Description	008/23=o and 300$a “online resource”	2 points if both elements exist; 1 point if either exists
		String formOfItem = null;
		switch (marcRecord.getType()) {
			case BOOKS: formOfItem = marcRecord.getControl008().getTag008book23().getValue(); break;
			case COMPUTER_FILES: formOfItem = marcRecord.getControl008().getTag008computer23().getValue(); break;
			case CONTINUING_RESOURCES: formOfItem = marcRecord.getControl008().getTag008continuing23().getValue(); break;
			case MAPS: formOfItem = marcRecord.getControl008().getTag008map29().getValue(); break;
			case MIXED_MATERIALS: formOfItem = marcRecord.getControl008().getTag008mixed23().getValue(); break;
			case MUSIC: formOfItem = marcRecord.getControl008().getTag008music23().getValue(); break;
			case VISUAL_MATERIALS: formOfItem = marcRecord.getControl008().getTag008visual29().getValue(); break;
		}
		score = formOfItem != null && formOfItem.equals("o") ? 1 : 0;
		List<DataField> fields300 = marcRecord.getDatafield("300");
		boolean isOnlineResource = false;
		if (fields300 != null && !fields300.isEmpty()) {
			for (DataField field : fields300) {
				List<MarcSubfield> subfields = field.getSubfield("a");
				if (subfields != null && !subfields.isEmpty())
					for (MarcSubfield subfield : subfields)
						if (!isOnlineResource && subfield.getValue().equals("online resource"))
							isOnlineResource = true;
			}
		}
		score = isOnlineResource ? 1 : 0;
		scores.add(score);

		// Language of Resource	008/35-37	1 point if likely language code exists
		scores.add(LanguageCodes.getInstance().isValid(marcRecord.getControl008().getTag008all35().getValue()) ? 1 : 0);

		// Country of Publication Code	008/15-17	1 point if likely country code exists
		scores.add(CountryCodes.getInstance().isValid(marcRecord.getControl008().getTag008all15().getValue()) ? 1 : 0);

		// Language of Cataloging	040$b	1 point if either no language is specified, or if English is specified
		// Descriptive cataloging standard	040$e	1 point if value is “rda”
		List<DataField> fields040 = marcRecord.getDatafield("040");
		boolean noLanguageOrEnglish = false;
		boolean isRDA = false;
		if (fields040 != null && !fields040.isEmpty()) {
			for (DataField language : fields040) {
				List<MarcSubfield> subfields = language.getSubfield("b");
				if (subfields != null && !subfields.isEmpty()) {
					for (MarcSubfield subfield : subfields) {
						if (!noLanguageOrEnglish && subfield.getValue().equals("eng"))
							noLanguageOrEnglish = true;
					}
				}
				subfields = language.getSubfield("e");
				if (subfields != null && !subfields.isEmpty())
					for (MarcSubfield subfield : subfields)
						if (!isRDA && subfield.getValue().equals("rda"))
							isRDA = true;
			}
		}
		scores.add(noLanguageOrEnglish ? 1 : 0);
		scores.add(isRDA ? 1 : 0);

		scores.add(getTotal(scores));
		return scores;
	}

	private static Integer getTotal(List<Integer> scores) {
		int total = 0;
		for (Integer score : scores) {
			total += score;
		}
		return total;
	}

	private static boolean exists(MarcRecord marcRecord, String tag) {
		List<DataField> fields = marcRecord.getDatafield(tag);
		return (fields != null && !fields.isEmpty());
	}

	private static int countFields(MarcRecord marcRecord, List<String> tags) {
		int counter = 0;
		for (String tag : tags) {
			if (exists(marcRecord, tag))
				counter += marcRecord.getDatafield(tag).size();
		}
		return counter;
	}

	class BoardEntry {
		String recordElement;
		Integer count;
	}
}
