package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Control006Subfields;
import de.gwdg.metadataqa.marc.definition.Control008Type;
import de.gwdg.metadataqa.marc.definition.ControlValue;

import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control006 implements Extractable {

	private static final Logger logger = Logger.getLogger(Control006.class.getCanonicalName());

	private static final String label = "Additional Material Characteristics";
	private static final String mqTag = "AdditionalMaterialCharacteristics";

	private String content;
	private Leader.Type recordType;

	private ControlValue tag006all00;

	private ControlValue tag006book01;
	private ControlValue tag006book05;
	private ControlValue tag006book06;
	private ControlValue tag006book07;
	private ControlValue tag006book11;
	private ControlValue tag006book12;
	private ControlValue tag006book13;
	private ControlValue tag006book14;
	private ControlValue tag006book16;
	private ControlValue tag006book17;

	private ControlValue tag006computer05;
	private ControlValue tag006computer06;
	private ControlValue tag006computer09;
	private ControlValue tag006computer11;

	private ControlValue tag006map01;
	private ControlValue tag006map05;
	private ControlValue tag006map08;
	private ControlValue tag006map11;
	private ControlValue tag006map12;
	private ControlValue tag006map14;
	private ControlValue tag006map16;

	private ControlValue tag006music01;
	private ControlValue tag006music03;
	private ControlValue tag006music04;
	private ControlValue tag006music05;
	private ControlValue tag006music06;
	private ControlValue tag006music07;
	private ControlValue tag006music13;
	private ControlValue tag006music16;

	private ControlValue tag006continuing01;
	private ControlValue tag006continuing02;
	private ControlValue tag006continuing04;
	private ControlValue tag006continuing05;
	private ControlValue tag006continuing06;
	private ControlValue tag006continuing07;
	private ControlValue tag006continuing08;
	private ControlValue tag006continuing11;
	private ControlValue tag006continuing12;
	private ControlValue tag006continuing16;
	private ControlValue tag006continuing17;

	private ControlValue tag006visual01;
	private ControlValue tag006visual05;
	private ControlValue tag006visual11;
	private ControlValue tag006visual12;
	private ControlValue tag006visual16;
	private ControlValue tag006visual17;

	private ControlValue tag006mixed06;

	private Map<ControlSubfield, String> valuesMap;
	private Map<Integer, ControlSubfield> byPosition = new LinkedHashMap<>();

	public Control006(String content, Leader.Type recordType) {
		this.content = content;
		this.recordType = recordType;
		valuesMap = new LinkedHashMap<>();
		if (content != null)
			process();
	}

	private void process() {
		for (ControlSubfield subfield : Control006Subfields.get(Control008Type.ALL_MATERIALS)) {
			int end = Math.min(content.length(), subfield.getPositionEnd());
			if (end < 0) {
				logger.severe(content.length() + " " + subfield.getPositionEnd());
			}
			try {
				String value = content.substring(subfield.getPositionStart(), end);

				switch (subfield.getId()) {
					case "tag006all00": tag006all00 = new ControlValue(subfield, value); break;

					default:
						logger.severe(String.format("Unhandled 006 subfield: %s", subfield.getId()));
						break;
				}

				valuesMap.put(subfield, value);
				byPosition.put(subfield.getPositionStart(), subfield);
			} catch (StringIndexOutOfBoundsException e) {
				logger.severe(content.length() + " " + subfield.getPositionStart() + "-" + subfield.getPositionEnd());
			}
		}

		Control008Type actual = Control008Type.byCode(recordType.getValue().toString());
		for (ControlSubfield subfield : Control006Subfields.get(actual)) {
			int end = Math.min(content.length(), subfield.getPositionEnd());
			try {

				String value = content.substring(subfield.getPositionStart(), end);

				switch (subfield.getId()) {
					case "tag006book01": tag006book01 = new ControlValue(subfield, value); break;
					case "tag006book05": tag006book05 = new ControlValue(subfield, value); break;
					case "tag006book06": tag006book06 = new ControlValue(subfield, value); break;
					case "tag006book07": tag006book07 = new ControlValue(subfield, value); break;
					case "tag006book11": tag006book11 = new ControlValue(subfield, value); break;
					case "tag006book12": tag006book12 = new ControlValue(subfield, value); break;
					case "tag006book13": tag006book13 = new ControlValue(subfield, value); break;
					case "tag006book14": tag006book14 = new ControlValue(subfield, value); break;
					case "tag006book16": tag006book16 = new ControlValue(subfield, value); break;
					case "tag006book17": tag006book17 = new ControlValue(subfield, value); break;

					case "tag006computer05": tag006computer05 = new ControlValue(subfield, value); break;
					case "tag006computer06": tag006computer06 = new ControlValue(subfield, value); break;
					case "tag006computer09": tag006computer09 = new ControlValue(subfield, value); break;
					case "tag006computer11": tag006computer11 = new ControlValue(subfield, value); break;

					case "tag006map01": tag006map01 = new ControlValue(subfield, value); break;
					case "tag006map05": tag006map05 = new ControlValue(subfield, value); break;
					case "tag006map08": tag006map08 = new ControlValue(subfield, value); break;
					case "tag006map11": tag006map11 = new ControlValue(subfield, value); break;
					case "tag006map12": tag006map12 = new ControlValue(subfield, value); break;
					case "tag006map14": tag006map14 = new ControlValue(subfield, value); break;
					case "tag006map16": tag006map16 = new ControlValue(subfield, value); break;

					case "tag006music01": tag006music01 = new ControlValue(subfield, value); break;
					case "tag006music03": tag006music03 = new ControlValue(subfield, value); break;
					case "tag006music04": tag006music04 = new ControlValue(subfield, value); break;
					case "tag006music05": tag006music05 = new ControlValue(subfield, value); break;
					case "tag006music06": tag006music06 = new ControlValue(subfield, value); break;
					case "tag006music07": tag006music07 = new ControlValue(subfield, value); break;
					case "tag006music13": tag006music13 = new ControlValue(subfield, value); break;
					case "tag006music16": tag006music16 = new ControlValue(subfield, value); break;

					case "tag006continuing01": tag006continuing01 = new ControlValue(subfield, value); break;
					case "tag006continuing02": tag006continuing02 = new ControlValue(subfield, value); break;
					case "tag006continuing04": tag006continuing04 = new ControlValue(subfield, value); break;
					case "tag006continuing05": tag006continuing05 = new ControlValue(subfield, value); break;
					case "tag006continuing06": tag006continuing06 = new ControlValue(subfield, value); break;
					case "tag006continuing07": tag006continuing07 = new ControlValue(subfield, value); break;
					case "tag006continuing08": tag006continuing08 = new ControlValue(subfield, value); break;
					case "tag006continuing11": tag006continuing11 = new ControlValue(subfield, value); break;
					case "tag006continuing12": tag006continuing12 = new ControlValue(subfield, value); break;
					case "tag006continuing16": tag006continuing16 = new ControlValue(subfield, value); break;
					case "tag006continuing17": tag006continuing17 = new ControlValue(subfield, value); break;

					case "tag006visual01": tag006visual01 = new ControlValue(subfield, value); break;
					case "tag006visual05": tag006visual05 = new ControlValue(subfield, value); break;
					case "tag006visual11": tag006visual11 = new ControlValue(subfield, value); break;
					case "tag006visual12": tag006visual12 = new ControlValue(subfield, value); break;
					case "tag006visual16": tag006visual16 = new ControlValue(subfield, value); break;
					case "tag006visual17": tag006visual17 = new ControlValue(subfield, value); break;

					case "tag006mixed06": tag006mixed06 = new ControlValue(subfield, value); break;

					default:
						logger.severe(String.format("Unhandled 006 subfield: %s", subfield.getId()));
						break;
				}

				valuesMap.put(subfield, value);
				byPosition.put(subfield.getPositionStart(), subfield);
			} catch (StringIndexOutOfBoundsException e) {
				logger.severe(content.length() + " " + subfield.getPositionStart() + "-" + subfield.getPositionEnd());
			}
		}
	}

	public String resolve(ControlSubfield key) {
		String value = (String)valuesMap.get(key);
		String text = key.resolve(value);
		return text;
	}

	public Map<ControlSubfield, String> getMap() {
		return valuesMap;
	}

	public String getValueByPosition(int position) {
		return valuesMap.get(getSubfieldByPosition(position));
	}

	public ControlSubfield getSubfieldByPosition(int position) {
		return byPosition.get(position);
	}

	public Set<Integer> getSubfieldPositions() {
		return byPosition.keySet();
	}

	public Map<ControlSubfield, String> getValueMap() {
		return valuesMap;
	}

	public Leader.Type getRecordType() {
		return recordType;
	}

	public ControlValue getTag008all00() {
		return tag006all00;
	}

	public ControlValue getTag006all00() {
		return tag006all00;
	}

	public ControlValue getTag006book01() {
		return tag006book01;
	}

	public ControlValue getTag006book05() {
		return tag006book05;
	}

	public ControlValue getTag006book06() {
		return tag006book06;
	}

	public ControlValue getTag006book07() {
		return tag006book07;
	}

	public ControlValue getTag006book11() {
		return tag006book11;
	}

	public ControlValue getTag006book12() {
		return tag006book12;
	}

	public ControlValue getTag006book13() {
		return tag006book13;
	}

	public ControlValue getTag006book14() {
		return tag006book14;
	}

	public ControlValue getTag006book16() {
		return tag006book16;
	}

	public ControlValue getTag006book17() {
		return tag006book17;
	}

	public ControlValue getTag006computer05() {
		return tag006computer05;
	}

	public ControlValue getTag006computer06() {
		return tag006computer06;
	}

	public ControlValue getTag006computer09() {
		return tag006computer09;
	}

	public ControlValue getTag006computer11() {
		return tag006computer11;
	}

	public ControlValue getTag006map01() {
		return tag006map01;
	}

	public ControlValue getTag006map05() {
		return tag006map05;
	}

	public ControlValue getTag006map08() {
		return tag006map08;
	}

	public ControlValue getTag006map11() {
		return tag006map11;
	}

	public ControlValue getTag006map12() {
		return tag006map12;
	}

	public ControlValue getTag006map14() {
		return tag006map14;
	}

	public ControlValue getTag006map16() {
		return tag006map16;
	}

	public ControlValue getTag006music01() {
		return tag006music01;
	}

	public ControlValue getTag006music03() {
		return tag006music03;
	}

	public ControlValue getTag006music04() {
		return tag006music04;
	}

	public ControlValue getTag006music05() {
		return tag006music05;
	}

	public ControlValue getTag006music06() {
		return tag006music06;
	}

	public ControlValue getTag006music07() {
		return tag006music07;
	}

	public ControlValue getTag006music13() {
		return tag006music13;
	}

	public ControlValue getTag006music16() {
		return tag006music16;
	}

	public ControlValue getTag006continuing01() {
		return tag006continuing01;
	}

	public ControlValue getTag006continuing02() {
		return tag006continuing02;
	}

	public ControlValue getTag006continuing04() {
		return tag006continuing04;
	}

	public ControlValue getTag006continuing05() {
		return tag006continuing05;
	}

	public ControlValue getTag006continuing06() {
		return tag006continuing06;
	}

	public ControlValue getTag006continuing07() {
		return tag006continuing07;
	}

	public ControlValue getTag006continuing08() {
		return tag006continuing08;
	}

	public ControlValue getTag006continuing11() {
		return tag006continuing11;
	}

	public ControlValue getTag006continuing12() {
		return tag006continuing12;
	}

	public ControlValue getTag006continuing16() {
		return tag006continuing16;
	}

	public ControlValue getTag006continuing17() {
		return tag006continuing17;
	}

	public ControlValue getTag006visual01() {
		return tag006visual01;
	}

	public ControlValue getTag006visual05() {
		return tag006visual05;
	}

	public ControlValue getTag006visual11() {
		return tag006visual11;
	}

	public ControlValue getTag006visual12() {
		return tag006visual12;
	}

	public ControlValue getTag006visual16() {
		return tag006visual16;
	}

	public ControlValue getTag006visual17() {
		return tag006visual17;
	}

	public ControlValue getTag006mixed06() {
		return tag006mixed06;
	}

	@Override
	public Map<String, List<String>> getKeyValuePairs() {
		Map<String, List<String>> map = new LinkedHashMap<>();
		if (content != null) {
			map.put(mqTag, Arrays.asList(content));
			for (ControlSubfield controlSubfield : valuesMap.keySet()) {
				String code = controlSubfield.getMqTag() != null
					? controlSubfield.getMqTag()
					: controlSubfield.getId();
				String key = String.format("%s_%s", mqTag, code);
				String value = controlSubfield.resolve(valuesMap.get(controlSubfield));
				map.put(key, Arrays.asList(value));
			}
		}
		return map;
	}

	public static String getLabel() {
		return label;
	}

	public static String getMqTag() {
		return mqTag;
	}
}
