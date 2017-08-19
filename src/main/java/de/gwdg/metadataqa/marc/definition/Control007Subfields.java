package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.ControlSubfield;
import de.gwdg.metadataqa.marc.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Control007Subfields {

	private static final Map<Control007Category, List<ControlSubfield>> subfields = new HashMap<>();

	static {
		subfields.put(Control007Category.Common, Arrays.asList(
				new ControlSubfield("Category of material", 0, 1)
		));
		subfields.put(Control007Category.Map, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"d", "Atlas",
								"g", "Diagram",
								"j", "Map",
								"k", "Profile",
								"q", "Model",
								"r", "Remote-sensing image",
								"s", "Section",
								"u", "Unspecified",
								"y", "View",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007map01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Color", 3, 4,
						Utils.generateCodes(
								"a", "One color",
								"c", "Multicolored",
								"|", "No attempt to code"
						)
				).setId("tag007map03"),
				new ControlSubfield("Physical medium", 4, 5,
						Utils.generateCodes(
								"a", "Paper",
								"b", "Wood",
								"c", "Stone",
								"d", "Metal",
								"e", "Synthetic",
								"f", "Skin",
								"g", "Textiles",
								"i", "Plastic",
								"j", "Glass",
								"l", "Vinyl",
								"n", "Vellum",
								"p", "Plaster",
								"q", "Flexible base photographic, positive",
								"r", "Flexible base photographic, negative",
								"s", "Non-flexible base photographic, positive",
								"t", "Non-flexible base photographic, negative",
								"u", "Unknown",
								"v", "Leather",
								"w", "Parchment",
								"y", "Other photographic medium",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007map04"),
				new ControlSubfield("Type of reproduction", 5, 6,
						Utils.generateCodes(
								"f", "Facsimile",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007map05"),
				new ControlSubfield("Production/reproduction details", 6, 7,
						Utils.generateCodes(
								"a", "Photocopy, blueline print",
								"b", "Photocopy",
								"c", "Pre-production",
								"d", "Film",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007map06"),
				new ControlSubfield("Positive/negative aspect", 7, 8,
						Utils.generateCodes(
								"a", "Positive",
								"b", "Negative",
								"m", "Mixed polarity",
								"n", "Not applicable",
								"|", "No attempt to code"
						)
				).setId("tag007map07")
		));
		subfields.put(Control007Category.ElectronicResource, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"a", "Tape cartridge",
								"b", "Chip cartridge",
								"c", "Computer optical disc cartridge",
								"d", "Computer disc, type unspecified",
								"e", "Computer disc cartridge, type unspecified",
								"f", "Tape cassette",
								"h", "Tape reel",
								"j", "Magnetic disk ",
								"k", "Computer card",
								"m", "Magneto-optical disc",
								"o", "Optical disc",
								"r", "Remote",
								"s", "Standalone device",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007electro01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Color", 3, 4,
						Utils.generateCodes(
								"a", "One color",
								"b", "Black-and-white",
								"c", "Multicolored",
								"g", "Gray scale",
								"m", "Mixed",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007electro03"),
				new ControlSubfield("Dimensions", 4, 5,
						Utils.generateCodes(
								"a", "3 1/2 in.",
								"e", "12 in.",
								"g", "4 3/4 in. or 12 cm.",
								"i", "1 1/8 x 2 3/8 in.",
								"j", "3 7/8 x 2 1/2 in.",
								"n", "Not applicable",
								"o", "5 1/4 in.",
								"u", "Unknown",
								"v", "8 in.",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007electro04"),
				new ControlSubfield("Sound", 5, 6,
						Utils.generateCodes(
								" ", "No sound (silent)",
								"a", "Sound",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007electro05"),
				new ControlSubfield("Image bit depth", 6, 9,
						Utils.generateCodes(
								"001-999", "Exact bit depth", // pattern!
								"mmm", "Multiple",
								"nnn", "Not applicable",
								"---", "Unknown",
								"|||", "No attempt to code"
						)
				).setId("tag007electro06"),
				new ControlSubfield("File formats", 9, 10,
						Utils.generateCodes(
								"a", "One file format",
								"m", "Multiple file formats",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007electro09"),
				new ControlSubfield("Quality assurance targets", 10, 11,
						Utils.generateCodes(
								"a", "Absent",
								"n", "Not applicable",
								"p", "Present",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007electro10"),
				new ControlSubfield("Antecedent/source", 11, 12,
						Utils.generateCodes(
								"a", "File reproduced from original",
								"b", "File reproduced from microform",
								"c", "File reproduced from an electronic resource",
								"d", "File reproduced from an intermediate (not microform)",
								"m", "Mixed",
								"n", "Not applicable",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007electro11"),
				new ControlSubfield("Level of compression", 12, 13,
						Utils.generateCodes(
								"a", "Uncompressed",
								"b", "Lossless",
								"d", "Lossy",
								"m", "Mixed",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007electro12"),
				new ControlSubfield("Reformatting quality", 13, 14,
						Utils.generateCodes(
								"a", "Access",
								"n", "Not applicable",
								"p", "Preservation",
								"r", "Replacement",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007electro13")
		));
		subfields.put(Control007Category.Globe, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"a", "Celestial globe",
								"b", "Planetary or lunar globe",
								"c", "Terrestrial globe",
								"e", "Earth moon globe",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007globe01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Color", 3, 4,
						Utils.generateCodes(
								"a", "One color",
								"c", "Multicolored",
								"|", "No attempt to code"
						)
				).setId("tag007globe03"),
				new ControlSubfield("Physical medium", 4, 5,
						Utils.generateCodes(
								"a", "Paper",
								"b", "Wood",
								"c", "Stone",
								"d", "Metal",
								"e", "Synthetic",
								"f", "Skin",
								"g", "Textile",
								"i", "Plastic",
								"l", "Vinyl",
								"n", "Vellum",
								"p", "Plaster",
								"u", "Unknown",
								"v", "Leather",
								"w", "Parchment",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007globe04"),
				new ControlSubfield("Type of reproduction", 5, 6,
						Utils.generateCodes(
								"f", "Facsimile",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007Globe05")
		));
		subfields.put(Control007Category.TactileMaterial, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"01", "Specific material designation",
								"a", "Moon",
								"b", "Braille",
								"c", "Combination",
								"d", "Tactile, with no writing system",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007tactile01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Class of braille writing", 3, 5,
						Utils.generateCodes(
								" ", "No specified class of braille writing",
								"a", "Literary braille",
								"b", "Format code braille",
								"c", "Mathematics and scientific braille",
								"d", "Computer braille",
								"e", "Music braille",
								"m", "Multiple braille types",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"||", "No attempt to code"
						)
					)
					.setUnitLength(1)
					.setRepeatableContent(true)
					.setId("tag007tactile03"),
				new ControlSubfield("Level of contraction", 5, 6,
						Utils.generateCodes(
								"a", "Uncontracted",
								"b", "Contracted",
								"m", "Combination",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007tactile05"),
				new ControlSubfield("Braille music format", 6, 9,
						Utils.generateCodes(
								" ", "No specified braille music format",
								"a", "Bar over bar",
								"b", "Bar by bar",
								"c", "Line over line",
								"d", "Paragraph",
								"e", "Single line",
								"f", "Section by section",
								"g", "Line by line",
								"h", "Open score",
								"i", "Spanner short form scoring",
								"j", "Short form scoring",
								"k", "Outline",
								"l", "Vertical score",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
					).setUnitLength(1).setRepeatableContent(true)
					.setId("tag007tactile06"),
				new ControlSubfield("Special physical characteristics", 9, 10,
						Utils.generateCodes(
								"a", "Print/braille",
								"b", "Jumbo or enlarged braille",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007tactile09")
		));
		subfields.put(Control007Category.ProjectedGraphic, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"c", "Filmstrip cartridge",
								"d", "Filmslip",
								"f", "Filmstrip, type unspecified",
								"o", "Filmstrip roll",
								"s", "Slide",
								"t", "Transparency",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007projected01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Color", 3, 4,
						Utils.generateCodes(
								"a", "One color",
								"b", "Black-and-white",
								"c", "Multicolored",
								"h", "Hand colored",
								"m", "Mixed",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007projected03"),
				new ControlSubfield("Base of emulsion", 4, 5,
						Utils.generateCodes(
								"d", "Glass",
								"e", "Synthetic",
								"j", "Safety film",
								"k", "Film base, other than safety film",
								"m", "Mixed collection",
								"o", "Paper",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007projected04"),
				new ControlSubfield("Sound on medium or separate", 5, 6,
						Utils.generateCodes(
								" ", "No sound (silent)",
								"a", "Sound on medium",
								"b", "Sound separate from medium",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007projected05"),
				new ControlSubfield("Medium for sound", 6, 7,
						Utils.generateCodes(
								" ", "No sound (silent)",
								"a", "Optical sound track on motion picture film",
								"b", "Magnetic sound track on motion picture film",
								"c", "Magnetic audio tape in cartridge",
								"d", "Sound disc",
								"e", "Magnetic audio tape on reel",
								"f", "Magnetic audio tape in cassette",
								"g", "Optical and magnetic sound track on motion picture film",
								"h", "Videotape",
								"i", "Videodisc",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007projected06"),
				new ControlSubfield("Dimensions", 7, 8,
						Utils.generateCodes(
								"a", "Standard 8 mm. film width",
								"b", "Super 8 mm./single 8 mm. film width",
								"c", "9.5 mm. film width",
								"d", "16 mm. film width",
								"e", "28 mm. film width",
								"f", "35 mm. film width",
								"g", "70 mm. film width",
								"j", "2x2 in. or 5x5 cm. slide",
								"k", "2 1/4 x 2 1/4 in. or 6x6 cm. slide",
								"s", "4x5 in. or 10x13 cm. transparency",
								"t", "5x7 in. or 13x18 cm. transparency",
								"v", "8x10 in. or 21x26 cm. transparency",
								"w", "9x9 in. or 23x23 cm. transparency",
								"x", "10x10 in. or 26x26 cm. transparency",
								"y", "7x7 in. or 18x18 cm. transparency",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007projected07"),
				new ControlSubfield("Secondary support material", 8, 9,
						Utils.generateCodes(
								" ", "No secondary support",
								"c", "Cardboard",
								"d", "Glass",
								"e", "Synthetic",
								"h", "Metal",
								"j", "Metal and glass",
								"k", "Synthetic and glass",
								"m", "Mixed collection",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007projected08")
		));
		subfields.put(Control007Category.Microform, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"a", "Aperture card",
								"b", "Microfilm cartridge",
								"c", "Microfilm cassette",
								"d", "Microfilm reel",
								"e", "Microfiche",
								"f", "Microfiche cassette",
								"g", "Microopaque",
								"h", "Microfilm slip",
								"j", "Microfilm roll",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007microform01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Positive/negative aspect", 3, 4,
						Utils.generateCodes(
								"a", "Positive",
								"b", "Negative",
								"m", "Mixed polarity",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007microform03"),
				new ControlSubfield("Dimensions", 4, 5,
						Utils.generateCodes(
								"a", "8 mm.",
								"d", "16 mm.",
								"f", "35 mm.",
								"g", "70 mm.",
								"h", "105 mm.",
								"l", "3x5 in. or 8x13 cm.",
								"m", "4x6 in. or 11x15 cm.",
								"o", "6x9 in. or 16x23 cm.",
								"p", "3 1/4 x 7 3/8 in. or 9x19 cm.",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007microform04"),
				new ControlSubfield("Reduction ratio range", 5, 6,
						Utils.generateCodes(
								"a", "Low reduction ratio",
								"b", "Normal reduction",
								"c", "High reduction",
								"d", "Very high reduction",
								"e", "Ultra high reduction",
								"u", "Unknown",
								"v", "Reduction rate varies",
								"|", "No attempt to code"
						)
				).setId("tag007microform05"),
				new ControlSubfield("Reduction ration", 6, 9).setId("tag007microform06"),
				new ControlSubfield("Color", 9, 10,
						Utils.generateCodes(
								"b", "Black-and-white",
								"c", "Multicolored",
								"m", "Mixed",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007microform09"),
				new ControlSubfield("Emulsion on film", 10, 11,
						Utils.generateCodes(
								"a", "Silver halide",
								"b", "Diazo",
								"c", "Vesicular",
								"m", "Mixed emulsion",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007microform10"),
				new ControlSubfield("Generation", 11, 12,
						Utils.generateCodes(
								"a", "First generation (master)",
								"b", "Printing master",
								"c", "Service copy",
								"m", "Mixed generation",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007microform11"),
				new ControlSubfield("Base of film", 12, 13,
						Utils.generateCodes(
								"a", "Safety base, undetermined",
								"c", "Safety base, acetate undetermined",
								"d", "Safety base, diacetate",
								"i", "Nitrate base",
								"m", "Mixed base (nitrate and safety)",
								"n", "Not applicable",
								"p", "Safety base, polyester",
								"r", "Safety base, mixed",
								"t", "Safety base, triacetate",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007microform12")
		));
		subfields.put(Control007Category.NonprojectedGraphic, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"a", "Activity card",
								"c", "Collage",
								"d", "Drawing",
								"e", "Painting",
								"f", "Photomechanical print",
								"g", "Photonegative",
								"h", "Photoprint",
								"i", "Picture",
								"j", "Print",
								"k", "Poster",
								"l", "Technical drawing",
								"n", "Chart",
								"o", "Flash card",
								"p", "Postcard",
								"q", "Icon",
								"r", "Radiograph",
								"s", "Study print",
								"u", "Unspecified",
								"v", "Photograph, type unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007nonprojected01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Color", 3, 4,
						Utils.generateCodes(
								"a", "One color",
								"b", "Black-and-white",
								"c", "Multicolored",
								"h", "Hand colored",
								"m", "Mixed",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007nonprojected03"),
				new ControlSubfield("Primary support material", 4, 5,
						Utils.generateCodes(
								"a", "Canvas",
								"b", "Bristol board",
								"c", "Cardboard/illustration board",
								"d", "Glass",
								"e", "Synthetic",
								"f", "Skin",
								"g", "Textile",
								"h", "Metal",
								"i", "Plastic",
								"l", "Vinyl",
								"m", "Mixed collection",
								"n", "Vellum",
								"o", "Paper",
								"p", "Plaster",
								"q", "Hardboard",
								"r", "Porcelain",
								"s", "Stone",
								"t", "Wood",
								"u", "Unknown",
								"v", "Leather",
								"w", "Parchment",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007nonprojected04"),
				new ControlSubfield("Secondary support material", 5, 6,
						Utils.generateCodes(
								"#", "No secondary support",
								"a", "Canvas",
								"b", "Bristol board",
								"c", "Cardboard/illustration board",
								"d", "Glass",
								"e", "Synthetic",
								"f", "Skin",
								"g", "Textile",
								"h", "Metal",
								"i", "Plastic",
								"l", "Vinyl",
								"m", "Mixed collection",
								"n", "Vellum",
								"o", "Paper",
								"p", "Plaster",
								"q", "Hardboard",
								"r", "Porcelain",
								"s", "Stone",
								"t", "Wood",
								"u", "Unknown",
								"v", "Leather",
								"w", "Parchment",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007nonprojected05")
		));
		subfields.put(Control007Category.MotionPicture, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"c", "Film cartridge",
								"f", "Film cassette",
								"o", "Film roll",
								"r", "Film reel",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Color", 3, 4,
						Utils.generateCodes(
								"b", "Black-and-white",
								"c", "Multicolored",
								"h", "Hand colored",
								"m", "Mixed",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture03"),
				new ControlSubfield("Motion picture presentation format", 4, 5,
						Utils.generateCodes(
								"a", "Standard sound aperture (reduced frame)",
								"b", "Nonanamorphic (wide-screen)",
								"c", "3D",
								"d", "Anamorphic (wide-screen)",
								"e", "Other wide-screen format",
								"f", "Standard silent aperture (full frame)",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture04"),
				new ControlSubfield("Sound on medium or separate", 5, 6,
						Utils.generateCodes(
								" ", "No sound (silent)",
								"a", "Sound on medium",
								"b", "Sound separate from medium",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture05"),
				new ControlSubfield("Medium for sound", 6, 7,
						Utils.generateCodes(
								" ", "No sound (silent)",
								"a", "Optical sound track on motion picture film",
								"b", "Magnetic sound track on motion picture film",
								"c", "Magnetic audio tape in cartridge",
								"d", "Sound disc",
								"e", "Magnetic audio tape on reel",
								"f", "Magnetic audio tape in cassette",
								"g", "Optical and magnetic sound track on motion picture film",
								"h", "Videotape",
								"i", "Videodisc",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture06"),
				new ControlSubfield("Dimensions", 7, 8,
						Utils.generateCodes(
								"a", "Standard 8 mm.",
								"b", "Super 8 mm./single 8 mm.",
								"c", "9.5 mm.",
								"d", "16 mm.",
								"e", "28 mm.",
								"f", "35 mm.",
								"g", "70 mm.",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture07"),
				new ControlSubfield("Configuration of playback channels", 8, 9,
						Utils.generateCodes(
								"k", "Mixed",
								"m", "Monaural",
								"n", "Not applicable",
								"q", "Quadraphonic, multichannel, or surround",
								"s", "Stereophonic",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture08"),
				new ControlSubfield("Production elements", 9, 10,
						Utils.generateCodes(
								"a", "Workprint",
								"b", "Trims",
								"c", "Outtakes",
								"d", "Rushes",
								"e", "Mixing tracks",
								"f", "Title bands/inter-title rolls",
								"g", "Production rolls",
								"n", "Not applicable",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture09"),
				new ControlSubfield("Positive/negative aspect", 10, 11,
						Utils.generateCodes(
								"a", "Positive",
								"b", "Negative",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture10"),
				new ControlSubfield("Generation", 11, 12,
						Utils.generateCodes(
								"d", "Duplicate",
								"e", "Master",
								"o", "Original",
								"r", "Reference print/viewing copy",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture11"),
				new ControlSubfield("Base of film", 12, 13,
						Utils.generateCodes(
								"a", "Safety base, undetermined",
								"c", "Safety base, acetate undetermined",
								"d", "Safety base, diacetate",
								"i", "Nitrate base",
								"m", "Mixed base (nitrate and safety)",
								"n", "Not applicable",
								"p", "Safety base, polyester",
								"r", "Safety base, mixed",
								"t", "Safety base, triacetate",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture12"),
				new ControlSubfield("Refined categories of color", 13, 14,
						Utils.generateCodes(
								"a", "3 layer color",
								"b", "2 color, single strip",
								"c", "Undetermined 2 color",
								"d", "Undetermined 3 color",
								"e", "3 strip color",
								"f", "2 strip color",
								"g", "Red strip",
								"h", "Blue or green strip",
								"i", "Cyan strip",
								"j", "Magenta strip",
								"k", "Yellow strip",
								"l", "S E N 2",
								"m", "S E N 3",
								"n", "Not applicable",
								"p", "Sepia tone",
								"q", "Other tone",
								"r", "Tint",
								"s", "Tinted and toned",
								"t", "Stencil color",
								"u", "Unknown",
								"v", "Hand colored",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture13"),
				new ControlSubfield("Kind of color stock or print", 14, 15,
						Utils.generateCodes(
								"a", "Imbibition dye transfer prints",
								"b", "Three-layer stock",
								"c", "Three layer stock, low fade",
								"d", "Duplitized stock",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture14"),
				new ControlSubfield("Deterioration stage", 15, 16,
						Utils.generateCodes(
								"a", "None apparent",
								"b", "Nitrate: suspicious odor",
								"c", "Nitrate: pungent odor",
								"d", "Nitrate: brownish, discoloration, fading, dusty",
								"e", "Nitrate: sticky",
								"f", "Nitrate: frothy, bubbles, blisters",
								"g", "Nitrate: congealed",
								"h", "Nitrate: powder",
								"k", "Non-nitrate: detectable deterioration",
								"l", "Non-nitrate: advanced deterioration",
								"m", "Non-nitrate: disaster",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture15"),
				new ControlSubfield("Completeness", 16, 17,
						Utils.generateCodes(
								"c", "Complete",
								"i", "Incomplete",
								"n", "Not applicable",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007motionPicture16"),
				new ControlSubfield("Film inspection date", 17, 23)
						.setId("tag007motionPicture17")
		));
		subfields.put(Control007Category.Kit, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"u", "Unspecified",
								"|", "No attempt to code"
						)
				).setId("tag007kit01")
		));
		subfields.put(Control007Category.NotatedMusic, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"u", "Unspecified",
								"|", "No attempt to code"
						)
				).setId("tag007music01")
		));
		subfields.put(Control007Category.RemoteSensingImage, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"u", "Unspecified",
								"|", "No attempt to code"
						)
				).setId("tag007remoteSensing01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Altitude of sensor", 3, 4,
						Utils.generateCodes(
								"a", "Surface",
								"b", "Airborne",
								"c", "Spaceborne",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007remoteSensing03"),
				new ControlSubfield("Attitude of sensor", 4, 5,
						Utils.generateCodes(
								"a", "Low oblique",
								"b", "High oblique",
								"c", "Vertical",
								"n", "Not applicable",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007remoteSensing04"),
				new ControlSubfield("Cloud cover", 5, 6,
						Utils.generateCodes(
								"0", "0-9%",
								"1", "10-19%",
								"2", "20-29%",
								"3", "30-39%",
								"4", "40-49%",
								"5", "50-59%",
								"6", "60-69%",
								"7", "70-79%",
								"8", "80-89%",
								"9", "90-100%",
								"n", "Not applicable",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007remoteSensing05"),
				new ControlSubfield("Platform construction type", 6, 7,
						Utils.generateCodes(
								"a", "Balloon",
								"b", "Aircraft--low altitude",
								"c", "Aircraft--medium altitude",
								"d", "Aircraft--high altitude",
								"e", "Manned spacecraft",
								"f", "Unmanned spacecraft",
								"g", "Land-based remote-sensing device",
								"h", "Water surface-based remote-sensing device",
								"i", "Submersible remote-sensing device",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007remoteSensing06"),
				new ControlSubfield("Platform use category", 7, 8,
						Utils.generateCodes(
								"a", "Meteorological",
								"b", "Surface observing",
								"c", "Space observing",
								"m", "Mixed uses",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007remoteSensing07"),
				new ControlSubfield("Sensor type", 8, 9,
						Utils.generateCodes(
								"a", "Active",
								"b", "Passive",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007remoteSensing08"),
				new ControlSubfield("Data type", 9, 11,
						Utils.generateCodes(
								"aa", "Visible light",
								"da", "Near infrared",
								"db", "Middle infrared",
								"dc", "Far infrared",
								"dd", "Thermal infrared",
								"de", "Shortwave infrared (SWIR)",
								"df", "Reflective infrared",
								"dv", "Combinations",
								"dz", "Other infrared data",
								"ga", "Sidelooking airborne radar (SLAR)",
								"gb", "Synthetic aperture radar (SAR)-Single frequency",
								"gc", "SAR-multi-frequency (multichannel)",
								"gd", "SAR-like polarization",
								"ge", "SAR-cross polarization",
								"gf", "Infometric SAR",
								"gg", "polarmetric SAR",
								"gu", "Passive microwave mapping",
								"gz", "Other microwave data",
								"ja", "Far ultraviolet",
								"jb", "Middle ultraviolet",
								"jc", "Near ultraviolet",
								"jv", "Ultraviolet combinations",
								"jz", "Other ultraviolet data",
								"ma", "Multi-spectral, multidata",
								"mb", "Multi-temporal",
								"mm", "Combination of various data types",
								"nn", "Not applicable",
								"pa", "Sonar--water depth",
								"pb", "Sonar--bottom topography images, sidescan",
								"pc", "Sonar--bottom topography, near-surface",
								"pd", "Sonar--bottom topography, near-bottom",
								"pe", "Seismic surveys",
								"pz", "Other acoustical data",
								"ra", "Gravity anomalies (general)",
								"rb", "Free-air",
								"rc", "Bouger",
								"rd", "Isostatic",
								"sa", "Magnetic field",
								"ta", "radiometric surveys",
								"uu", "Unknown",
								"zz", "Other",
								"||", "No attempt to code"
						)
				).setId("tag007remoteSensing09")
		));
		subfields.put(Control007Category.SoundRecording, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"d", "Sound disc",
								"e", "Cylinder",
								"g", "Sound cartridge",
								"i", "Sound-track film",
								"q", "Roll",
								"r", "Remote"
						)
				).setId("tag007soundRecording01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Speed", 3, 4,
						Utils.generateCodes(
								"a", "16 rpm (discs)",
								"b", "33 1/3 rpm (discs)",
								"c", "45 rpm (discs)",
								"d", "78 rpm (discs)",
								"e", "8 rpm (discs)",
								"f", "1.4 m. per second (discs)",
								"h", "120 rpm (cylinders)",
								"i", "160 rpm (cylinders)",
								"k", "15/16 ips (tapes)",
								"l", "1 7/8 ips (tapes)",
								"m", "3 3/4 ips (tapes)",
								"n", "Not applicable",
								"o", "7 1/2 ips (tapes)",
								"p", "15 ips (tapes)",
								"r", "30 ips (tape)",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording03"),
				new ControlSubfield("Configuration of playback channels", 4, 5,
						Utils.generateCodes(
								"m", "Monaural",
								"q", "Quadraphonic, multichannel, or surround",
								"s", "Stereophonic",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording04"),
				new ControlSubfield("Groove width/groove pitch", 5, 6,
						Utils.generateCodes(
								"m", "Microgroove/fine",
								"n", "Not applicable",
								"s", "Coarse/standard",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording05"),
				new ControlSubfield("Dimensions", 6, 7,
						Utils.generateCodes(
								"a", "3 in. diameter",
								"b", "5 in. diameter",
								"c", "7 in. diameter",
								"d", "10 in. diameter",
								"e", "12 in. diameter",
								"f", "16 in. diameter",
								"g", "4 3/4 in. or 12 cm. diameter",
								"j", "3 7/8 x 2 1/2 in.",
								"n", "Not applicable",
								"o", "5 1/4 x 3 7/8 in.",
								"s", "2 3/4 x 4 in.",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording06"),
				new ControlSubfield("Tape width", 7, 8,
						Utils.generateCodes(
								"l", "1/8 in.",
								"m", "1/4 in.",
								"n", "Not applicable",
								"o", "1/2 in.",
								"p", "1 in.",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording07"),
				new ControlSubfield("Tape configuration", 8, 9,
						Utils.generateCodes(
								"a", "Full (1) track",
								"b", "Half (2) track",
								"c", "Quarter (4) track",
								"d", "Eight track",
								"e", "Twelve track",
								"f", "Sixteen track",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording08"),
				new ControlSubfield("Kind of disc, cylinder, or tape", 9, 10,
						Utils.generateCodes(
								"a", "Master tape",
								"b", "Tape duplication master",
								"d", "Disc master (negative)",
								"i", "Instantaneous (recorded on the spot)",
								"m", "Mass-produced",
								"n", "Not applicable",
								"r", "Mother (positive)",
								"s", "Stamper (negative)",
								"t", "Test pressing",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording09"),
				new ControlSubfield("Kind of material", 10, 11,
						Utils.generateCodes(
								"a", "Lacquer coating",
								"b", "Cellulose nitrate",
								"c", "Acetate tape with ferrous oxide",
								"g", "Glass with lacquer",
								"i", "Aluminum with lacquer",
								"l", "Metal",
								"m", "Plastic with metal",
								"n", "Not applicable",
								"p", "Plastic",
								"r", "Paper with lacquer or ferrous oxide",
								"s", "Shellac",
								"w", "Wax",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording10"),
				new ControlSubfield("Kind of cutting", 11, 12,
						Utils.generateCodes(
								"h", "Hill-and-dale cutting",
								"l", "Lateral or combined cutting",
								"n", "Not applicable",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording11"),
				new ControlSubfield("Special playback characteristics", 12, 13,
						Utils.generateCodes(
								"a", "NAB standard",
								"b", "CCIR standard",
								"c", "Dolby-B encoded",
								"d", "dbx encoded",
								"e", "Digital recording",
								"f", "Dolby-A encoded",
								"g", "Dolby-C encoded",
								"h", "CX encoded",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording12"),
				new ControlSubfield("Capture and storage technique", 13, 14,
						Utils.generateCodes(
								"a", "Acoustical capture, direct storage",
								"b", "Direct storage, not acoustical",
								"d", "Digital storage",
								"e", "Analog electrical storage",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007soundRecording13")
		));
		subfields.put(Control007Category.Text, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"a", "Regular print",
								"b", "Large print",
								"c", "Braille",
								"d", "Loose-leaf",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007text01")
		));
		subfields.put(Control007Category.Videorecording, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"c", "Videocartridge",
								"d", "Videodisc",
								"f", "Videocassette",
								"r", "Videoreel",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007video01"),
				// new ControlSubField("Undefined", 2, 3),
				new ControlSubfield("Color", 3, 4,
						Utils.generateCodes(
								"a", "One color",
								"b", "Black-and-white",
								"c", "Multicolored",
								"m", "Mixed",
								"n", "Not applicable",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007video03"),
				new ControlSubfield("Videorecording format", 4, 5,
						Utils.generateCodes(
								"a", "Beta (1/2 in., videocassette)",
								"b", "VHS (1/2 in., videocassette)",
								"c", "U-matic (3/4 in., videocasstte)",
								"d", "EIAJ (1/2 in., reel)",
								"e", "Type C (1 in., reel)",
								"f", "Quadruplex (1 in. or 2 in., reel)",
								"g", "Laserdisc",
								"h", "CED (Capacitance Electronic Disc) videodisc",
								"i", "Betacam (1/2 in., videocassette)",
								"j", "Betacam SP (1/2 in., videocassette)",
								"k", "Super-VHS (1/2 in., videocassette)",
								"m", "M-II (1/2 in., videocassette)",
								"o", "D-2 (3/4 in., videocassette)",
								"p", "8 mm.",
								"q", "Hi-8 mm.",
								"s", "Blu-ray disc",
								"u", "Unknown",
								"v", "DVD",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007video04"),
				new ControlSubfield("Sound on medium or separate", 5, 6,
						Utils.generateCodes(
								" ", "No sound (silent)",
								"a", "Sound on medium",
								"b", "Sound separate from medium",
								"u", "Unknown",
								"|", "No attempt to code"
						)
				).setId("tag007video05"),
				new ControlSubfield("Medium for sound", 6, 7,
						Utils.generateCodes(
								" ", "No sound (silent)",
								"a", "Optical sound track on motion picture film",
								"b", "Magnetic sound track on motion picture film",
								"c", "Magnetic audio tape in cartridge",
								"d", "Sound disc",
								"e", "Magnetic audio tape on reel",
								"f", "Magnetic audio tape in cassette",
								"g", "Optical and magnetic sound track on motion picture film",
								"h", "Videotape",
								"i", "Videodisc",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007video06"),
				new ControlSubfield("Dimensions", 7, 8,
						Utils.generateCodes(
								"a", "8 mm.",
								"m", "1/4 in.",
								"o", "1/2 in.",
								"p", "1 in.",
								"q", "2 in.",
								"r", "3/4 in.",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007video07"),
				new ControlSubfield("Configuration of playback channels", 8, 9,
						Utils.generateCodes(
								"k", "Mixed",
								"m", "Monaural",
								"n", "Not applicable",
								"q", "Quadraphonic, multichannel, or surround",
								"s", "Stereophonic",
								"u", "Unknown",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007video08")
		));
		subfields.put(Control007Category.Unspecified, Arrays.asList(
				// new ControlSubField("Category of material", 0, 1),
				new ControlSubfield("Specific material designation", 1, 2,
						Utils.generateCodes(
								"m", "Multiple physical forms",
								"u", "Unspecified",
								"z", "Other",
								"|", "No attempt to code"
						)
				).setId("tag007unspecified01")
		));
	}

	public static List<ControlSubfield> get(Control007Category category) {
		return subfields.get(category);
	}
}
