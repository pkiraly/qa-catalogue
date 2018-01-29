package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.codelist.CodeList;
import de.gwdg.metadataqa.marc.utils.MarcTagLister;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MappingToJson {

	private static boolean exportSubfieldCodes = false;
	private static List<String> nonMarc21TagLibraries = Arrays.asList(
		"oclctags", "fennicatags", "dnbtags", "sztetags", "genttags", "holdings"
	);

	public static void main(String[] args) {
		List<Class<? extends DataFieldDefinition>> tags = MarcTagLister.listTags();

		System.out.println("{");

		System.out.println("\"Leader\":{\"positions\":[");
		List<String> positions = new ArrayList<>();
		for (ControlSubfield subfield : LeaderSubfields.getSubfields()) {
			positions.add(controlSubfieldToJson(subfield));
		}
		System.out.println(StringUtils.join(positions, ",\n"));
		System.out.println("]},");

		System.out.println("\"001\":" + toJson(true, "label", Control001.getLabel()) + ",");
		System.out.println("\"003\":" + toJson(true, "label", Control003.getLabel()) + ",");
		System.out.println("\"005\":" + toJson(true, "label", Control005.getLabel()) + ",");

		System.out.printf("\"006\":{\"label\":\"%s\",\"types\":[\n", Control006.getLabel());
		int i = Control006Subfields.getSubfields().keySet().size();
		for (Control008Type type : Control006Subfields.getSubfields().keySet()) {
			System.out.printf("{\"type\":\"%s\",\"positions\":[\n", type.getValue());
			positions = new ArrayList<>();
			for (ControlSubfield subfield : Control006Subfields.getSubfields().get(type))
				positions.add(controlSubfieldToJson(subfield));
			System.out.println(StringUtils.join(positions, ",\n"));
			System.out.println("]}" + (--i > 0 ? "," : ""));
		}
		System.out.println("]},");

		System.out.printf("\"007\":{\"label\":\"%s\",\"categories\":[\n", Control007.getLabel());
		i = Control007Subfields.getSubfields().keySet().size();
		for (Control007Category category : Control007Subfields.getSubfields().keySet()) {
			System.out.printf("{\"type\":\"%s\",\"positions\":[\n", category.getLabel());
			positions = new ArrayList<>();
			for (ControlSubfield subfield : Control007Subfields.getSubfields().get(category))
				positions.add(controlSubfieldToJson(subfield));
			System.out.println(StringUtils.join(positions, ",\n"));
			System.out.println("]}" + (--i > 0 ? "," : ""));
		}
		System.out.println("]},");

		System.out.printf("\"008\":{\"label\":\"%s\",\"types\":[", Control008.getLabel());
		i = Control008Subfields.getSubfields().keySet().size();
		for (Control008Type type : Control008Subfields.getSubfields().keySet()) {
			System.out.printf("{\"type\":\"%s\",\"positions\":[\n", type.getValue());
			positions = new ArrayList<>();
			for (ControlSubfield subfield : Control008Subfields.getSubfields().get(type))
				positions.add(controlSubfieldToJson(subfield));
			System.out.println(StringUtils.join(positions, ",\n"));
			System.out.println("]}" + (--i > 0 ? "," : ""));
		}
		System.out.println("]},");

		List<String> items = new ArrayList<>();
		for (Class<? extends DataFieldDefinition> tagClass : tags) {
			if (isNonMarc21Tag(tagClass))
				continue;

			Method getInstance;
			DataFieldDefinition tag;
			try {
				getInstance = tagClass.getMethod("getInstance");
				tag = (DataFieldDefinition) getInstance.invoke(tagClass);
				items.add(dataFieldToJson(tag));
			} catch (NoSuchMethodException
			       | IllegalAccessException
			       | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		System.out.println(StringUtils.join(items, ",\n"));
		System.out.println("}");
	}

	private static boolean isNonMarc21Tag(Class<? extends DataFieldDefinition> tagClass) {
		boolean isNonMarc21Tag = false;
		for (String nonCore : nonMarc21TagLibraries) {
			if (tagClass.getCanonicalName().contains(nonCore)) {
				isNonMarc21Tag = true;
				break;
			}
		}
		return isNonMarc21Tag;
	}

	private static String controlSubfieldToJson(ControlSubfield subfield) {
		Map<String, String> values = new LinkedHashMap<>();
		values.put("position", subfield.formatPositon());
		values.put("label", subfield.getLabel());
		values.put("url", subfield.getDescriptionUrl());
		String text = mapToJson(values, false);

		if (subfield.getCodes() != null) {
			List<String> codes = new ArrayList<>();
			for (Code code : subfield.getCodes()) {
				codes.add(toJson(true, "code", code.getCode(), "label", code.getLabel()));
			}
			text += ",\"codes\":[\n" + StringUtils.join(codes, ",\n") + "\n]";
		}
		if (subfield.getHistoricalCodes() != null) {
			List<String> codes = new ArrayList<>();
			for (Code code : subfield.getHistoricalCodes()) {
				codes.add(toJson(true, "code", code.getCode(), "label", code.getLabel()));
			}
			text += ",\"historical-codes\":[\n" + StringUtils.join(codes, ",\n") + "\n]";
		}
		return "{" + text + "}";
	}

	private static String mapToJson(Map<String, String> values, boolean isAUnit) {
		List<String> entries = new ArrayList<>();
		for (Map.Entry value : values.entrySet()) {
			String jsonEntry;
			if (value.getValue().equals("true")
				|| value.getValue().equals("false"))
				jsonEntry = String.format("\"%s\":%s", value.getKey(), value.getValue());
			else
				jsonEntry = String.format("\"%s\":\"%s\"", value.getKey(), value.getValue());
			entries.add(jsonEntry);
		}
		if (isAUnit)
			return '{' + StringUtils.join(entries, ",") + '}';
		else
			return StringUtils.join(entries, ",");
	}

	private static String dataFieldToJson(DataFieldDefinition tag) {
		String text = "\"" + tag.getTag() + "\":{" + toJson(false,
			"label", tag.getLabel(),
			"url", tag.getDescriptionUrl(),
			"repeatable", (tag.getCardinality().getCode().equals("R") ? "false" : "true")
		);
		if (tag.getInd1().exists() || tag.getInd2().exists()) {
			text += ",\"indicators\":{";

			List<String> indicators = new ArrayList<>();
			if (tag.getInd1().exists())
				indicators.add(indicatorToJson("ind1", tag.getInd1()));

			if (tag.getInd2().exists())
				indicators.add(indicatorToJson("ind2", tag.getInd2()));
			text += StringUtils.join(indicators, ",\n");
			text += "}\n";
		}

		List<String> subfields = new ArrayList<>();
		for (SubfieldDefinition subfield : tag.getSubfields()) {
			subfields.add(subfieldToJson(subfield));
		}
		text += ",\"subfields\":{\n" + StringUtils.join(subfields, ",\n") + "}\n";

		if (tag.getHistoricalSubfields() != null) {
			subfields = new ArrayList<>();
			for (Code code : tag.getHistoricalSubfields()) {
				subfields.add('"' + code.getCode() + "\":" + toJson(true,
					"label", code.getLabel()
				));
			}
			text += ",\"historical-subfields\":{\n" + StringUtils.join(subfields, ",\n") + "}\n";
		}

		text += "}";

		return text;
	}

	private static String subfieldToJson(SubfieldDefinition subfield) {
		String text = '"' + subfield.getCode() + "\":{" + toJson(false,
			"label", subfield.getLabel(),
			"repeatable", (subfield.getCardinalityCode().equals("R") ? "false" : "true")
		);

		if (MappingToJson.exportSubfieldCodes) {
			if (subfield.getCodeList() != null
				&& !subfield.getCodeList().getCodes().isEmpty()) {
				CodeList codeList = subfield.getCodeList();
				String meta = toJson(false,
					"name", codeList.getName(),
					"url", codeList.getUrl()
				);
				if (!codeList.getName().equals("MARC Organization Codes")) {
					List<String> codes = new ArrayList<>();
					for (Code code : subfield.getCodeList().getCodes()) {
						codes.add(toJson(true,
							"code", code.getCode(),
							"label", code.getLabel().replace("\"", "\\\"")
						));
					}
					meta += ",\"codes\":[" + StringUtils.join(codes, ",\n") + "]\n";
				}
				text += ",\"codelist\":{" + meta + "}\n";
			}
		}
		text += "}";
		return text;
	}

	private static String indicatorToJson(String label, Indicator indicator) {
		String text = String.format("\"%s\":{", label);
		text += String.format("\"label\": \"%s\"", indicator.getLabel());
		text += "}";
		return text;
	}

	private static String toJson(boolean isAUnit, String... args) {
		Map<String, String> values = new LinkedHashMap<>();
		for (int i = 0; i < args.length; i += 2) {
			values.put(args[i], args[i+1]);
		}
		return mapToJson(values, isAUnit);
	}
}
