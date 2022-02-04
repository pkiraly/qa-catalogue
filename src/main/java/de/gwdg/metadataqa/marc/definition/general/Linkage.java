package de.gwdg.metadataqa.marc.definition.general;

import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.model.SolrFieldType;

import java.io.Serializable;
import java.util.*;

public class Linkage implements Extractable, Serializable {

  private String linkingTag;
  private String occurrenceNumber;
  private String scriptIdentificationCode;
  private String fieldOrientationCode;
  private static Map<String, String> scriptIdentificationCodes;
  private static Map<String, String> fieldOrientationCodes;

  static {
    scriptIdentificationCodes = new HashMap<>();
    scriptIdentificationCodes.put("(3", "Arabic");
    scriptIdentificationCodes.put("(B", "Latin");
    scriptIdentificationCodes.put("$1", "Chinese, Japanese, Korean");
    scriptIdentificationCodes.put("(N", "Cyrillic");
    scriptIdentificationCodes.put("(S", "Greek");
    scriptIdentificationCodes.put("(2", "Hebrew");

    fieldOrientationCodes = new HashMap<>();
    fieldOrientationCodes.put("r", "right-to-left");
  }

  public Linkage(String linkingTag, String occurrenceNumber) {
    this.linkingTag = linkingTag;
    this.occurrenceNumber = occurrenceNumber;
  }

  public void setScriptIdentificationCode(String scriptIdentificationCode) {
    this.scriptIdentificationCode = scriptIdentificationCode;
  }

  public void setFieldOrientationCode(String fieldOrientationCode) {
    this.fieldOrientationCode = fieldOrientationCode;
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs() {
    return getKeyValuePairs(SolrFieldType.MARC);
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
    return getKeyValuePairs(type, MarcVersion.MARC21);
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type, MarcVersion marcVersion) {
    Map<String, List<String>> map = new LinkedHashMap<>();
    Map<String, String> simpleMap = getMap();
    for (Map.Entry<String, String> entry : simpleMap.entrySet()) {
      map.put(entry.getKey(), Arrays.asList(entry.getValue()));
    }
    return map;
  }

  public Map<String, String> getMap() {
    Map<String, String> map = new LinkedHashMap<>();
    map.put("linkingTag", linkingTag);
    map.put("occurrenceNumber", occurrenceNumber);
    if (scriptIdentificationCode != null)
      map.put("scriptIdentificationCode", resolveScriptIdentificationCode());
    if (fieldOrientationCode != null)
      map.put("fieldOrientationCode", resolveFieldOrientationCode());

    return map;
  }

  public String resolveScriptIdentificationCode() {
    if (scriptIdentificationCodes.containsKey(scriptIdentificationCode))
      return scriptIdentificationCodes.get(scriptIdentificationCode);
    return scriptIdentificationCode;
  }

  public String resolveFieldOrientationCode() {
    if (fieldOrientationCodes.containsKey(fieldOrientationCode))
      return fieldOrientationCodes.get(fieldOrientationCode);
    return fieldOrientationCode;
  }

  public String getLinkingTag() {
    return linkingTag;
  }

  public String getOccurrenceNumber() {
    return occurrenceNumber;
  }

  public String getScriptIdentificationCode() {
    return scriptIdentificationCode;
  }

  public String getFieldOrientationCode() {
    return fieldOrientationCode;
  }

  @Override
  public String toString() {
    return "Linkage{" +
      "linkingTag='" + linkingTag + '\'' +
      ", occurrenceNumber='" + occurrenceNumber + '\'' +
      ", scriptIdentificationCode='" + resolveScriptIdentificationCode() + '\'' +
      ", fieldOrientationCode='" + fieldOrientationCode + '\'' +
      '}';
  }
}
