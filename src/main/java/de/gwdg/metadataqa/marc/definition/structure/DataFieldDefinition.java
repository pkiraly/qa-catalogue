package de.gwdg.metadataqa.marc.definition.structure;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.definition.Cardinality;
import de.gwdg.metadataqa.marc.definition.CompilanceLevel;
import de.gwdg.metadataqa.marc.definition.FRBRFunction;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.bibliographic.BibliographicFieldDefinition;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class DataFieldDefinition implements BibliographicFieldDefinition, Serializable {

  private static final long serialVersionUID = 4422093591547118076L;
  protected String tag;
  protected String bibframeTag;
  protected String mqTag;
  protected String label;
  protected Cardinality cardinality = Cardinality.Nonrepeatable;
  protected Indicator ind1;
  protected Indicator ind2;
  protected String descriptionUrl;
  protected List<SubfieldDefinition> subfields;
  protected Map<String, SubfieldDefinition> subfieldIndex = new LinkedHashMap<>();
  protected List<EncodedValue> historicalSubfields;
  protected Map<String, EncodedValue> historicalSubfieldsIndex;
  protected String indexTag = null;
  protected Map<MarcVersion, List<SubfieldDefinition>> versionSpecificSubfields;
  protected List<FRBRFunction> functions;
  protected SourceSpecificationType sourceSpecificationType = null;
  protected boolean obsolete = false;
  private CompilanceLevel nationalCompilanceLevel;
  private CompilanceLevel minimalCompilanceLevel;
  private MarcVersion marcVersion;

  public String getTag() {
    return tag;
  }

  public String getIndexTag() {
    if (indexTag != null) {
      return indexTag;
    }
    if (mqTag != null) {
      indexTag = mqTag;
    } else if (bibframeTag != null) {
      indexTag = bibframeTag.replace("/", "");
    } else {
      indexTag = tag;
    }

    return indexTag;
  }

  public String getLabel() {
    return label;
  }

  public Cardinality getCardinality() {
    return cardinality;
  }

  public Indicator getInd1() {
    return ind1;
  }

  public Indicator getInd2() {
    return ind2;
  }

  public List<Indicator> getIndicators() {
    return Arrays.asList(ind1, ind2);
  }

  protected void postCreation() {
    setIndicatorFlags();
  }

  protected void setIndicatorFlags() {
    if (ind1 != null) {
      ind1.setParent(this);
      ind1.setIndicatorFlag("ind1");
    }
    if (ind2 != null) {
      ind2.setParent(this);
      ind2.setIndicatorFlag("ind2");
    }
  }

  public List<SubfieldDefinition> getSubfields() {
    return subfields;
  }

  protected void setSubfields(String... input) {
    subfields = new LinkedList<>();
    for (int i = 0; i < input.length; i += 2) {
      subfields.add(new SubfieldDefinition(input[i], input[i + 1]));
    }
    indexSubfields();
  }

  protected void setSubfieldsWithCardinality(String... input) {
    subfields = new LinkedList<>();
    for (int i = 0; i < input.length; i += 3) {
      String code = input[i];
      String subfieldLabel = input[i + 1];
      String subfieldCardinality = input[i + 2];
      SubfieldDefinition definition = new SubfieldDefinition(code, subfieldLabel, subfieldCardinality);
      definition.setParent(this);
      subfields.add(definition);
    }
    indexSubfields();
  }

  /**
   * Populates the subfield index hash map. The key is the subfield code.
   */
  protected void indexSubfields() {
    for (SubfieldDefinition subfield : subfields) {
      subfieldIndex.put(subfield.getCode(), subfield);
    }
  }

  protected DataFieldDefinition setHistoricalSubfields(String... input) {
    historicalSubfields = new LinkedList<>();
    for (int i = 0; i<input.length; i+=2) {
      historicalSubfields.add(new EncodedValue(input[i], input[i+1]));
    }
    indexHistoricalSubfields();
    return this;
  }

  private void indexHistoricalSubfields() {
    historicalSubfieldsIndex = new LinkedHashMap<>();
    for (EncodedValue code : historicalSubfields) {
      historicalSubfieldsIndex.put(code.getCode(), code);
    }
  }

  public boolean isHistoricalSubfield(String code) {
    return historicalSubfields != null
      && !historicalSubfields.isEmpty()
      && historicalSubfieldsIndex.containsKey(code);
  }

  public String getDescriptionUrl() {
    return descriptionUrl;
  }

  public SubfieldDefinition getSubfield(String code) {
    return subfieldIndex.getOrDefault(code, null);
  }

  public void putVersionSpecificSubfields(MarcVersion marcVersion,
                                          List<SubfieldDefinition> subfieldDefinitions) {
    if (versionSpecificSubfields == null)
      versionSpecificSubfields = new EnumMap<>(MarcVersion.class);

    for (SubfieldDefinition subfieldDefinition : subfieldDefinitions) {
      subfieldDefinition.setParent(this);
      subfieldDefinition.setMarcVersion(marcVersion);
    }

    versionSpecificSubfields.put(marcVersion, subfieldDefinitions);
  }

  public Map<MarcVersion, List<SubfieldDefinition>> getVersionSpecificSubfields() {
    return versionSpecificSubfields;
  }

  public boolean hasVersionSpecificSubfields(MarcVersion marcVersion) {
    return versionSpecificSubfields.containsKey(marcVersion);
  }

  public boolean isVersionSpecificSubfields(MarcVersion marcVersion, String code) {
    if (versionSpecificSubfields == null
      || versionSpecificSubfields.isEmpty()
      || !versionSpecificSubfields.containsKey(marcVersion)
      || versionSpecificSubfields.get(marcVersion).isEmpty()) {
      return false;
    }

    for (SubfieldDefinition subfieldDefinition : versionSpecificSubfields.get(marcVersion)) {
      if (subfieldDefinition.getCode().equals(code)) {
        return true;
      }
    }
    return false;
  }

  public SubfieldDefinition getVersionSpecificDefinition(MarcVersion marcVersion, String code) {
    if (!isVersionSpecificSubfields(marcVersion, code)) {
      return null;
    }

    for (SubfieldDefinition subfieldDefinition : versionSpecificSubfields.get(marcVersion)) {
      if (subfieldDefinition.getCode().equals(code)) {
        return subfieldDefinition;
      }
    }
    return null;
  }

  public List<EncodedValue> getHistoricalSubfields() {
    return historicalSubfields;
  }

  public String getMqTag() {
    return mqTag;
  }

  public List<FRBRFunction> getFrbrFunctions() {
    return functions;
  }

  public SourceSpecificationType getSourceSpecificationType() {
    return sourceSpecificationType;
  }

  public void setCompilanceLevels(String national) {
    setNationalCompilanceLevel(national);
  }

  public void setCompilanceLevels(String national, String minimal) {
    setNationalCompilanceLevel(national);
    setMinimalCompilanceLevel(minimal);
  }

  public CompilanceLevel getNationalCompilanceLevel() {
    return nationalCompilanceLevel;
  }

  public void setNationalCompilanceLevel(CompilanceLevel nationalLevel) {
    this.nationalCompilanceLevel = nationalLevel;
  }

  public void setNationalCompilanceLevel(String level) {
    if (StringUtils.isNotBlank(level))
      this.nationalCompilanceLevel = CompilanceLevel.byAbbreviation(level);
  }

  public CompilanceLevel getMinimalCompilanceLevel() {
    return minimalCompilanceLevel;
  }

  public void setMinimalCompilanceLevel(String level) {
    if (StringUtils.isNotBlank(level))
      this.minimalCompilanceLevel = CompilanceLevel.byAbbreviation(level);
  }

  public void setMinimalCompilanceLevel(CompilanceLevel minimalLevel) {
    this.minimalCompilanceLevel = minimalLevel;
  }

  public MarcVersion getMarcVersion() {
    if (marcVersion == null)
      marcVersion = Utils.getVersion(this);
    return marcVersion;
  }

  public boolean isObsolete() {
    return obsolete;
  }


  @Override
  public String toString() {
    return "DataFieldDefinition{" +
      "tag='" + tag + '\'' +
      ", label='" + label + '\'' +
      ", cardinality=" + cardinality +
      // ", ind1=" + ind1 +
      // ", ind2=" + ind2 +
      ", descriptionUrl='" + descriptionUrl + '\'' +
      // ", subfields=" + subfields +
      '}';
  }
}
