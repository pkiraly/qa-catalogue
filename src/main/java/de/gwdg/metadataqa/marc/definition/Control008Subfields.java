package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.controlsubfields.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Control008Subfields {

	private static final Map<Control008Type, List<ControlSubfield>> subfields = new HashMap<>();

	static {
		subfields.put(Control008Type.ALL_MATERIALS, Arrays.asList(
			Tag008all00.getInstance(),
			Tag008all06.getInstance(),
			Tag008all07.getInstance(),
			Tag008all11.getInstance(),
			Tag008all15.getInstance(),
			Tag008all35.getInstance(),
			Tag008all38.getInstance(),
			Tag008all39.getInstance()
		));
		subfields.put(Control008Type.BOOKS, Arrays.asList(
			Tag008book18.getInstance(),
			Tag008book22.getInstance(),
			Tag008book23.getInstance(),
			Tag008book24.getInstance(),
			Tag008book28.getInstance(),
			Tag008book29.getInstance(),
			Tag008book30.getInstance(),
			Tag008book31.getInstance(),
			// new ControlSubfield("undefined", 32, 33),
			Tag008book33.getInstance(),
			Tag008book34.getInstance()
		));
		subfields.put(Control008Type.COMPUTER_FILES, Arrays.asList(
			// new ControlSubfield("undefined", 18, 21),
			Tag008computer22.getInstance(),
			Tag008computer23.getInstance(),
			// new ControlSubfield("undefined", 24, 25),
			Tag008computer26.getInstance(),
			// new ControlSubfield("undefined", 27, 28),
			Tag008computer28.getInstance()
			// new ControlSubfield("undefined", 29, 35)
		));
		subfields.put(Control008Type.MAPS, Arrays.asList(
			Tag008map18.getInstance(),
			Tag008map22.getInstance(),
			// new ControlSubfield("undefined", 24, 25),
			Tag008map25.getInstance(),
			// new ControlSubfield("undefined", 26, 28),
			Tag008map28.getInstance(),
			Tag008map29.getInstance(),
			// new ControlSubfield("undefined", 30, 31),
			Tag008map31.getInstance(),
			// new ControlSubfield("undefined", 32, 33)
			Tag008map33.getInstance()
		));
		subfields.put(Control008Type.MUSIC, Arrays.asList(
			Tag008music18.getInstance(),
			Tag008music20.getInstance(),
			Tag008music21.getInstance(),
			Tag008music22.getInstance(),
			Tag008music23.getInstance(),
			Tag008music24.getInstance(),
			Tag008music30.getInstance(),
			// new ControlSubfield("Undefined", 32, 33),
			Tag008music33.getInstance()
			// new ControlSubfield("Undefined", 34, 35)
		));
		subfields.put(Control008Type.CONTINUING_RESOURCES, Arrays.asList(
			new ControlSubfield("Frequency", 18, 19,
				Utils.generateCodes(
					" ", "No determinable frequency",
					"a", "Annual",
					"b", "Bimonthly",
					"c", "Semiweekly",
					"d", "Daily",
					"e", "Biweekly",
					"f", "Semiannual",
					"g", "Biennial",
					"h", "Triennial",
					"i", "Three times a week",
					"j", "Three times a month",
					"k", "Continuously updated",
					"m", "Monthly",
					"q", "Quarterly",
					"s", "Semimonthly",
					"t", "Three times a year",
					"u", "Unknown",
					"w", "Weekly",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag008continuing18").setMqTag("frequency"),
			new ControlSubfield("Regularity", 19, 20,
				Utils.generateCodes(
					"n", "Normalized irregular",
					"r", "Regular",
					"u", "Unknown",
					"x", "Completely irregular",
					"|", "No attempt to code"
				)
			).setId("tag008continuing19").setMqTag("regularity"),
			// new ControlSubfield("Undefined", 20, 21),
			new ControlSubfield("Type of continuing resource", 21, 22,
				Utils.generateCodes(
					" ", "None of the following",
					"d", "Updating database",
					"l", "Updating loose-leaf",
					"m", "Monographic series",
					"n", "Newspaper",
					"p", "Periodical",
					"w", "Updating Web site",
					"|", "No attempt to code"
				)
			).setId("tag008continuing21").setMqTag("typeOfContinuingResource"),
			new ControlSubfield("Form of original item", 22, 23,
				Utils.generateCodes(
					" ", "None of the following",
					"a", "Microfilm",
					"b", "Microfiche",
					"c", "Microopaque",
					"d", "Large print",
					"e", "Newspaper format",
					"f", "Braille",
					"o", "Online",
					"q", "Direct electronic",
					"s", "Electronic",
					"|", "No attempt to code"
				)
			).setId("tag008continuing22").setMqTag("formOfOriginalItem"),
			new ControlSubfield("Form of item", 23, 24,
				Utils.generateCodes(
					" ", "None of the following",
					"a", "Microfilm",
					"b", "Microfiche",
					"c", "Microopaque",
					"d", "Large print",
					"f", "Braille",
					"o", "Online",
					"q", "Direct electronic",
					"r", "Regular print reproduction",
					"s", "Electronic",
					"|", "No attempt to code"
				)
			).setId("tag008continuing23").setMqTag("formOfItem"),
			new ControlSubfield("Nature of entire work", 24, 25,
				Utils.generateCodes(
					" ", "Not specified",
					"a", "Abstracts/summaries",
					"b", "Bibliographies",
					"c", "Catalogs",
					"d", "Dictionaries",
					"e", "Encyclopedias",
					"f", "Handbooks",
					"g", "Legal articles",
					"h", "Biography",
					"i", "Indexes",
					"k", "Discographies",
					"l", "Legislation",
					"m", "Theses",
					"n", "Surveys of literature in a subject area",
					"o", "Reviews",
					"p", "Programmed texts",
					"q", "Filmographies",
					"r", "Directories",
					"s", "Statistics",
					"t", "Technical reports",
					"u", "Standards/specifications",
					"v", "Legal cases and case notes",
					"w", "Law reports and digests",
					"y", "Yearbooks",
					"z", "Treaties",
					"5", "Calendars",
					"6", "Comics/graphic novels",
					"|", "No attempt to code"
				)
			).setId("tag008continuing24").setMqTag("natureOfEntireWork"),
			new ControlSubfield("Nature of contents", 25, 28,
				Utils.generateCodes(
					" ", "Not specified",
					"a", "Abstracts/summaries",
					"b", "Bibliographies",
					"c", "Catalogs",
					"d", "Dictionaries",
					"e", "Encyclopedias",
					"f", "Handbooks",
					"g", "Legal articles",
					"h", "Biography",
					"i", "Indexes",
					"k", "Discographies",
					"l", "Legislation",
					"m", "Theses",
					"n", "Surveys of literature in a subject area",
					"o", "Reviews",
					"p", "Programmed texts",
					"q", "Filmographies",
					"r", "Directories",
					"s", "Statistics",
					"t", "Technical reports",
					"u", "Standards/specifications",
					"v", "Legal cases and case notes",
					"w", "Law reports and digests",
					"y", "Yearbooks",
					"z", "Treaties",
					"5", "Calendars",
					"6", "Comics/graphic novels",
					"|", "No attempt to code"
				)
			).setUnitLength(1).setRepeatableContent(true)
				.setId("tag008continuing25").setMqTag("natureOfContents"),
			new ControlSubfield("Government publication", 28, 29,
				Utils.generateCodes(
					" ", "Not a government publication",
					"a", "Autonomous or semi-autonomous component",
					"c", "Multilocal",
					"f", "Federal/national",
					"i", "International intergovernmental",
					"l", "Local",
					"m", "Multistate",
					"o", "Government publication-level undetermined",
					"s", "State, provincial, territorial, dependent, etc. ",
					"u", "Unknown if item is government publication",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag008continuing28").setMqTag("governmentPublication"),
			new ControlSubfield("Conference publication", 29, 30,
				Utils.generateCodes(
					"0", "Not a conference publication",
					"1", "Conference publication",
					"|", "No attempt to code"
				)
			).setId("tag008continuing29").setMqTag("conferencePublication"),
			// new ControlSubfield("Undefined", 30, 33),
			new ControlSubfield("Original alphabet or script of title", 33, 34,
				Utils.generateCodes(
					" ", "No alphabet or script given/No key title",
					"a", "Basic Roman",
					"b", "Extended Roman",
					"c", "Cyrillic",
					"d", "Japanese",
					"e", "Chinese",
					"f", "Arabic",
					"g", "Greek",
					"h", "Hebrew",
					"i", "Thai",
					"j", "Devanagari",
					"k", "Korean",
					"l", "Tamil",
					"u", "Unknown",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag008continuing33").setMqTag("originalAlphabetOrScriptOfTitle"),
			new ControlSubfield("Entry convention", 34, 35,
				Utils.generateCodes(
					"0", "Successive entry",
					"1", "Latest entry",
					"2", "Integrated entry",
					"|", "No attempt to code"
				)
			).setId("tag008continuing34").setMqTag("entryConvention")
		));
		subfields.put(Control008Type.VISUAL_MATERIALS, Arrays.asList(
			new ControlSubfield("Running time for motion pictures and videorecordings", 18, 21,
				Utils.generateCodes(
					"000", "Running time exceeds three characters",
					"001-999", "Running time", // TODO: handle this as RegEx!
					"nnn", "Not applicable",
					"---", "Unknown",
					"|||", "No attempt to code "
				)
			).setId("tag008visual18").setMqTag("runningTime"),
			// new ControlSubfield("Undefined", 21, 22),
			new ControlSubfield("Target audience", 22, 23,
				Utils.generateCodes(
					" ", "Unknown or not specified",
					"a", "Preschool",
					"b", "Primary",
					"c", "Pre-adolescent",
					"d", "Adolescent",
					"e", "Adult",
					"f", "Specialized",
					"g", "General",
					"j", "Juvenile",
					"|", "No attempt to code"
				)
			).setId("tag008visual22").setMqTag("targetAudience"),
			// new ControlSubfield("Undefined", 23, 28),
			new ControlSubfield("Government publication", 28, 29,
				Utils.generateCodes(
					" ", "Not a government publication",
					"a", "Autonomous or semi-autonomous component",
					"c", "Multilocal",
					"f", "Federal/national",
					"i", "International intergovernmental",
					"l", "Local",
					"m", "Multistate",
					"o", "Government publication-level undetermined",
					"s", "State, provincial, territorial, dependent, etc.",
					"u", "Unknown if item is government publication",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag008visual28").setMqTag("governmentPublication"),
			new ControlSubfield("Form of item", 29, 30,
				Utils.generateCodes(
					" ", "None of the following",
					"a", "Microfilm",
					"b", "Microfiche",
					"c", "Microopaque",
					"d", "Large print",
					"f", "Braille",
					"o", "Online",
					"q", "Direct electronic",
					"r", "Regular print reproduction",
					"s", "Electronic",
					"|", "No attempt to code"
				)
			).setId("tag008visual29").setMqTag("formOfItem"),
			// new ControlSubfield("Undefined", 30, 33),
			new ControlSubfield("Type of visual material", 33, 34,
				Utils.generateCodes(
					"a", "Art original",
					"b", "Kit",
					"c", "Art reproduction",
					"d", "Diorama",
					"f", "Filmstrip",
					"g", "Game",
					"i", "Picture",
					"k", "Graphic",
					"l", "Technical drawing",
					"m", "Motion picture",
					"n", "Chart",
					"o", "Flash card",
					"p", "Microscope slide",
					"q", "Model",
					"r", "Realia",
					"s", "Slide",
					"t", "Transparency",
					"v", "Videorecording",
					"w", "Toy",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag008visual33").setMqTag("typeOfVisualMaterial"),
			new ControlSubfield("Technique", 34, 35,
				Utils.generateCodes(
					"a", "Animation",
					"c", "Animation and live action",
					"l", "Live action",
					"n", "Not applicable",
					"u", "Unknown",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag008visual34").setMqTag("technique")
		));
		subfields.put(Control008Type.MIXED_MATERIALS, Arrays.asList(
			// new ControlSubfield("Undefined", 18, 23),
			new ControlSubfield("Form of item", 23, 24,
				Utils.generateCodes(
					" ", "None of the following",
					"a", "Microfilm",
					"b", "Microfiche",
					"c", "Microopaque",
					"d", "Large print",
					"f", "Braille",
					"o", "Online",
					"q", "Direct electronic",
					"r", "Regular print reproduction",
					"s", "Electronic",
					"|", "No attempt to code"
				)
			).setId("tag008mixed23").setMqTag("formOfItem")
			// new ControlSubfield("Undefined", 24, 35),
		));
	}

	public static Map<Control008Type, List<ControlSubfield>> getSubfields() {
		return subfields;
	}

	public static List<ControlSubfield> get(Control008Type category) {
		return subfields.get(category);
	}
}
