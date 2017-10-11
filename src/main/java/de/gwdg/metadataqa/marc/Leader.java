package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.LeaderSubfields;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Leader implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(Leader.class.getCanonicalName());

	private String mqTag = "Leader";

	public enum Type {
		BOOKS("Books"),
		CONTINUING_RESOURCES("Continuing Resources"),
		MUSIC("Music"),
		MAPS("Maps"),
		VISUAL_MATERIALS("Visual Materials"),
		COMPUTER_FILES("Computer Files"),
		MIXED_MATERIALS("Mixed Materials");

		String value;
		Type(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	};

	private Type type;
	private String content;
	// private Map<String, Object> map;
	private Map<ControlSubfield, String> valuesMap;
	private ControlValue recordLength;
	private ControlValue recordStatus;
	private ControlValue typeOfRecord;
	private ControlValue bibliographicLevel;
	private ControlValue typeOfControl;
	private ControlValue characterCodingScheme;
	private ControlValue indicatorCount;
	private ControlValue subfieldCodeCount;
	private ControlValue baseAddressOfData;
	private ControlValue encodingLevel;
	private ControlValue descriptiveCatalogingForm;
	private ControlValue multipartResourceRecordLevel;
	private ControlValue lengthOfTheLengthOfFieldPortion;
	private ControlValue lengthOfTheStartingCharacterPositionPortion;
	private ControlValue lengthOfTheImplementationDefinedPortion;

	public Leader(String content) {
		this.content = content;
		valuesMap = new LinkedHashMap<>();
		process();
		setType();
	}

	private void process() {
		for (ControlSubfield subfield : LeaderSubfields.getSubfields()) {
			int end = Math.min(content.length(), subfield.getPositionEnd());
			try {
				String value = content.substring(subfield.getPositionStart(), end);
				switch (subfield.getId()) {
					case "leader01": recordLength = new ControlValue(subfield, value); break;
					case "leader05": recordStatus = new ControlValue(subfield, value); break;
					case "leader06": typeOfRecord = new ControlValue(subfield, value); break;
					case "leader07": bibliographicLevel = new ControlValue(subfield, value); break;
					case "leader08": typeOfControl = new ControlValue(subfield, value); break;
					case "leader09": characterCodingScheme = new ControlValue(subfield, value); break;
					case "leader10": indicatorCount = new ControlValue(subfield, value); break;
					case "leader11": subfieldCodeCount = new ControlValue(subfield, value); break;
					case "leader12": baseAddressOfData = new ControlValue(subfield, value); break;
					case "leader17": encodingLevel = new ControlValue(subfield, value); break;
					case "leader18": descriptiveCatalogingForm = new ControlValue(subfield, value); break;
					case "leader19": multipartResourceRecordLevel = new ControlValue(subfield, value); break;
					case "leader20": lengthOfTheLengthOfFieldPortion = new ControlValue(subfield, value); break;
					case "leader21": lengthOfTheStartingCharacterPositionPortion = new ControlValue(subfield, value); break;
					case "leader22": lengthOfTheImplementationDefinedPortion = new ControlValue(subfield, value); break;
					default:
						break;
				}
				valuesMap.put(subfield, value);
			} catch (StringIndexOutOfBoundsException e) {
				logger.severe(String.format("Problem with processing Leader ('%s'). " +
						"The content length is only %d while reading position @%d-%d (for %s)",
					content,
					content.length(), subfield.getPositionStart(), subfield.getPositionEnd(), subfield.getLabel()));
			}
		}
	}

	private void setType() {
		// typeOfRecord = valuesMap.get(LeaderSubfields.getByLabel("Type of record"));
		// bibliographicLevel = valuesMap.get(LeaderSubfields.getByLabel("Bibliographic level"));
		if (typeOfRecord.getValue().equals("a") && bibliographicLevel.getValue().matches("^(a|c|d|m)$")) {
			type = Type.BOOKS;
		} else if (typeOfRecord.getValue().equals("a") && bibliographicLevel.getValue().matches("^(b|i|s)$")) {
			type = Type.CONTINUING_RESOURCES;
		} else if (typeOfRecord.getValue().equals("t")) {
			type = Type.BOOKS;
		} else if (typeOfRecord.getValue().matches("^[cdij]$")) {
			type = Type.MUSIC;
		} else if (typeOfRecord.getValue().matches("^[ef]$")) {
			type = Type.MAPS;
		} else if (typeOfRecord.getValue().matches("^[gkor]$")) {
			type = Type.VISUAL_MATERIALS;
		} else if (typeOfRecord.getValue().equals("m")) {
			type = Type.COMPUTER_FILES;
		} else if (typeOfRecord.getValue().equals("p")) {
			type = Type.MIXED_MATERIALS;
		}
	}

	public String resolve(ControlSubfield key) {
		String value = valuesMap.get(key);
		String text = key.resolve(value);
		return text;
	}

	public String resolve(String key) {
		return resolve(LeaderSubfields.getByLabel(key));
	}

	public Map<ControlSubfield, String> getMap() {
		return valuesMap;
	}

	public String get(ControlSubfield key) {
		return valuesMap.get(key);
	}

	public String getByLabel(String key) {
		return get(LeaderSubfields.getByLabel(key));
	}
	public String getById(String key) {
		return get(LeaderSubfields.getById(key));
	}

	/**
	 * Return Tpye
	 * @return
	 */
	public Type getType() {
		return type;
	}

	public String getLeaderString() {
		return content;
	}

	public ControlValue getRecordLength() {
		return recordLength;
	}

	public ControlValue getRecordStatus() {
		return recordStatus;
	}

	public ControlValue getTypeOfRecord() {
		return typeOfRecord;
	}

	public ControlValue getBibliographicLevel() {
		return bibliographicLevel;
	}

	public ControlValue getTypeOfControl() {
		return typeOfControl;
	}

	public ControlValue getCharacterCodingScheme() {
		return characterCodingScheme;
	}

	public ControlValue getIndicatorCount() {
		return indicatorCount;
	}

	public ControlValue getSubfieldCodeCount() {
		return subfieldCodeCount;
	}

	public ControlValue getBaseAddressOfData() {
		return baseAddressOfData;
	}

	public ControlValue getEncodingLevel() {
		return encodingLevel;
	}

	public ControlValue getDescriptiveCatalogingForm() {
		return descriptiveCatalogingForm;
	}

	public ControlValue getMultipartResourceRecordLevel() {
		return multipartResourceRecordLevel;
	}

	public ControlValue getLengthOfTheLengthOfFieldPortion() {
		return lengthOfTheLengthOfFieldPortion;
	}

	public ControlValue getLengthOfTheStartingCharacterPositionPortion() {
		return lengthOfTheStartingCharacterPositionPortion;
	}

	public ControlValue getLengthOfTheImplementationDefinedPortion() {
		return lengthOfTheImplementationDefinedPortion;
	}

	public String toString() {
		String output = String.format( "type: %s\n", type.getValue());
		for (ControlSubfield key : LeaderSubfields.getSubfields()) {
			output += String.format("%s: %s\n", key.getLabel(), resolve(key));
		}
		return output;
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
		Map<String, List<String>> map = new LinkedHashMap<>();
		map.put(mqTag, Arrays.asList(content));
		for (ControlSubfield controlSubfield : valuesMap.keySet()) {
			String code = controlSubfield.getMqTag() != null
				? controlSubfield.getMqTag()
				: controlSubfield.getId();
			String key = String.format("%s_%s", mqTag, code);
			String value = controlSubfield.resolve(valuesMap.get(controlSubfield));
			map.put(key, Arrays.asList(value));
		}
		return map;
	}

	@Override
	public boolean validate() {
		return false;
	}

	@Override
	public List<String> getErrors() {
		return null;
	}
}
