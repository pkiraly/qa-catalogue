package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.Control007Category;
import de.gwdg.metadataqa.marc.definition.Control007Subfields;
import de.gwdg.metadataqa.marc.definition.ControlSubfield;
import de.gwdg.metadataqa.marc.definition.ControlValue;

import java.util.*;
import java.util.logging.Logger;

/**
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class Control007 implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(Control007.class.getCanonicalName());

	private static final String label = "Physical Description";
	private static final String mqTag = "PhysicalDescription";

	private String content;
	private String categoryOfMaterial;
	private Control007Category category;

	private ControlValue common;

	private ControlValue tag007map00;
	private ControlValue tag007map01;
	private ControlValue tag007map03;
	private ControlValue tag007map04;
	private ControlValue tag007map05;
	private ControlValue tag007map06;
	private ControlValue tag007map07;

	private ControlValue tag007electro00;
	private ControlValue tag007electro01;
	private ControlValue tag007electro03;
	private ControlValue tag007electro04;
	private ControlValue tag007electro05;
	private ControlValue tag007electro06;
	private ControlValue tag007electro09;
	private ControlValue tag007electro10;
	private ControlValue tag007electro11;
	private ControlValue tag007electro12;
	private ControlValue tag007electro13;

	private ControlValue tag007globe00;
	private ControlValue tag007globe01;
	private ControlValue tag007globe03;
	private ControlValue tag007globe04;
	private ControlValue tag007globe05;

	private ControlValue tag007tactile00;
	private ControlValue tag007tactile01;
	private ControlValue tag007tactile03;
	private ControlValue tag007tactile05;
	private ControlValue tag007tactile06;
	private ControlValue tag007tactile09;

	private ControlValue tag007projected00;
	private ControlValue tag007projected01;
	private ControlValue tag007projected03;
	private ControlValue tag007projected04;
	private ControlValue tag007projected05;
	private ControlValue tag007projected06;
	private ControlValue tag007projected07;
	private ControlValue tag007projected08;

	private ControlValue tag007microform00;
	private ControlValue tag007microform01;
	private ControlValue tag007microform03;
	private ControlValue tag007microform04;
	private ControlValue tag007microform05;
	private ControlValue tag007microform06;
	private ControlValue tag007microform09;
	private ControlValue tag007microform10;
	private ControlValue tag007microform11;
	private ControlValue tag007microform12;

	private ControlValue tag007nonprojected00;
	private ControlValue tag007nonprojected01;
	private ControlValue tag007nonprojected03;
	private ControlValue tag007nonprojected04;
	private ControlValue tag007nonprojected05;

	private ControlValue tag007motionPicture00;
	private ControlValue tag007motionPicture01;
	private ControlValue tag007motionPicture03;
	private ControlValue tag007motionPicture04;
	private ControlValue tag007motionPicture05;
	private ControlValue tag007motionPicture06;
	private ControlValue tag007motionPicture07;
	private ControlValue tag007motionPicture08;
	private ControlValue tag007motionPicture09;
	private ControlValue tag007motionPicture10;
	private ControlValue tag007motionPicture11;
	private ControlValue tag007motionPicture12;
	private ControlValue tag007motionPicture13;
	private ControlValue tag007motionPicture14;
	private ControlValue tag007motionPicture15;
	private ControlValue tag007motionPicture16;
	private ControlValue tag007motionPicture17;

	private ControlValue tag007kit00;
	private ControlValue tag007kit01;

	private ControlValue tag007music00;
	private ControlValue tag007music01;

	private ControlValue tag007remoteSensing00;
	private ControlValue tag007remoteSensing01;
	private ControlValue tag007remoteSensing03;
	private ControlValue tag007remoteSensing04;
	private ControlValue tag007remoteSensing05;
	private ControlValue tag007remoteSensing06;
	private ControlValue tag007remoteSensing07;
	private ControlValue tag007remoteSensing08;
	private ControlValue tag007remoteSensing09;

	private ControlValue tag007soundRecording00;
	private ControlValue tag007soundRecording01;
	private ControlValue tag007soundRecording03;
	private ControlValue tag007soundRecording04;
	private ControlValue tag007soundRecording05;
	private ControlValue tag007soundRecording06;
	private ControlValue tag007soundRecording07;
	private ControlValue tag007soundRecording08;
	private ControlValue tag007soundRecording09;
	private ControlValue tag007soundRecording10;
	private ControlValue tag007soundRecording11;
	private ControlValue tag007soundRecording12;
	private ControlValue tag007soundRecording13;

	private ControlValue tag007text00;
	private ControlValue tag007text01;

	private ControlValue tag007video00;
	private ControlValue tag007video01;
	private ControlValue tag007video03;
	private ControlValue tag007video04;
	private ControlValue tag007video05;
	private ControlValue tag007video06;
	private ControlValue tag007video07;
	private ControlValue tag007video08;

	private ControlValue tag007unspecified00;
	private ControlValue tag007unspecified01;

	private Map<ControlSubfield, String> valuesMap;
	private List<ControlValue> valuesList;
	private Map<Integer, ControlSubfield> byPosition = new LinkedHashMap<>();
	private List<String> errors;

	public Control007(String content) {
		this.content = content;
		valuesMap = new LinkedHashMap<>();
		valuesList = new ArrayList<>();
		if (content != null)
			process();
	}

	private void process() {
		String categoryCode = content.substring(0, 1);
		category = Control007Category.byCode(categoryCode);
		categoryOfMaterial = category.getLabel();

		/*
		ControlSubfield subfieldCommon = Control007Subfields.get(Control007Category.Common).get(0);
		common = new ControlValue(subfieldCommon, category.getLabel());

		valuesMap.put(subfieldCommon, category.getLabel());
		byPosition.put(subfieldCommon.getPositionStart(), subfieldCommon);
		*/

		for (ControlSubfield subfield : Control007Subfields.get(category)) {
			byPosition.put(subfield.getPositionStart(), subfield);
			int end = Math.min(content.length(), subfield.getPositionEnd());

			String value = null;
			if (subfield.getPositionStart() <= content.length()) {
				try {
					value = content.substring(subfield.getPositionStart(), end);
				} catch (StringIndexOutOfBoundsException e) {
					logger.severe(String.format("Problem with processing 007 ('%s'). " +
							"The content length is only %d while reading position @%d-%d" +
							" (for %s '%s')",
						content,
						content.length(), subfield.getPositionStart(), subfield.getPositionEnd(),
						subfield.getId(), subfield.getLabel()));
				}
			} else {
				logger.severe(String.format("Problem with processing 007 ('%s'). " +
						"The content length is only %d while reading position @%d-%d" +
						" (for %s '%s')",
					content,
					content.length(), subfield.getPositionStart(), subfield.getPositionEnd(),
					subfield.getId(), subfield.getLabel()));
				if (subfield.getDefaultCode() != null) {
					value = subfield.getDefaultCode();
					logger.warning(String.format("Applying default value: '%s'. ", subfield.getDefaultCode()));
				} else {
					value = "";
				}
			}

			ControlValue controlValue = new ControlValue(subfield, value);
			valuesList.add(controlValue);

			switch (subfield.getId()) {
				case "tag007map00": tag007map00 = controlValue; break;
				case "tag007map01": tag007map01 = controlValue; break;
				case "tag007map03": tag007map03 = controlValue; break;
				case "tag007map04": tag007map04 = controlValue; break;
				case "tag007map05": tag007map05 = controlValue; break;
				case "tag007map06": tag007map06 = controlValue; break;
				case "tag007map07": tag007map07 = controlValue; break;

				case "tag007electro00": tag007electro00 = controlValue; break;
				case "tag007electro01": tag007electro01 = controlValue; break;
				case "tag007electro03": tag007electro03 = controlValue; break;
				case "tag007electro04": tag007electro04 = controlValue; break;
				case "tag007electro05": tag007electro05 = controlValue; break;
				case "tag007electro06": tag007electro06 = controlValue; break;
				case "tag007electro09": tag007electro09 = controlValue; break;
				case "tag007electro10": tag007electro10 = controlValue; break;
				case "tag007electro11": tag007electro11 = controlValue; break;
				case "tag007electro12": tag007electro12 = controlValue; break;
				case "tag007electro13": tag007electro13 = controlValue; break;

				case "tag007globe00": tag007globe00 = controlValue; break;
				case "tag007globe01": tag007globe01 = controlValue; break;
				case "tag007globe03": tag007globe03 = controlValue; break;
				case "tag007globe04": tag007globe04 = controlValue; break;
				case "tag007globe05": tag007globe05 = controlValue; break;

				case "tag007tactile00": tag007tactile00 = controlValue; break;
				case "tag007tactile01": tag007tactile01 = controlValue; break;
				case "tag007tactile03": tag007tactile03 = controlValue; break;
				case "tag007tactile05": tag007tactile05 = controlValue; break;
				case "tag007tactile06": tag007tactile06 = controlValue; break;
				case "tag007tactile09": tag007tactile09 = controlValue; break;

				case "tag007projected00": tag007projected00 = controlValue; break;
				case "tag007projected01": tag007projected01 = controlValue; break;
				case "tag007projected03": tag007projected03 = controlValue; break;
				case "tag007projected04": tag007projected04 = controlValue; break;
				case "tag007projected05": tag007projected05 = controlValue; break;
				case "tag007projected06": tag007projected06 = controlValue; break;
				case "tag007projected07": tag007projected07 = controlValue; break;
				case "tag007projected08": tag007projected08 = controlValue; break;

				case "tag007microform00": tag007microform00 = controlValue; break;
				case "tag007microform01": tag007microform01 = controlValue; break;
				case "tag007microform03": tag007microform03 = controlValue; break;
				case "tag007microform04": tag007microform04 = controlValue; break;
				case "tag007microform05": tag007microform05 = controlValue; break;
				case "tag007microform06": tag007microform06 = controlValue; break;
				case "tag007microform09": tag007microform09 = controlValue; break;
				case "tag007microform10": tag007microform10 = controlValue; break;
				case "tag007microform11": tag007microform11 = controlValue; break;
				case "tag007microform12": tag007microform12 = controlValue; break;

				case "tag007nonprojected00": tag007nonprojected00 = controlValue; break;
				case "tag007nonprojected01": tag007nonprojected01 = controlValue; break;
				case "tag007nonprojected03": tag007nonprojected03 = controlValue; break;
				case "tag007nonprojected04": tag007nonprojected04 = controlValue; break;
				case "tag007nonprojected05": tag007nonprojected05 = controlValue; break;

				case "tag007motionPicture00": tag007motionPicture00 = controlValue; break;
				case "tag007motionPicture01": tag007motionPicture01 = controlValue; break;
				case "tag007motionPicture03": tag007motionPicture03 = controlValue; break;
				case "tag007motionPicture04": tag007motionPicture04 = controlValue; break;
				case "tag007motionPicture05": tag007motionPicture05 = controlValue; break;
				case "tag007motionPicture06": tag007motionPicture06 = controlValue; break;
				case "tag007motionPicture07": tag007motionPicture07 = controlValue; break;
				case "tag007motionPicture08": tag007motionPicture08 = controlValue; break;
				case "tag007motionPicture09": tag007motionPicture09 = controlValue; break;
				case "tag007motionPicture10": tag007motionPicture10 = controlValue; break;
				case "tag007motionPicture11": tag007motionPicture11 = controlValue; break;
				case "tag007motionPicture12": tag007motionPicture12 = controlValue; break;
				case "tag007motionPicture13": tag007motionPicture13 = controlValue; break;
				case "tag007motionPicture14": tag007motionPicture14 = controlValue; break;
				case "tag007motionPicture15": tag007motionPicture15 = controlValue; break;
				case "tag007motionPicture16": tag007motionPicture16 = controlValue; break;
				case "tag007motionPicture17": tag007motionPicture17 = controlValue; break;

				case "tag007kit00": tag007kit00 = controlValue; break;
				case "tag007kit01": tag007kit01 = controlValue; break;

				case "tag007music00": tag007music00 = controlValue; break;
				case "tag007music01": tag007music01 = controlValue; break;

				case "tag007remoteSensing00": tag007remoteSensing00 = controlValue; break;
				case "tag007remoteSensing01": tag007remoteSensing01 = controlValue; break;
				case "tag007remoteSensing03": tag007remoteSensing03 = controlValue; break;
				case "tag007remoteSensing04": tag007remoteSensing04 = controlValue; break;
				case "tag007remoteSensing05": tag007remoteSensing05 = controlValue; break;
				case "tag007remoteSensing06": tag007remoteSensing06 = controlValue; break;
				case "tag007remoteSensing07": tag007remoteSensing07 = controlValue; break;
				case "tag007remoteSensing08": tag007remoteSensing08 = controlValue; break;
				case "tag007remoteSensing09": tag007remoteSensing09 = controlValue; break;

				case "tag007soundRecording00": tag007soundRecording00 = controlValue; break;
				case "tag007soundRecording01": tag007soundRecording01 = controlValue; break;
				case "tag007soundRecording03": tag007soundRecording03 = controlValue; break;
				case "tag007soundRecording04": tag007soundRecording04 = controlValue; break;
				case "tag007soundRecording05": tag007soundRecording05 = controlValue; break;
				case "tag007soundRecording06": tag007soundRecording06 = controlValue; break;
				case "tag007soundRecording07": tag007soundRecording07 = controlValue; break;
				case "tag007soundRecording08": tag007soundRecording08 = controlValue; break;
				case "tag007soundRecording09": tag007soundRecording09 = controlValue; break;
				case "tag007soundRecording10": tag007soundRecording10 = controlValue; break;
				case "tag007soundRecording11": tag007soundRecording11 = controlValue; break;
				case "tag007soundRecording12": tag007soundRecording12 = controlValue; break;
				case "tag007soundRecording13": tag007soundRecording13 = controlValue; break;

				case "tag007text00": tag007text00 = controlValue; break;
				case "tag007text01": tag007text01 = controlValue; break;

				case "tag007video00": tag007video00 = controlValue; break;
				case "tag007video01": tag007video01 = controlValue; break;
				case "tag007video03": tag007video03 = controlValue; break;
				case "tag007video04": tag007video04 = controlValue; break;
				case "tag007video05": tag007video05 = controlValue; break;
				case "tag007video06": tag007video06 = controlValue; break;
				case "tag007video07": tag007video07 = controlValue; break;
				case "tag007video08": tag007video08 = controlValue; break;

				case "tag007unspecified00": tag007unspecified00 = controlValue; break;
				case "tag007unspecified01": tag007unspecified01 = controlValue; break;

				default:
					logger.severe(String.format("Unhandled 007 subfield: %s", subfield.getId()));
					break;
			}

			valuesMap.put(subfield, value);
		}
	}

	public String resolve(ControlSubfield key) {
		String value = (String)valuesMap.get(key);
		String text = key.resolve(value);
		return text;
	}

	public String getContent() {
		return content;
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

	public String getCategoryOfMaterial() {
		return categoryOfMaterial;
	}

	public Control007Category getCategory() {
		return category;
	}

	public ControlValue getMap01() {
		return tag007map01;
	}

	public ControlValue getMap03() {
		return tag007map03;
	}

	public ControlValue getMap04() {
		return tag007map04;
	}

	public ControlValue getMap05() {
		return tag007map05;
	}

	public ControlValue getMap06() {
		return tag007map06;
	}

	public ControlValue getMap07() {
		return tag007map07;
	}

	public ControlValue getElectro01() {
		return tag007electro01;
	}

	public ControlValue getElectro03() {
		return tag007electro03;
	}

	public ControlValue getElectro04() {
		return tag007electro04;
	}

	public ControlValue getElectro05() {
		return tag007electro05;
	}

	public ControlValue getElectro06() {
		return tag007electro06;
	}

	public ControlValue getElectro09() {
		return tag007electro09;
	}

	public ControlValue getElectro10() {
		return tag007electro10;
	}

	public ControlValue getElectro11() {
		return tag007electro11;
	}

	public ControlValue getElectro12() {
		return tag007electro12;
	}

	public ControlValue getElectro13() {
		return tag007electro13;
	}

	public ControlValue getGlobe01() {
		return tag007globe01;
	}

	public ControlValue getGlobe03() {
		return tag007globe03;
	}

	public ControlValue getGlobe04() {
		return tag007globe04;
	}

	public ControlValue getGlobe05() {
		return tag007globe05;
	}

	public ControlValue getTactile0101() {
		return tag007tactile01;
	}

	public ControlValue getTactile0103() {
		return tag007tactile03;
	}

	public ControlValue getTactile0105() {
		return tag007tactile05;
	}

	public ControlValue getTactile0106() {
		return tag007tactile06;
	}

	public ControlValue getTactile0109() {
		return tag007tactile09;
	}

	public ControlValue getProjected01() {
		return tag007projected01;
	}

	public ControlValue getProjected03() {
		return tag007projected03;
	}

	public ControlValue getProjected04() {
		return tag007projected04;
	}

	public ControlValue getProjected05() {
		return tag007projected05;
	}

	public ControlValue getProjected06() {
		return tag007projected06;
	}

	public ControlValue getProjected07() {
		return tag007projected07;
	}

	public ControlValue getProjected08() {
		return tag007projected08;
	}

	public ControlValue getMicroform01() {
		return tag007microform01;
	}

	public ControlValue getMicroform03() {
		return tag007microform03;
	}

	public ControlValue getMicroform04() {
		return tag007microform04;
	}

	public ControlValue getMicroform05() {
		return tag007microform05;
	}

	public ControlValue getMicroform06() {
		return tag007microform06;
	}

	public ControlValue getMicroform09() {
		return tag007microform09;
	}

	public ControlValue getMicroform10() {
		return tag007microform10;
	}

	public ControlValue getMicroform11() {
		return tag007microform11;
	}

	public ControlValue getMicroform12() {
		return tag007microform12;
	}

	public ControlValue getNonprojected01() {
		return tag007nonprojected01;
	}

	public ControlValue getNonprojected03() {
		return tag007nonprojected03;
	}

	public ControlValue getNonprojected04() {
		return tag007nonprojected04;
	}

	public ControlValue getNonprojected05() {
		return tag007nonprojected05;
	}

	public ControlValue getMotionPicture01() {
		return tag007motionPicture01;
	}

	public ControlValue getMotionPicture03() {
		return tag007motionPicture03;
	}

	public ControlValue getMotionPicture04() {
		return tag007motionPicture04;
	}

	public ControlValue getMotionPicture05() {
		return tag007motionPicture05;
	}

	public ControlValue getMotionPicture06() {
		return tag007motionPicture06;
	}

	public ControlValue getMotionPicture07() {
		return tag007motionPicture07;
	}

	public ControlValue getMotionPicture08() {
		return tag007motionPicture08;
	}

	public ControlValue getMotionPicture09() {
		return tag007motionPicture09;
	}

	public ControlValue getMotionPicture10() {
		return tag007motionPicture10;
	}

	public ControlValue getMotionPicture11() {
		return tag007motionPicture11;
	}

	public ControlValue getMotionPicture12() {
		return tag007motionPicture12;
	}

	public ControlValue getMotionPicture13() {
		return tag007motionPicture13;
	}

	public ControlValue getMotionPicture14() {
		return tag007motionPicture14;
	}

	public ControlValue getMotionPicture15() {
		return tag007motionPicture15;
	}

	public ControlValue getMotionPicture16() {
		return tag007motionPicture16;
	}

	public ControlValue getMotionPicture17() {
		return tag007motionPicture17;
	}

	public ControlValue getKit01() {
		return tag007kit01;
	}

	public ControlValue getMusic01() {
		return tag007music01;
	}

	public ControlValue getRemoteSensing01() {
		return tag007remoteSensing01;
	}

	public ControlValue getRemoteSensing03() {
		return tag007remoteSensing03;
	}

	public ControlValue getRemoteSensing04() {
		return tag007remoteSensing04;
	}

	public ControlValue getRemoteSensing05() {
		return tag007remoteSensing05;
	}

	public ControlValue getRemoteSensing06() {
		return tag007remoteSensing06;
	}

	public ControlValue getRemoteSensing07() {
		return tag007remoteSensing07;
	}

	public ControlValue getRemoteSensing08() {
		return tag007remoteSensing08;
	}

	public ControlValue getRemoteSensing09() {
		return tag007remoteSensing09;
	}

	public ControlValue getSoundRecording01() {
		return tag007soundRecording01;
	}

	public ControlValue getSoundRecording03() {
		return tag007soundRecording03;
	}

	public ControlValue getSoundRecording04() {
		return tag007soundRecording04;
	}

	public ControlValue getSoundRecording05() {
		return tag007soundRecording05;
	}

	public ControlValue getSoundRecording06() {
		return tag007soundRecording06;
	}

	public ControlValue getSoundRecording07() {
		return tag007soundRecording07;
	}

	public ControlValue getSoundRecording08() {
		return tag007soundRecording08;
	}

	public ControlValue getSoundRecording09() {
		return tag007soundRecording09;
	}

	public ControlValue getSoundRecording10() {
		return tag007soundRecording10;
	}

	public ControlValue getSoundRecording11() {
		return tag007soundRecording11;
	}

	public ControlValue getSoundRecording12() {
		return tag007soundRecording12;
	}

	public ControlValue getSoundRecording13() {
		return tag007soundRecording13;
	}

	public ControlValue getText01() {
		return tag007text01;
	}

	public ControlValue getVideo01() {
		return tag007video01;
	}

	public ControlValue getVideo03() {
		return tag007video03;
	}

	public ControlValue getVideo04() {
		return tag007video04;
	}

	public ControlValue getVideo05() {
		return tag007video05;
	}

	public ControlValue getVideo06() {
		return tag007video06;
	}

	public ControlValue getVideo07() {
		return tag007video07;
	}

	public ControlValue getVideo08() {
		return tag007video08;
	}

	public ControlValue getUnspecified01() {
		return tag007unspecified01;
	}

	@Override
	public String toString() {
		return "Control007{" +
				"content='" + content + '\'' +
				", categoryOfMaterial='" + categoryOfMaterial + '\'' +
				", category=" + category +
				", map=" + valuesMap +
				'}';
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

	public static String getLabel() {
		return label;
	}

	public static String getMqTag() {
		return mqTag;
	}

	@Override
	public boolean validate() {
		boolean isValid = true;
		errors = new ArrayList<>();
		for (ControlValue controlValue : valuesList) {
			if (!controlValue.validate()) {
				errors.addAll(controlValue.getErrors());
				isValid = false;
			}
		}
		return isValid;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}
}