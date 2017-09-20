package de.gwdg.metadataqa.marc;

import java.util.*;

public class MarcRecord {

	private Leader leader;
	private Control001 control001;
	private Control003 control003;
	private Control005 control005;
	private Control006 control006;
	private Control007 control007;
	private Control008 control008;
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

	public Leader getLeader() {
		return leader;
	}

	public Leader.Type getType() {
		return leader.getType();
	}

	public Control001 getControl001() {
		return control001;
	}

	public void setControl001(Control001 control001) {
		this.control001 = control001;
	}

	public Control003 getControl003() {
		return control003;
	}

	public void setControl003(Control003 control003) {
		this.control003 = control003;
	}

	public Control005 getControl005() {
		return control005;
	}

	public void setControl005(Control005 control005) {
		this.control005 = control005;
	}

	public Control006 getControl006() {
		return control006;
	}

	public void setControl006(Control006 control006) {
		this.control006 = control006;
	}

	public Control007 getControl007() {
		return control007;
	}

	public void setControl007(Control007 control007) {
		this.control007 = control007;
	}

	public Control008 getControl008() {
		return control008;
	}

	public void setControl008(Control008 control008) {
		this.control008 = control008;
	}

	public boolean validate() {
		return true;
	}

	private List<Extractable> getControlfields() {
		return Arrays.asList(
			control001, control003, control005, control006, control007, control008);
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

		mainKeyValuePairs.putAll(leader.getKeyValuePairs());

		for (Extractable controlField : getControlfields()) {
			mainKeyValuePairs.putAll(controlField.getKeyValuePairs());
		}

		for (DataField field : datafields) {
			Map<String, List<String>> keyValuePairs = field.getKeyValuePairs();
			mainKeyValuePairs.putAll(keyValuePairs);
		}
		return mainKeyValuePairs;
	}
}
