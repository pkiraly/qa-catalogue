package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.Range;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;

import java.util.*;

public class Indicator {
  private DataFieldDefinition parent;
  private String label = null;
  private String bibframeTag = null;
  private String mqTag = null;
  private String indexTag = null;
  private List<EncodedValue> codes;
  protected List<EncodedValue> historicalCodes;
  private Map<String, EncodedValue> codeIndex = new LinkedHashMap<>();
  private Map<String, EncodedValue> historicalCodeIndex = new LinkedHashMap<>();
  private Map<Range, EncodedValue> ranges;
  private String indicatorFlag;
  private Map<MarcVersion, List<EncodedValue>> versionSpecificCodes;
  private List<FRBRFunction> functions;

  public Indicator() {}

  public Indicator(String label) {
    this.label = label;
  }

  public Indicator(String label, List<EncodedValue> codes) {
    this.label = label;
    this.codes = codes;
    indexCodes();
  }

  public String getPath() {
    return String.format("%s$%s", parent.getTag(), indicatorFlag);
  }

  public String getIndexTag() {
    if (indexTag == null) {
      if (mqTag != null)
        indexTag = mqTag;
      else if (bibframeTag != null)
        indexTag = bibframeTag.replace("/", "");
      else
        indexTag = indicatorFlag;
    }
    return indexTag;
  }

  public Indicator setLabel(String label) {
    this.label = label;
    return this;
  }

  public Indicator setCodes(List<EncodedValue> codes) {
    this.codes = codes;
    return this;
  }

  public Indicator setCodes(String... input) {
    codes = new ArrayList<>();
    for (int i = 0; i<input.length; i+=2) {
      codes.add(new EncodedValue(input[i], input[i+1]));
    }
    indexCodes();
    return this;
  }

  public Indicator setHistoricalCodes(String... input) {
    historicalCodes = new ArrayList<>();
    for (int i = 0; i<input.length; i+=2) {
      historicalCodes.add(new EncodedValue(input[i], input[i+1]));
    }
    indexHistoricalCodes();
    return this;
  }

  public String getLabel() {
    return label;
  }

  public String getBibframeTag() {
    return bibframeTag;
  }

  public void setBibframeTag(String bibframeTag) {
    this.bibframeTag = bibframeTag;
  }

  public String getMqTag() {
    return mqTag;
  }

  public Indicator setMqTag(String mqTag) {
    this.mqTag = mqTag;
    return this;
  }

  public boolean exists() {
    return label != null && !label.equals("");
  }

  public List<EncodedValue> getCodes() {
    return codes;
  }

  public EncodedValue getCode(String codeString) {
    if (codeIndex.containsKey(codeString))
      return codeIndex.get(codeString);

    for (Range range : getRanges().keySet()) {
      if (range.isValid(codeString))
        return ranges.get(range);
    }

    return null;
  }

  public boolean hasCode(String code) {
    if (codeIndex.containsKey(code))
      return true;
    for (Range range : getRanges().keySet()) {
      if (range.isValid(code))
        return true;
    }

    return false;
  }

  private Map<Range, EncodedValue> getRanges() {
    if (ranges == null) {
      ranges = new HashMap<>();
      if (codes != null) {
        for (EncodedValue code : codes) {
          if (code.isRange()) {
            ranges.put(code.getRange(), code);
          }
        }
      }
    }
    return ranges;
  }

  private void indexCodes() {
    codeIndex = new LinkedHashMap<>();
    for (EncodedValue code : codes) {
      codeIndex.put(code.getCode(), code);
    }
  }

  private void indexHistoricalCodes() {
    historicalCodeIndex = new LinkedHashMap<>();
    for (EncodedValue code : historicalCodes) {
      historicalCodeIndex.put(code.getCode(), code);
    }
  }

  public boolean isHistoricalCode(String code) {
    return historicalCodes != null
      && !historicalCodes.isEmpty()
      && historicalCodeIndex.containsKey(code);
  }

  public void setIndicatorFlag(String indicatorFlag) {
    this.indicatorFlag = indicatorFlag;
  }

  public void setParent(DataFieldDefinition parent) {
    this.parent = parent;
  }

  public Indicator putVersionSpecificCodes(MarcVersion marcVersion, List<EncodedValue> codeList) {
    if (versionSpecificCodes == null)
      versionSpecificCodes = new EnumMap<>(MarcVersion.class);
    versionSpecificCodes.put(marcVersion, codeList);
    return this;
  }

  public boolean hasVersionSpecificCodes(MarcVersion marcVersion) {
    return versionSpecificCodes.containsKey(marcVersion);
  }

  public List<EncodedValue> getHistoricalCodes() {
    return historicalCodes;
  }

  public boolean isVersionSpecificCode(MarcVersion marcVersion, String code) {
    if (versionSpecificCodes != null
        && !versionSpecificCodes.isEmpty()
        && versionSpecificCodes.containsKey(marcVersion)
        && !versionSpecificCodes.get(marcVersion).isEmpty())
      for (EncodedValue versionSpecificCode : versionSpecificCodes.get(marcVersion))
        if (versionSpecificCode.getCode().equals(code))
          return true;
    return false;
  }

  @Override
  public String toString() {
    return "Indicator{" +
      "label='" + label + '\'' +
      ", codes=" + codes +
      ", indicatorFlag='" + indicatorFlag + '\'' +
      '}';
  }

  public Indicator setFrbrFunctions(FRBRFunction... functions) {
    this.functions = Arrays.asList(functions);
    return this;
  }

  public List<FRBRFunction> getFrbrFunctions() {
    return functions;
  }

  public String getIndicatorFlag() {
    return indicatorFlag;
  }
}
