package de.gwdg.metadataqa.marc.definition;

import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.controlsubfields.tag007.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Control007Subfields {

	private static final Map<Control007Category, List<ControlSubfield>> subfields = new HashMap<>();

	static {
		subfields.put(
			Control007Category.Common,
			Arrays.asList(
				Tag007common00.getInstance()
			)
		);

		subfields.put(
			Control007Category.Map,
			Arrays.asList(
				Tag007map00.getInstance(),
				Tag007map01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007map03.getInstance(),
				Tag007map04.getInstance(),
				Tag007map05.getInstance(),
				Tag007map06.getInstance(),
				Tag007map07.getInstance()
			)
		);

		subfields.put(
			Control007Category.ElectronicResource,
			Arrays.asList(
				Tag007electro00.getInstance(),
				Tag007electro01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007electro03.getInstance(),
				Tag007electro04.getInstance(),
				Tag007electro05.getInstance(),
				Tag007electro06.getInstance(),
				Tag007electro09.getInstance(),
				Tag007electro10.getInstance(),
				Tag007electro11.getInstance(),
				Tag007electro12.getInstance(),
				Tag007electro13.getInstance()
			)
		);

		subfields.put(
			Control007Category.Globe,
			Arrays.asList(
				Tag007globe00.getInstance(),
				Tag007globe01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007globe03.getInstance(),
				Tag007globe04.getInstance(),
				Tag007globe05.getInstance()
			)
		);

		subfields.put(
			Control007Category.TactileMaterial,
			Arrays.asList(
				Tag007tactile00.getInstance(),
				Tag007tactile01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007tactile03.getInstance(),
				Tag007tactile05.getInstance(),
				Tag007tactile06.getInstance(),
				Tag007tactile09.getInstance()
			)
		);

		subfields.put(
			Control007Category.ProjectedGraphic,
			Arrays.asList(
				Tag007projected00.getInstance(),
				Tag007projected01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007projected03.getInstance(),
				Tag007projected04.getInstance(),
				Tag007projected05.getInstance(),
				Tag007projected06.getInstance(),
				Tag007projected07.getInstance(),
				Tag007projected08.getInstance()
			)
		);

		subfields.put(
			Control007Category.Microform,
			Arrays.asList(
				Tag007microform00.getInstance(),
				Tag007microform01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007microform03.getInstance(),
				Tag007microform04.getInstance(),
				Tag007microform05.getInstance(),
				Tag007microform06.getInstance(),
				Tag007microform09.getInstance(),
				Tag007microform10.getInstance(),
				Tag007microform11.getInstance(),
				Tag007microform12.getInstance()
			)
		);

		subfields.put(
			Control007Category.NonprojectedGraphic,
			Arrays.asList(
				Tag007nonprojected00.getInstance(),
				Tag007nonprojected01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007nonprojected03.getInstance(),
				Tag007nonprojected04.getInstance(),
				Tag007nonprojected05.getInstance()
			)
		);

		subfields.put(
			Control007Category.MotionPicture,
			Arrays.asList(
				Tag007motionPicture00.getInstance(),
				Tag007motionPicture01.getInstance(),
				// new ControlSubField("Undefined", 2, 3),
				Tag007motionPicture03.getInstance(),
				Tag007motionPicture04.getInstance(),
				Tag007motionPicture05.getInstance(),
				Tag007motionPicture06.getInstance(),
				Tag007motionPicture07.getInstance(),
				Tag007motionPicture08.getInstance(),
				Tag007motionPicture09.getInstance(),
				Tag007motionPicture10.getInstance(),
				Tag007motionPicture11.getInstance(),
				Tag007motionPicture12.getInstance(),
				Tag007motionPicture13.getInstance(),
				Tag007motionPicture14.getInstance(),
				Tag007motionPicture15.getInstance(),
				Tag007motionPicture16.getInstance(),
				Tag007motionPicture17.getInstance()
			)
		);

		subfields.put(
			Control007Category.Kit,
			Arrays.asList(
				Tag007kit00.getInstance(),
				Tag007kit01.getInstance()
			)
		);

		subfields.put(Control007Category.NotatedMusic, Arrays.asList(
			// new ControlSubField("Category of material", 0, 1),
			new ControlSubfield("Specific material designation", 1, 2,
				Utils.generateCodes(
					"u", "Unspecified",
					"|", "No attempt to code"
				)
			).setId("tag007music01").setMqTag("specificMaterialDesignation")
		));
		subfields.put(Control007Category.RemoteSensingImage, Arrays.asList(
			// new ControlSubField("Category of material", 0, 1),
			new ControlSubfield("Specific material designation", 1, 2,
				Utils.generateCodes(
					"u", "Unspecified",
					"|", "No attempt to code"
				)
			).setId("tag007remoteSensing01").setMqTag("specificMaterialDesignation"),
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
			).setId("tag007remoteSensing03").setMqTag("altitudeOfSensor"),
			new ControlSubfield("Attitude of sensor", 4, 5,
				Utils.generateCodes(
					"a", "Low oblique",
					"b", "High oblique",
					"c", "Vertical",
					"n", "Not applicable",
					"u", "Unknown",
					"|", "No attempt to code"
				)
			).setId("tag007remoteSensing04").setMqTag("attitudeOfSensor"),
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
			).setId("tag007remoteSensing05").setMqTag("cloudCover"),
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
			).setId("tag007remoteSensing06").setMqTag("platformConstructionType"),
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
			).setId("tag007remoteSensing07").setMqTag("platformUseCategory"),
			new ControlSubfield("Sensor type", 8, 9,
				Utils.generateCodes(
					"a", "Active",
					"b", "Passive",
					"u", "Unknown",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag007remoteSensing08").setMqTag("sensorType"),
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
			).setId("tag007remoteSensing09").setMqTag("dataType")
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
			).setId("tag007soundRecording01").setMqTag("specificMaterialDesignation"),
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
			).setId("tag007soundRecording03").setMqTag("speed"),
			new ControlSubfield("Configuration of playback channels", 4, 5,
				Utils.generateCodes(
					"m", "Monaural",
					"q", "Quadraphonic, multichannel, or surround",
					"s", "Stereophonic",
					"u", "Unknown",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag007soundRecording04").setMqTag("configurationOfPlaybackChannels"),
			new ControlSubfield("Groove width/groove pitch", 5, 6,
				Utils.generateCodes(
					"m", "Microgroove/fine",
					"n", "Not applicable",
					"s", "Coarse/standard",
					"u", "Unknown",
					"z", "Other",
					"|", "No attempt to code"
				)
			).setId("tag007soundRecording05").setMqTag("grooveWidthOrGroovePitch"),
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
			).setId("tag007soundRecording06").setMqTag("dimensions"),
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
			).setId("tag007soundRecording07").setMqTag("tapeWidth"),
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
			).setId("tag007soundRecording08").setMqTag("tapeConfiguration"),
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
			).setId("tag007soundRecording09").setMqTag("kindOfDiscCylinderOrTape"),
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
			).setId("tag007soundRecording10").setMqTag("kindOfMaterial"),
			new ControlSubfield("Kind of cutting", 11, 12,
				Utils.generateCodes(
					"h", "Hill-and-dale cutting",
					"l", "Lateral or combined cutting",
					"n", "Not applicable",
					"u", "Unknown",
					"|", "No attempt to code"
				)
			).setId("tag007soundRecording11").setMqTag("kindOfCutting"),
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
			).setId("tag007soundRecording12").setMqTag("specialPlaybackCharacteristics"),
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
			).setId("tag007soundRecording13").setMqTag("captureAndStorageTechnique")
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
			).setId("tag007text01").setMqTag("specificMaterialDesignation")
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
			).setId("tag007video01").setMqTag("specificMaterialDesignation"),
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
			).setId("tag007video03").setMqTag("color"),
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
			).setId("tag007video04").setMqTag("videorecordingFormat"),
			new ControlSubfield("Sound on medium or separate", 5, 6,
				Utils.generateCodes(
					" ", "No sound (silent)",
					"a", "Sound on medium",
					"b", "Sound separate from medium",
					"u", "Unknown",
					"|", "No attempt to code"
				)
			).setId("tag007video05").setMqTag("soundOnMediumOrSeparate"),
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
			).setId("tag007video06").setMqTag("mediumForSound"),
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
			).setId("tag007video07").setMqTag("dimensions"),
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
			).setId("tag007video08").setMqTag("configurationOfPlaybackChannels")
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
			).setId("tag007unspecified01").setMqTag("specificMaterialDesignation")
		));
	}

	public static Map<Control007Category, List<ControlSubfield>> getSubfields() {
		return subfields;
	}

	public static List<ControlSubfield> get(Control007Category category) {
		return subfields.get(category);
	}
}
