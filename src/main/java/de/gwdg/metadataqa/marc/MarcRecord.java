package de.gwdg.metadataqa.marc;

import java.util.*;

public class MarcRecord {
	private Leader leader;
	private List<DataField> datafields;
	private Map<String, List<DataField>> datafieldIndex;
	private List<String> unhandledTags;

	public MarcRecord() {
		datafields = new ArrayList<>();
		datafieldIndex = new TreeMap<>();
		unhandledTags = new ArrayList<>();
	}

	public void addDataField(DataField dataField) {
		indexField(dataField);
		datafields.add(dataField);
	}

	private void indexField(DataField dataField) {
		String tag = dataField.getTag();

		if (!datafieldIndex.containsKey(tag))
			datafieldIndex.put(tag, new ArrayList<>());

		datafieldIndex.get(tag).add(dataField);
	}

	public void addUnhandledTags(String tag) {
		unhandledTags.add(tag);
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	public Object getLeader() {
		return leader;
	}

	public boolean validate() {
		return true;
	}

	public String format() {
		String output = "";
		for (DataField field : datafields) {
			output += field.format();
		}
		return output;
	}

	public String formatAsMarc() {
		String output = "";
		for (DataField field : datafields) {
			output += field.formatAsMarc();
		}
		return output;
	}

	public String formatForIndex() {
		String output = "";
		for (DataField field : datafields) {
			output += field.formatForIndex();
		}
		return output;
	}

	public Map<String, List<String>> getKeyValuePairs() {
		Map<String, List<String>> mainKeyValuePairs = new LinkedHashMap<>();
		for (DataField field : datafields) {
			Map<String, List<String>> keyValuePairs = field.getKeyValuePairs();
			mainKeyValuePairs.putAll(keyValuePairs);
		}
		return mainKeyValuePairs;
	}
}
