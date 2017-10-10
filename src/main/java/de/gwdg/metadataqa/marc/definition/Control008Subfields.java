package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.ControlSubfield;
import de.gwdg.metadataqa.marc.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Control008Subfields {

	private static final Map<Control008Type, List<ControlSubfield>> subfields = new HashMap<>();

	static {
		subfields.put(Control008Type.ALL_MATERIALS, Arrays.asList(
				new ControlSubfield("Date entered on file", 0, 6)
					.setId("tag008all00").setMqTag("dateEnteredOnFile"),
				// pattern: yymmdd
				new ControlSubfield("Type of date/Publication status", 6, 7,
						Utils.generateCodes(
								"b", "No dates given; B.C. date involved",
								"c", "Continuing resource currently published",
								"d", "Continuing resource ceased publication",
								"e", "Detailed date",
								"i", "Inclusive dates of collection",
								"k", "Range of years of bulk of collection",
								"m", "Multiple dates",
								"n", "Dates unknown",
								"p", "Date of distribution/release/issue and production/recording session when different",
								"q", "Questionable date",
								"r", "Reprint/reissue date and original date",
								"s", "Single known date/probable date",
								"t", "Publication date and copyright date",
								"u", "Continuing resource status unknown",
								"|", "No attempt to code"
						))
						.setId("tag008all06").setMqTag("typeOfDateOrPublicationStatus"),
				new ControlSubfield("Date 1", 7, 11)
						.setId("tag008all07").setMqTag("date1"),
				new ControlSubfield("Date 2", 11, 15)
						.setId("tag008all11").setMqTag("date2"),
				new ControlSubfield("Place of publication, production, or execution", 15, 18)
						.setId("tag008all15").setMqTag("placeOfPublicationProductionOrExecution"),
				new ControlSubfield("Language", 35, 38)
						.setId("tag008all35").setMqTag("language"),
				new ControlSubfield("Modified record", 38, 39,
						Utils.generateCodes(
								" ", "Not modified",
								"d", "Dashed-on information omitted",
								"o", "Completely romanized/printed cards romanized",
								"r", "Completely romanized/printed cards in script",
								"s", "Shortened",
								"x", "Missing characters",
								"|", "No attempt to code"
						)
				).setId("tag008all38").setMqTag("modifiedRecord"),
				new ControlSubfield("Cataloging source", 39, 40,
						Utils.generateCodes(
								" ", "National bibliographic agency",
								"c", "Cooperative cataloging program",
								"d", "Other",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag008all39").setMqTag("catalogingSource")
		));
		subfields.put(Control008Type.BOOKS, Arrays.asList(
				new ControlSubfield("Illustrations", 18, 22,
						Utils.generateCodes(
								" ", "No illustrations",
								"a", "Illustrations",
								"b", "Maps",
								"c", "Portraits",
								"d", "Charts",
								"e", "Plans",
								"f", "Plates",
								"g", "Music",
								"h", "Facsimiles",
								"i", "Coats of arms",
								"j", "Genealogical tables",
								"k", "Forms",
								"l", "Samples",
								"m", "Phonodisc, phonowire, etc.",
								"o", "Photographs",
								"p", "Illuminations",
								"|", "No attempt to code"
						)
				).setRepeatableContent(true).setUnitLength(1)
					.setId("tag008book18").setMqTag("illustrations"),
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
				).setId("tag008book22").setMqTag("targetAudience"),
				new ControlSubfield("Form of item", 23, 24,
						Utils.generateCodes(
								" ", "None of the following",
								"a", "Microfilm",
								"b", "Microfiche",
								"c", "Microopaque",
								"d", "Large print",
								"f", "Braille"
						)
				).setId("tag008book23").setMqTag("formOfItem"),
				new ControlSubfield("Nature of contents", 24, 28,
						Utils.generateCodes(
								" ", "No specified nature of contents",
								"a", "Abstracts/summaries",
								"b", "Bibliographies",
								"c", "Catalogs",
								"d", "Dictionaries",
								"e", "Encyclopedias",
								"f", "Handbooks",
								"g", "Legal articles",
								"i", "Indexes",
								"j", "Patent document",
								"k", "Discographies",
								"l", "Legislation",
								"m", "Theses",
								"n", "Surveys of literature in a subject area",
								"o", "Reviews"
						)
				)
						.setRepeatableContent(true)
						.setUnitLength(1)
						.setId("tag008book24").setMqTag("natureOfContents"),
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
				).setId("tag008book28").setMqTag("governmentPublication"),
				new ControlSubfield("Conference publication", 29, 30,
						Utils.generateCodes(
								"0", "Not a conference publication",
								"1", "Conference publication",
								"|", "No attempt to code"
						)
				).setId("tag008book29").setMqTag("conferencePublication"),
				new ControlSubfield("Festschrift", 30, 31,
						Utils.generateCodes(
								"0", "Not a festschrift",
								"1", "Festschrift",
								"|", "No attempt to code"
						)
				).setId("tag008book30").setMqTag("festschrift"),
				new ControlSubfield("Index", 31, 32,
						Utils.generateCodes(
								"0", "No index",
								"1", "Index present",
								"|", "No attempt to code"
						)
				).setId("tag008book31").setMqTag("index"),
				// new ControlSubfield("undefined", 32, 33),
				new ControlSubfield("Literary form", 33, 34,
						Utils.generateCodes(
								"0", "Not fiction (not further specified)",
								"1", "Fiction (not further specified)",
								"d", "Dramas",
								"e", "Essays",
								"f", "Novels",
								"h", "Humor, satires, etc.",
								"i", "Letters",
								"j", "Short stories",
								"m", "Mixed forms",
								"p", "Poetry",
								"s", "Speeches",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag008book33").setMqTag("literaryForm"),
				new ControlSubfield("Biography", 34, 35,
						Utils.generateCodes(
								" ", "No biographical material",
								"a", "Autobiography",
								"b", "Individual biography",
								"c", "Collective biography",
								"d", "Contains biographical information",
								"|", "No attempt to code"
						)
				).setId("tag008book34").setMqTag("biography")
		));
		subfields.put(Control008Type.COMPUTER_FILES, Arrays.asList(
				// new ControlSubfield("undefined", 18, 21),
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
				).setId("tag008computer22").setMqTag("targetAudience"),
				new ControlSubfield("Form of item", 23, 24,
						Utils.generateCodes(
								" ", "Unknown or not specified",
								"o", "Online",
								"q", "Direct electronic",
								"|", "No attempt to code"
						)
				).setId("tag008computer23").setMqTag("formOfItem"),
				// new ControlSubfield("undefined", 24, 25),
				new ControlSubfield("Type of computer file", 26, 27,
						Utils.generateCodes(
								"a", "Numeric data",
								"b", "Computer program",
								"c", "Representational",
								"d", "Document",
								"e", "Bibliographic data",
								"f", "Font",
								"g", "Game",
								"h", "Sound",
								"i", "Interactive multimedia",
								"j", "Online system or service",
								"m", "Combination",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag008computer26").setMqTag("typeOfComputerFile"),
				// new ControlSubfield("undefined", 27, 28),
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
				).setId("tag008computer28").setMqTag("governmentPublication")
				// new ControlSubfield("undefined", 29, 35)
		));
		subfields.put(Control008Type.MAPS, Arrays.asList(
				new ControlSubfield("Relief", 18, 22,
						Utils.generateCodes(
								" ", "No relief shown",
								"a", "Contours",
								"b", "Shading",
								"c", "Gradient and bathymetric tints",
								"d", "Hachures",
								"e", "Bathymetry/soundings",
								"f", "Form lines",
								"g", "Spot heights",
								"i", "Pictorially",
								"j", "Land forms",
								"k", "Bathymetry/isolines",
								"m", "Rock drawings",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setUnitLength(1).setRepeatableContent(true)
					.setId("tag008map18").setMqTag("relief"),
				new ControlSubfield("Projection", 22, 24,
						Utils.generateCodes(
								"  ", "Projection not specified",
								"aa", "Aitoff",
								"ab", "Gnomic",
								"ac", "Lambert's azimuthal equal area",
								"ad", "Orthographic",
								"ae", "Azimuthal equidistant",
								"af", "Stereographic",
								"ag", "General vertical near-sided",
								"am", "Modified stereographic for Alaska",
								"an", "Chamberlin trimetric",
								"ap", "Polar stereographic",
								"au", "Azimuthal, specific type unknown",
								"az", "Azimuthal, other",
								"ba", "Gall",
								"bb", "Goode's homolographic",
								"bc", "Lambert's cylindrical equal area",
								"bd", "Mercator",
								"be", "Miller",
								"bf", "Mollweide",
								"bg", "Sinusoidal",
								"bh", "Transverse Mercator",
								"bi", "Gauss-Kruger",
								"bj", "Equirectangular",
								"bk", "Krovak",
								"bl", "Cassini-Soldner",
								"bo", "Oblique Mercator",
								"br", "Robinson",
								"bs", "Space oblique Mercator",
								"bu", "Cylindrical, specific type unknown",
								"bz", "Cylindrical, other",
								"ca", "Albers equal area",
								"cb", "Bonne",
								"cc", "Lambert's conformal conic",
								"ce", "Equidistant conic",
								"cp", "Polyconic",
								"cu", "Conic, specific type unknown",
								"cz", "Conic, other",
								"da", "Armadillo",
								"db", "Butterfly",
								"dc", "Eckert",
								"dd", "Goode's homolosine",
								"de", "Miller's bipolar oblique conformal conic",
								"df", "Van Der Grinten",
								"dg", "Dimaxion",
								"dh", "Cordiform",
								"dl", "Lambert conformal",
								"zz", "Other",
								"||", "No attempt to code"
						)
				).setId("tag008map22").setMqTag("projection"),
				// new ControlSubfield("undefined", 24, 25),
				new ControlSubfield("Type of cartographic material", 25, 26,
						Utils.generateCodes(
								"a", "Single map",
								"b", "Map series",
								"c", "Map serial",
								"d", "Globe",
								"e", "Atlas",
								"f", "Separate supplement to another work",
								"g", "Bound as part of another work",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag008map25").setMqTag("typeOfCartographicMaterial"),
				// new ControlSubfield("undefined", 26, 28),
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
				).setId("tag008map28").setMqTag("governmentPublication"),
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
				).setId("tag008map29").setMqTag("formOfItem"),
				// new ControlSubfield("undefined", 30, 31),
				new ControlSubfield("Index", 31, 32,
						Utils.generateCodes(
								"0", "No index",
								"1", "Index present",
								"|", "No attempt to code"
						)
				).setId("tag008map31").setMqTag("index"),
				// new ControlSubfield("undefined", 32, 33)
				new ControlSubfield("Special format characteristics", 33, 35,
						Utils.generateCodes(
								" ", "No specified special format characteristics",
								"e", "Manuscript",
								"j", "Picture card, post card",
								"k", "Calendar",
								"l", "Puzzle",
								"n", "Game",
								"o", "Wall map",
								"p", "Playing cards",
								"r", "Loose-leaf",
								"z", "Other",
								"||", "No attempt to code"
						)
				).setUnitLength(1).setRepeatableContent(true)
						.setId("tag008map33").setMqTag("specialFormatCharacteristics")
		));
		subfields.put(Control008Type.MUSIC, Arrays.asList(
				new ControlSubfield("Form of composition", 18, 20,
						Utils.generateCodes(
								"an", "Anthems",
								"bd", "Ballads",
								"bg", "Bluegrass music",
								"bl", "Blues",
								"bt", "Ballets",
								"ca", "Chaconnes",
								"cb", "Chants, Other religions",
								"cc", "Chant, Christian",
								"cg", "Concerti grossi",
								"ch", "Chorales",
								"cl", "Chorale preludes",
								"cn", "Canons and rounds",
								"co", "Concertos",
								"cp", "Chansons, polyphonic",
								"cr", "Carols",
								"cs", "Chance compositions",
								"ct", "Cantatas",
								"cy", "Country music",
								"cz", "Canzonas",
								"df", "Dance forms",
								"dv", "Divertimentos, serenades, cassations, divertissements, and notturni",
								"fg", "Fugues",
								"fl", "Flamenco",
								"fm", "Folk music",
								"ft", "Fantasias",
								"gm", "Gospel music",
								"hy", "Hymns",
								"jz", "Jazz",
								"mc", "Musical revues and comedies",
								"md", "Madrigals",
								"mi", "Minuets",
								"mo", "Motets",
								"mp", "Motion picture music",
								"mr", "Marches",
								"ms", "Masses",
								"mu", "Multiple forms",
								"mz", "Mazurkas",
								"nc", "Nocturnes",
								"nn", "Not applicable",
								"op", "Operas",
								"or", "Oratorios",
								"ov", "Overtures",
								"pg", "Program music",
								"pm", "Passion music",
								"po", "Polonaises",
								"pp", "Popular music",
								"pr", "Preludes",
								"ps", "Passacaglias",
								"pt", "Part-songs",
								"pv", "Pavans",
								"rc", "Rock music",
								"rd", "Rondos",
								"rg", "Ragtime music",
								"ri", "Ricercars",
								"rp", "Rhapsodies",
								"rq", "Requiems",
								"sd", "Square dance music",
								"sg", "Songs",
								"sn", "Sonatas",
								"sp", "Symphonic poems",
								"st", "Studies and exercises",
								"su", "Suites",
								"sy", "Symphonies",
								"tc", "Toccatas",
								"tl", "Teatro lirico",
								"ts", "Trio-sonatas",
								"uu", "Unknown",
								"vi", "Villancicos",
								"vr", "Variations",
								"wz", "Waltzes",
								"za", "Zarzuelas",
								"zz", "Other",
								"||", "No attempt to code"
						)
				).setId("tag008music18").setMqTag("formOfComposition"),
				new ControlSubfield("Format of music", 20, 21,
						Utils.generateCodes(
								"a", "Full score",
								"b", "Miniature or study score",
								"c", "Accompaniment reduced for keyboard",
								"d", "Voice score with accompaniment omitted",
								"e", "Condensed score or piano-conductor score",
								"g", "Close score",
								"h", "Chorus score",
								"i", "Condensed score",
								"j", "Performer-conductor part",
								"k", "Vocal score",
								"l", "Score",
								"m", "Multiple score formats",
								"n", "Not applicable",
								"p", "Piano score",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag008music20").setMqTag("formatOfMusic"),
				new ControlSubfield("Music parts", 21, 22,
						Utils.generateCodes(
								" ", "No parts in hand or not specified",
								"d", "Instrumental and vocal parts",
								"e", "Instrumental parts",
								"f", "Vocal parts",
								"n", "Not applicable",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag008music21").setMqTag("musicParts"),
				new ControlSubfield("Target audience", 22, 23,
						Utils.generateCodes(
								" ", "Unknown or unspecified",
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
				).setId("tag008music22").setMqTag("targetAudience"),
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
				).setId("tag008music23").setMqTag("formOfItem"),
				new ControlSubfield("Accompanying matter", 24, 30,
						Utils.generateCodes(
								" ", "No accompanying matter",
								"a", "Discography",
								"b", "Bibliography",
								"c", "Thematic index",
								"d", "Libretto or text",
								"e", "Biography of composer or author",
								"f", "Biography of performer or history of ensemble",
								"g", "Technical and/or historical information on instruments",
								"h", "Technical information on music",
								"i", "Historical information",
								"k", "Ethnological information",
								"r", "Instructional materials",
								"s", "Music",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag008music24").setMqTag("accompanyingMatter"),
				new ControlSubfield("Literary text for sound recordings", 30, 32,
						Utils.generateCodes(
								" ", "Item is a music sound recording",
								"a", "Autobiography",
								"b", "Biography",
								"c", "Conference proceedings",
								"d", "Drama",
								"e", "Essays",
								"f", "Fiction",
								"g", "Reporting",
								"h", "History",
								"i", "Instruction",
								"j", "Language instruction",
								"k", "Comedy",
								"l", "Lectures, speeches",
								"m", "Memoirs",
								"n", "Not applicable",
								"o", "Folktales",
								"p", "Poetry",
								"r", "Rehearsals",
								"s", "Sounds",
								"t", "Interviews",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag008music30").setMqTag("literaryTextForSoundRecordings"),
				// new ControlSubfield("Undefined", 32, 33),
				new ControlSubfield("Transposition and arrangement", 33, 34,
						Utils.generateCodes(
								" ", "Not arrangement or transposition or not specified",
								"a", "Transposition",
								"b", "Arrangement",
								"c", "Both transposed and arranged",
								"n", "Not applicable",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag008music33").setMqTag("transpositionAndArrangement")
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
