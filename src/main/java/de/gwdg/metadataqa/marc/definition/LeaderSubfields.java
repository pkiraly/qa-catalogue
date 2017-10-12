package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Utils;

import java.util.*;

public class LeaderSubfields {

	private static final List<ControlSubfield> subfields = new ArrayList<>();
	private static final Map<String, ControlSubfield> subfieldLabelMap = new HashMap<>();
	private static final Map<String, ControlSubfield> subfieldIdMap = new HashMap<>();

	static {

		addAllSubfields(Arrays.asList(
			new ControlSubfield("Record length", 0, 5)
				.setId("leader01").setMqTag("recordLength"),

			new ControlSubfield("Record status", 5, 6,
				Utils.generateCodes(
					"a", "Increase in encoding level",
					"c", "Corrected or revised",
					"d", "Deleted",
					"n", "New",
					"p", "Increase in encoding level from prepublication"
				))
				.setId("leader05").setMqTag("recordStatus"),

			new ControlSubfield("Type of record", 6, 7,
				Utils.generateCodes(
					"a", "Language material",
					"c", "Notated music",
					"d", "Manuscript notated music",
					"e", "Cartographic material",
					"f", "Manuscript cartographic material",
					"g", "Projected medium",
					"i", "Nonmusical sound recording",
					"j", "Musical sound recording",
					"k", "Two-dimensional nonprojectable graphic",
					"m", "Computer file",
					"o", "Kit",
					"p", "Mixed materials",
					"r", "Three-dimensional artifact or naturally occurring object",
					"t", "Manuscript language material"
				)).setId("leader06").setMqTag("typeOfRecord"),

			new ControlSubfield("Bibliographic level", 7, 8, Utils.generateCodes(
				"a", "Monographic component part",
				"b", "Serial component part",
				"c", "Collection",
				"d", "Subunit",
				"i", "Integrating resource",
				"m", "Monograph/Item",
				"s", "Serial"
			)).setId("leader07").setMqTag("bibliographicLevel"),

			new ControlSubfield("Type of control", 8, 9,
				Utils.generateCodes(
				" ", "No specified type",
				"a", "Archival"
			)).setId("leader08").setMqTag("typeOfControl"),

			new ControlSubfield("Character coding scheme", 9, 10, Utils.generateCodes(
				" ", "MARC-8",
				"a", "UCS/Unicode"
			)).setId("leader09").setMqTag("characterCodingScheme"),

			new ControlSubfield("Indicator count", 10, 11, Utils.generateCodes(
			)).setId("leader10").setMqTag("indicatorCount"),

			new ControlSubfield("Subfield code count", 11, 12, Utils.generateCodes(
			)).setId("leader11").setMqTag("subfieldCodeCount"),

			new ControlSubfield("Base address of data", 12, 16, Utils.generateCodes(
			)).setId("leader12").setMqTag("baseAddressOfData"),

			new ControlSubfield("Encoding level", 17, 18, Utils.generateCodes(
				" ", "Full level",
				"1", "Full level, material not examined",
				"2", "Less-than-full level, material not examined",
				"3", "Abbreviated level",
				"4", "Core level",
				"5", "Partial (preliminary) level",
				"7", "Minimal level",
				"8", "Prepublication level",
				"u", "Unknown",
				"z", "Not applicable"
			)).setId("leader17").setMqTag("encodingLevel"),

			new ControlSubfield("Descriptive cataloging form", 18, 19, Utils.generateCodes(
				" ", "Non-ISBD",
				"a", "AACR 2",
				"c", "ISBD punctuation omitted",
				"i", "ISBD punctuation included",
				"n", "Non-ISBD punctuation omitted",
				"u", "Unknown"
			)).setId("leader18").setMqTag("descriptiveCatalogingForm"),

			new ControlSubfield("Multipart resource record level", 19, 20, Utils.generateCodes(
				" ", "Not specified or not applicable",
				"a", "Set",
				"b", "Part with independent title",
				"c", "Part with dependent title"
			)).setId("leader19").setMqTag("multipartResourceRecordLevel"),

			new ControlSubfield("Length of the length-of-field portion",20, 21)
				.setId("leader20").setMqTag("lengthOfTheLengthOfFieldPortion"),

			new ControlSubfield("Length of the starting-character-position portion", 21, 22)
				.setId("leader21").setMqTag("lengthOfTheStartingCharacterPositionPortion"),

			new ControlSubfield("Length of the implementation-defined portion", 22, 23)
				.setId("leader22").setMqTag("lengthOfTheImplementationDefinedPortion")
			// new ControlSubField("undefined", 23, 24)
		));
	}

	private static void addAllSubfields(List<ControlSubfield> _subfields) {
		subfields.addAll(_subfields);
		for (ControlSubfield subField : _subfields) {
			subfieldLabelMap.put(subField.getLabel(), subField);
			subfieldIdMap.put(subField.getId(), subField);
		}
	}

	public static List<ControlSubfield> getSubfields() {
		return subfields;
	}

	public static ControlSubfield getByLabel(String key) {
		return subfieldLabelMap.get(key);
	}

	public static ControlSubfield getById(String key) {
		return subfieldIdMap.get(key);
	}

}
