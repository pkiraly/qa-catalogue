package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Instruments or Voices Codes
 * http://www.loc.gov/marc/bibliographic/bd048.html
 */
public class MusicalInstrumentsOrVoicesCodes extends CodeList {

	private void initialize() {
		codes = Utils.generateCodes(
			"ba", "Brass - Horn",
			"bb", "Brass - Trumpet",
			"bc", "Brass - Cornet",
			"bd", "Brass - Trombone",
			"be", "Brass - Tuba",
			"bf", "Brass - Baritone",
			"bn", "Brass - Unspecified",
			"bu", "Brass - Unknown",
			"by", "Brass - Ethnic",
			"bz", "Brass - Other",
			"ca", "Choruses - Mixed",
			"cb", "Choruses - Women's",
			"cc", "Choruses - Men's",
			"cd", "Choruses - Children's",
			"cn", "Choruses - Unspecified",
			"cu", "Choruses - Unknown",
			"cy", "Choruses - Ethnic",
			"ea", "Electronic - Synthesizer",
			"eb", "Electronic - Tape",
			"ec", "Electronic - Computer",
			"ed", "Electronic - Ondes Martinot",
			"en", "Electronic - Unspecified",
			"eu", "Electronic - Unknown",
			"ez", "Electronic - Other",
			"ka", "Keyboard - Piano",
			"kb", "Keyboard - Organ",
			"kc", "Keyboard - Harpsichord",
			"kd", "Keyboard - Clavichord",
			"ke", "Keyboard - Continuo",
			"kf", "Keyboard - Celeste",
			"kn", "Keyboard - Unspecified",
			"ku", "Keyboard - Unknown",
			"ky", "Keyboard - Ethnic",
			"kz", "Keyboard - Other",
			"oa", "Larger ensemble - Full orchestra",
			"ob", "Larger ensemble - Chamber orch.",
			"oc", "Larger ensemble - String orchestra",
			"od", "Larger ensemble - Band",
			"oe", "Larger ensemble - Dance orchestra",
			"of", "Larger ensemble - Brass band (brass with some doubling, with or without percussion)",
			"on", "Larger ensemble - Unspecified",
			"ou", "Larger ensemble - Unknown",
			"oy", "Larger ensemble - Ethnic",
			"oz", "Larger ensemble - Other",
			"pa", "Percussion - Timpani",
			"pb", "Percussion - Xylophone",
			"pc", "Percussion - Marimba",
			"pd", "Percussion - Drum",
			"pn", "Percussion - Unspecified",
			"pu", "Percussion - Unknown",
			"py", "Percussion - Ethnic",
			"pz", "Percussion - Other",
			"sa", "Strings, bowed - Violin",
			"sb", "Strings, bowed - Viola",
			"sc", "Strings, bowed - Violoncello",
			"sd", "Strings, bowed - Double bass",
			"se", "Strings, bowed - Viol",
			"sf", "Strings, bowed - Viola d'amore",
			"sg", "Strings, bowed - Viola da gamba",
			"sn", "Strings, bowed - Unspecified",
			"su", "Strings, bowed - Unknown",
			"sy", "Strings, bowed - Ethnic",
			"sz", "Strings, bowed - Other",
			"ta", "Strings, plucked - Harp",
			"tb", "Strings, plucked - Guitar",
			"tc", "Strings, plucked - Lute",
			"td", "Strings, plucked - Mandolin",
			"tn", "Strings, plucked - Unspecified",
			"tu", "Strings, plucked - Unknown",
			"ty", "Strings, plucked - Ethnic",
			"tz", "Strings, plucked - Other",
			"va", "Voices - Soprano",
			"vb", "Voices - Mezzo Soprano",
			"vc", "Voices - Alto",
			"vd", "Voices - Tenor",
			"ve", "Voices - Baritone",
			"vf", "Voices - Bass",
			"vg", "Voices - Counter tenor",
			"vh", "Voices - High voice",
			"vi", "Voices - Medium voice",
			"vj", "Voices - Low voice",
			"vn", "Voices - Unspecified",
			"vu", "Voices - Unknown",
			"vy", "Voices - Ethnic",
			"wa", "Woodwinds - Flute",
			"wb", "Woodwinds - Oboe",
			"wc", "Woodwinds - Clarinet",
			"wd", "Woodwinds - Bassoon",
			"we", "Woodwinds - Piccolo",
			"wf", "Woodwinds - English horn",
			"wg", "Woodwinds - Bass clarinet",
			"wh", "Woodwinds - Recorder",
			"wi", "Woodwinds - Saxophone",
			"wn", "Woodwinds - Unspecified",
			"wu", "Woodwinds - Unknown",
			"wy", "Woodwinds - Ethnic",
			"wz", "Woodwinds - Other",
			"zn", "Unspecified instruments",
			"zu", "Unknown"
		);
		indexCodes();
	}

	private static MusicalInstrumentsOrVoicesCodes uniqueInstance;

	private MusicalInstrumentsOrVoicesCodes() {
		initialize();
	}

	public static MusicalInstrumentsOrVoicesCodes getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new MusicalInstrumentsOrVoicesCodes();
		return uniqueInstance;
	}
}
