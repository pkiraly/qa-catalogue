package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Control008Subfields;
import de.gwdg.metadataqa.marc.definition.Control008Type;
import de.gwdg.metadataqa.marc.definition.ControlValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control008 {

	private static final Logger logger = Logger.getLogger(Control008.class.getCanonicalName());

	private String content;
	private Leader.Type recordType;
	private Map<ControlSubfield, Object> valueMap;

	private ControlValue tag008all00;
	private ControlValue tag008all06;
	private ControlValue tag008all07;
	private ControlValue tag008all11;
	private ControlValue tag008all15;
	private ControlValue tag008all35;
	private ControlValue tag008all38;
	private ControlValue tag008all39;

	private ControlValue tag008book18;
	private ControlValue tag008book22;
	private ControlValue tag008book23;
	private ControlValue tag008book24;
	private ControlValue tag008book28;
	private ControlValue tag008book29;
	private ControlValue tag008book30;
	private ControlValue tag008book31;
	private ControlValue tag008book33;
	private ControlValue tag008book34;

	private ControlValue tag008computer22;
	private ControlValue tag008computer23;
	private ControlValue tag008computer26;
	private ControlValue tag008computer28;

	private ControlValue tag008map18;
	private ControlValue tag008map22;
	private ControlValue tag008map25;
	private ControlValue tag008map28;
	private ControlValue tag008map29;
	private ControlValue tag008map31;
	private ControlValue tag008map33;

	private ControlValue tag008music18;
	private ControlValue tag008music20;
	private ControlValue tag008music21;
	private ControlValue tag008music22;
	private ControlValue tag008music23;
	private ControlValue tag008music24;
	private ControlValue tag008music30;
	private ControlValue tag008music33;

	private ControlValue tag008continuing18;
	private ControlValue tag008continuing19;
	private ControlValue tag008continuing21;
	private ControlValue tag008continuing22;
	private ControlValue tag008continuing23;
	private ControlValue tag008continuing24;
	private ControlValue tag008continuing25;
	private ControlValue tag008continuing28;
	private ControlValue tag008continuing29;
	private ControlValue tag008continuing33;
	private ControlValue tag008continuing34;

	private ControlValue tag008visual18;
	private ControlValue tag008visual22;
	private ControlValue tag008visual28;
	private ControlValue tag008visual29;
	private ControlValue tag008visual33;
	private ControlValue tag008visual34;

	private ControlValue tag008mixed23;

	public Control008(String content, Leader.Type recordType) {
		this.content = content;
		this.recordType = recordType;
		valueMap = new LinkedHashMap<>();
		process();
	}

	private void process() {
		for (ControlSubfield subfield : Control008Subfields.get(Control008Type.ALL_MATERIALS)) {
			int end = Math.min(content.length(), subfield.getPositionEnd());
			if (end < 0) {
				logger.severe(content.length() + " " + subfield.getPositionEnd());
			}
			try {
				String value = content.substring(subfield.getPositionStart(), end);

				switch (subfield.getId()) {
					case "tag008all00": tag008all00 = new ControlValue(subfield, value); break;
					case "tag008all06": tag008all06 = new ControlValue(subfield, value); break;
					case "tag008all07": tag008all07 = new ControlValue(subfield, value); break;
					case "tag008all11": tag008all11 = new ControlValue(subfield, value); break;
					case "tag008all15": tag008all15 = new ControlValue(subfield, value); break;
					case "tag008all35": tag008all35 = new ControlValue(subfield, value); break;
					case "tag008all38": tag008all38 = new ControlValue(subfield, value); break;
					case "tag008all39": tag008all39 = new ControlValue(subfield, value); break;

					default:
						logger.severe(String.format("Unhandled 008 subfield: %s", subfield.getId()));
						break;
				}

				valueMap.put(subfield, value);
			} catch (StringIndexOutOfBoundsException e) {
				logger.severe(content.length() + " " + subfield.getPositionStart() + "-" + subfield.getPositionEnd());
			}
		}

		Control008Type actual = Control008Type.byCode(recordType.getValue().toString());
		for (ControlSubfield subfield : Control008Subfields.get(actual)) {
			int end = Math.min(content.length(), subfield.getPositionEnd());
			try {

				String value = content.substring(subfield.getPositionStart(), end);

				switch (subfield.getId()) {
					case "tag008book18": tag008book18 = new ControlValue(subfield, value); break;
					case "tag008book22": tag008book22 = new ControlValue(subfield, value); break;
					case "tag008book23": tag008book23 = new ControlValue(subfield, value); break;
					case "tag008book24": tag008book24 = new ControlValue(subfield, value); break;
					case "tag008book28": tag008book28 = new ControlValue(subfield, value); break;
					case "tag008book29": tag008book29 = new ControlValue(subfield, value); break;
					case "tag008book30": tag008book30 = new ControlValue(subfield, value); break;
					case "tag008book31": tag008book31 = new ControlValue(subfield, value); break;
					case "tag008book33": tag008book33 = new ControlValue(subfield, value); break;
					case "tag008book34": tag008book34 = new ControlValue(subfield, value); break;

					case "tag008computer22": tag008computer22 = new ControlValue(subfield, value); break;
					case "tag008computer23": tag008computer23 = new ControlValue(subfield, value); break;
					case "tag008computer26": tag008computer26 = new ControlValue(subfield, value); break;
					case "tag008computer28": tag008computer28 = new ControlValue(subfield, value); break;

					case "tag008map18": tag008map18 = new ControlValue(subfield, value); break;
					case "tag008map22": tag008map22 = new ControlValue(subfield, value); break;
					case "tag008map25": tag008map25 = new ControlValue(subfield, value); break;
					case "tag008map28": tag008map28 = new ControlValue(subfield, value); break;
					case "tag008map29": tag008map29 = new ControlValue(subfield, value); break;
					case "tag008map31": tag008map31 = new ControlValue(subfield, value); break;
					case "tag008map33": tag008map33 = new ControlValue(subfield, value); break;

					case "tag008music18": tag008music18 = new ControlValue(subfield, value); break;
					case "tag008music20": tag008music20 = new ControlValue(subfield, value); break;
					case "tag008music21": tag008music21 = new ControlValue(subfield, value); break;
					case "tag008music22": tag008music22 = new ControlValue(subfield, value); break;
					case "tag008music23": tag008music23 = new ControlValue(subfield, value); break;
					case "tag008music24": tag008music24 = new ControlValue(subfield, value); break;
					case "tag008music30": tag008music30 = new ControlValue(subfield, value); break;
					case "tag008music33": tag008music33 = new ControlValue(subfield, value); break;

					case "tag008continuing18": tag008continuing18 = new ControlValue(subfield, value); break;
					case "tag008continuing19": tag008continuing19 = new ControlValue(subfield, value); break;
					case "tag008continuing21": tag008continuing21 = new ControlValue(subfield, value); break;
					case "tag008continuing22": tag008continuing22 = new ControlValue(subfield, value); break;
					case "tag008continuing23": tag008continuing23 = new ControlValue(subfield, value); break;
					case "tag008continuing24": tag008continuing24 = new ControlValue(subfield, value); break;
					case "tag008continuing25": tag008continuing25 = new ControlValue(subfield, value); break;
					case "tag008continuing28": tag008continuing28 = new ControlValue(subfield, value); break;
					case "tag008continuing29": tag008continuing29 = new ControlValue(subfield, value); break;
					case "tag008continuing33": tag008continuing33 = new ControlValue(subfield, value); break;
					case "tag008continuing34": tag008continuing34 = new ControlValue(subfield, value); break;

					case "tag008visual18": tag008visual18 = new ControlValue(subfield, value); break;
					case "tag008visual22": tag008visual22 = new ControlValue(subfield, value); break;
					case "tag008visual28": tag008visual28 = new ControlValue(subfield, value); break;
					case "tag008visual29": tag008visual29 = new ControlValue(subfield, value); break;
					case "tag008visual33": tag008visual33 = new ControlValue(subfield, value); break;
					case "tag008visual34": tag008visual34 = new ControlValue(subfield, value); break;

					case "tag008mixed23": tag008mixed23 = new ControlValue(subfield, value); break;

					default:
						logger.severe(String.format("Unhandled 008 subfield: %s", subfield.getId()));
						break;
				}

				valueMap.put(subfield, value);
			} catch (StringIndexOutOfBoundsException e) {
				logger.severe(content.length() + " " + subfield.getPositionStart() + "-" + subfield.getPositionEnd());
			}
		}
	}

	public String resolve(ControlSubfield key) {
		String value = (String)valueMap.get(key);
		String text = key.resolve(value);
		return text;
	}

	public Map<ControlSubfield, Object> getValueMap() {
		return valueMap;
	}
}
