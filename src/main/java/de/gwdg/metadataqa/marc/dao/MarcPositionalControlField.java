package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.structure.ControlFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.ControlfieldPositionDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.keygenerator.PositionalControlFieldKeyGenerator;

import java.util.*;

public abstract class MarcPositionalControlField extends MarcControlField {

  protected ControlFieldDefinition definition;
  protected Map<ControlfieldPositionDefinition, String> valuesMap;
  protected List<ControlValue> valuesList;
  private Map<Integer, ControlValue> valuesByPosition = new LinkedHashMap<>();
  protected Leader.Type recordType;

  public MarcPositionalControlField(ControlFieldDefinition definition, String content) {
    this(definition, content, null);
  }

  public MarcPositionalControlField(ControlFieldDefinition definition,
                                    String content,
                                    Leader.Type recordType) {
    super(definition, content);
    this.definition = definition;
    this.recordType = recordType;
    valuesMap = new LinkedHashMap<>();
    valuesList = new ArrayList<>();
  }

  @Override
  public void setMarcRecord(BibliographicRecord marcRecord) {
    super.setMarcRecord(marcRecord);
    for (ControlValue value : valuesList) {
      value.setMarcRecord(this.marcRecord);
    }
  }

  protected abstract void processContent();

  @Override
  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
    return getKeyValuePairs(definition.getTag(), definition.getMqTag(), type);
  }

  public Map<String, List<String>> getKeyValuePairs(String tag,
                                    String mqTag,
                                    SolrFieldType type) {
    Map<String, List<String>> map = new LinkedHashMap<>();
    PositionalControlFieldKeyGenerator keyGenerator = new PositionalControlFieldKeyGenerator(tag, mqTag, type);
    if (content != null) {
      map.put(keyGenerator.forTag(), Arrays.asList(content));
      for (Map.Entry<ControlfieldPositionDefinition, String> entry : valuesMap.entrySet()) {
        ControlfieldPositionDefinition position = entry.getKey();
        String value = position.resolve(entry.getValue());
        map.put(keyGenerator.forSubfield(position), Arrays.asList(value));
      }
    }
    return map;
  }

  public Map<ControlfieldPositionDefinition, String> getMap() {
    return valuesMap;
  }

  public List<ControlValue> getValuesList() {
    return valuesList;
  }

  public String getLabel() {
    return definition.getLabel();
  }

  public String getTag() {
    return definition.getTag();
  }

  public String getMqTag() {
    return definition.getMqTag();
  }

  public Cardinality getCardinality() {
    return definition.getCardinality();
  }

  protected void registerControlValue(ControlValue controlValue) {
    valuesList.add(controlValue);
    valuesByPosition.put(controlValue.getDefinition().getPositionStart(), controlValue);
  }

  public ControlValue getControlValueByPosition(int position) {
    return valuesByPosition.get(position);
  }
}
