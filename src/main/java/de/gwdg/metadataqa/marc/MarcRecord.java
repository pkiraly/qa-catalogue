package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.validator.ClassificationReferenceValidator;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.logging.Logger;

public class MarcRecord implements Extractable, Validatable {

	private static final Logger logger = Logger.getLogger(MarcRecord.class.getCanonicalName());

	private Leader leader;
	private Control001 control001;
	private Control003 control003;
	private Control005 control005;
	private Control006 control006;
	private Control007 control007;
	private Control008 control008;
	private List<DataField> datafields;
	private Map<String, List<DataField>> datafieldIndex;
	Map<String, List<String>> mainKeyValuePairs;
	private List<String> errors = null;
	private List<ValidationError> validationErrors = null;

	private List<String> unhandledTags;

	public MarcRecord() {
		datafields = new ArrayList<>();
		datafieldIndex = new TreeMap<>();
		unhandledTags = new ArrayList<>();
	}

	public MarcRecord(String id) {
		this();
		control001 = new Control001(id);
	}

	public void addDataField(DataField dataField) {
		dataField.setRecord(this);
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
		leader.setMarcRecord(this);
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

	public MarcRecord setControl001(Control001 control001) {
		this.control001 = control001;
		return this;
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
		control006.setMarcRecord(this);
	}

	public Control007 getControl007() {
		return control007;
	}

	public void setControl007(Control007 control007) {
		this.control007 = control007;
		control007.setMarcRecord(this);
	}

	public Control008 getControl008() {
		return control008;
	}

	public void setControl008(Control008 control008) {
		this.control008 = control008;
		control008.setMarcRecord(this);
	}

	public String getId() {
		return control001.getContent();
	}

	private List<Extractable> getControlfields() {
		return Arrays.asList(
			control001, control003, control005, control006, control007, control008);
	}

	public List<DataField> getDatafield(String tag) {
		return datafieldIndex.getOrDefault(tag, null);
	}

	public boolean exists(String tag) {
		List<DataField> fields = getDatafield(tag);
		return (fields != null && !fields.isEmpty());
	}

	public List<String> getUnhandledTags() {
		return unhandledTags;
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
		if (mainKeyValuePairs == null) {
			mainKeyValuePairs = new LinkedHashMap<>();

			mainKeyValuePairs.put("type", Arrays.asList(getType().getValue()));
			mainKeyValuePairs.putAll(leader.getKeyValuePairs());

			for (Extractable controlField : getControlfields()) {
				if (controlField != null)
					mainKeyValuePairs.putAll(controlField.getKeyValuePairs());
			}

			for (DataField field : datafields) {
				Map<String, List<String>> keyValuePairs = field.getKeyValuePairs();
				mainKeyValuePairs.putAll(keyValuePairs);
			}
		}

		return mainKeyValuePairs;
	}

	@Override
	public boolean validate(MarcVersion marcVersion) {
		return validate(marcVersion, false);
	}

	public boolean validate(MarcVersion marcVersion, boolean isSummary) {
		errors = new ArrayList<>();
		validationErrors = new ArrayList<>();
		boolean isValidRecord = true;
		boolean isValidComponent;

		isValidComponent = leader.validate(marcVersion);
		if (!isValidComponent) {
			errors.addAll(leader.getErrors());
			validationErrors.addAll(leader.getValidationErrors());
			isValidRecord = isValidComponent;
		}

		if (!unhandledTags.isEmpty()) {
			if (isSummary) {
				for (String tag : unhandledTags) {
					validationErrors.add(new ValidationError(getId(), tag, ValidationErrorType.UndefinedField, tag, null));
					errors.add(String.format("Unhandled tag: %s", tag));
				}
			} else {
				Map<String, Integer> tags = new LinkedHashMap<>();
				for (String tag : unhandledTags) {
					if (!tags.containsKey(tag))
						tags.put(tag, 0);
					tags.put(tag, tags.get(tag) + 1);
				}
				List<String> unhandledTagsList = new ArrayList<>();
				for (String tag : tags.keySet()) {
					if (tags.get(tag) == 1)
						unhandledTagsList.add(tag);
					else
						unhandledTagsList.add(String.format("%s (%d*)", tag, tags.get(tag)));
				}
				String tagList = StringUtils.join(unhandledTagsList, ", ");
				validationErrors.add(new ValidationError(getId(), null, ValidationErrorType.UndefinedField, tagList, null));
				errors.add(String.format("Unhandled %s: %s",
					(unhandledTags.size() == 1 ? "tag" : "tags"),
					tagList));
			}

			isValidRecord = false;
		}

		// TODO: use reflection to get all validator class
		ValidatorResponse validatorResponse;
		/*
		validatorResponse = ClassificationReferenceValidator.validate(this);
		if (!validatorResponse.isValid()) {
			errors.addAll(validatorResponse.getErrors());
			isValidRecord = false;
		}
		*/

		for (Extractable controlField : getControlfields()) {
			if (controlField != null) {
				isValidComponent = ((Validatable)controlField).validate(marcVersion);
				if (!isValidComponent) {
					validationErrors.addAll(((Validatable)controlField).getValidationErrors());
					errors.addAll(((Validatable)controlField).getErrors());
					isValidRecord = isValidComponent;
				}
			}
		}

		Map<DataFieldDefinition, Integer> repetitionCounter = new HashMap<>();
		for (DataField field : datafields) {
			if (!repetitionCounter.containsKey(field.getDefinition())) {
				repetitionCounter.put(field.getDefinition(), 0);
			}
			repetitionCounter.put(field.getDefinition(), repetitionCounter.get(field.getDefinition()) + 1);
			if (!field.validate(marcVersion)) {
				isValidRecord = false;
				validationErrors.addAll(field.getValidationErrors());
				errors.addAll(field.getErrors());
			}

			validatorResponse = ClassificationReferenceValidator.validate(field);
			if (!validatorResponse.isValid()) {
				validationErrors.addAll(validatorResponse.getValidationErrors());
				errors.addAll(validatorResponse.getErrors());
				isValidRecord = false;
			}
		}

		for (DataFieldDefinition fieldDefinition : repetitionCounter.keySet()) {
			if (repetitionCounter.get(fieldDefinition) > 1
				&& fieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
				validationErrors.add(new ValidationError(getId(), fieldDefinition.getTag(),
					ValidationErrorType.NonrepeatableField,
					String.format(
						"non-repeatable, however there are %d instances",
						repetitionCounter.get(fieldDefinition)
					),
					fieldDefinition.getDescriptionUrl()
				));
				errors.add(String.format(
					"%s is not repeatable, however there are %d instances (%s)",
					fieldDefinition.getTag(),
					repetitionCounter.get(fieldDefinition), fieldDefinition.getDescriptionUrl()));
				isValidRecord = false;
			}
		}

		return isValidRecord;
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}
}
