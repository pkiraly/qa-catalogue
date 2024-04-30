package de.gwdg.metadataqa.marc.dao;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.Extractable;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.MarcVersion;
import de.gwdg.metadataqa.marc.definition.SourceSpecificationType;
import de.gwdg.metadataqa.marc.definition.TagDefinitionLoader;
import de.gwdg.metadataqa.marc.definition.bibliographic.SchemaType;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd1OrIf7FromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd1OrIfEmptyFromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd2;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd2AndSubfield2;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromInd2For055OrIf7FromSubfield2;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.SchemaFromSubfield2;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
import de.gwdg.metadataqa.marc.definition.structure.Indicator;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGeneratorFactory;
import de.gwdg.metadataqa.marc.utils.pica.PicaFieldDefinition;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataField implements Extractable, Serializable {

  private static final Logger logger = Logger.getLogger(DataField.class.getCanonicalName());
  private static final long serialVersionUID = -2283988601945108192L;

  private final DataFieldDefinition definition;
  private String tag;
  private final String ind1;
  private final String ind2;
  private List<MarcSubfield> subfields;
  private final Map<String, List<MarcSubfield>> subfieldIndex = new LinkedHashMap<>();
  /**
   * Occurrence is only used in PICA records
   */
  private String occurrence;
  private List<String> unhandledSubfields = null;
  private BibliographicRecord bibliographicRecord;
  private List<FieldIndexer> fieldIndexers;
  private boolean fieldIndexerInitialized = false;

  public <T extends DataFieldDefinition> DataField(T definition, String ind1, String ind2) {
    this.definition = definition;
    this.ind1 = ind1;
    this.ind2 = ind2;
    this.subfields = new ArrayList<>();
  }

  public <T extends DataFieldDefinition> DataField(T definition,
                                                   String ind1,
                                                   String ind2,
                                                   List<Map<String, String>> subfields) {
    this(definition, ind1, ind2);
    if (subfields == null) {
      return;
    }

    for (Map<String, String> subfield : subfields) {
      String code = subfield.get("code");
      String value = subfield.get("content");
      SubfieldDefinition subfieldDefinition = definition.getSubfield(code);

      if (subfieldDefinition != null) {
        MarcSubfield marcSubfield = new MarcSubfield(subfieldDefinition, code, value);
        marcSubfield.setField(this);
        this.subfields.add(marcSubfield);
        indexSubfield(code, marcSubfield);
        continue;
      }

      if (!(definition.getTag().equals("886") && code.equals("k")) && !definition.getTag().equals("936")) {
        String errorMessage = String.format("no definition for %s$%s (value: '%s') %s %s%n",
          definition.getTag(), code, value, definition.getTag().equals("886"),
          code.equals("k"));
        logger.severe(errorMessage);
      }
    }
  }

  public <T extends DataFieldDefinition> DataField(T definition,
                                                   String ind1,
                                                   String ind2,
                                                   String... subfields) {
    this(definition, ind1, ind2);
    if (subfields != null) {
      parseSubfieldArray(subfields);
    }
  }

  public DataField(String tag, String input) {
    this(tag, input, MarcVersion.MARC21);
  }

  public DataField(String tag, String input, MarcVersion version) {
    definition = TagDefinitionLoader.load(tag, version);
    if (definition == null) {
      this.tag = tag;
    }
    this.subfields = new ArrayList<>();

    ind1 = input.substring(0, 1);
    ind2 = input.substring(1, 2);
    parseAndAddSubfields(input.substring(2));
  }

  public DataField(String tag, String ind1, String ind2, MarcVersion marcVersion) {
    definition = TagDefinitionLoader.load(tag, marcVersion);
    if (definition == null) {
      this.tag = tag;
    }
    this.ind1 = ind1;
    this.ind2 = ind2;
    this.subfields = new ArrayList<>();
  }

  public DataField(String tag, String ind1, String ind2, String content, MarcVersion marcVersion) {
    definition = TagDefinitionLoader.load(tag, marcVersion);
    if (definition == null) {
      this.tag = tag;
    }
    this.ind1 = ind1;
    this.ind2 = ind2;
    this.subfields = new ArrayList<>();

    parseAndAddSubfields(content);
  }

  public static List<String[]> parseSubfields(String content) {
    List<String[]> subfields = new ArrayList<>();

    boolean codeFlag = false;
    String code = null;
    StringBuilder value = new StringBuilder();
    for (int i = 0; i < content.length(); i++) {
      String c = Character.toString(content.charAt(i));
      if (c.equals("$")) {
        codeFlag = true;
        if (code != null)
          subfields.add(new String[]{code, value.toString()});
        code = null;
        value = new StringBuilder();
      } else {
        if (codeFlag) {
          code = c;
          codeFlag = false;
        } else {
          value.append(c);
        }
      }
    }
    subfields.add(new String[]{code, value.toString()});

    return subfields;
  }

  private void parseAndAddSubfields(String content) {
    for (String[] sf : parseSubfields(content))
      addSubfield(sf[0], sf[1]);
  }

  public BibliographicRecord getBibliographicRecord() {
    return bibliographicRecord;
  }

  public void setBibliographicRecord(BibliographicRecord bibliographicRecord) {
    this.bibliographicRecord = bibliographicRecord;
    for (MarcSubfield marcSubfield : subfields)
      marcSubfield.setMarcRecord(bibliographicRecord);
  }

  public void indexSubfields() {
    for (MarcSubfield marcSubfield : subfields)
      indexSubfield(marcSubfield.getCode(), marcSubfield);
  }

  private void indexSubfield(String code, MarcSubfield marcSubfield) {
    subfieldIndex.computeIfAbsent(code, s -> new LinkedList<>());
    subfieldIndex.get(code).add(marcSubfield);
  }

  private void parseSubfieldArray(String[] subfields) {
    for (int i = 0; i < subfields.length; i += 2) {
      String code = subfields[i];
      String value = subfields[i + 1];
      addSubfield(code, value);
    }
  }

  private void addSubfield(String code, String value) {
    SubfieldDefinition subfieldDefinition = definition != null
                                          ? definition.getSubfield(code)
                                          : null;
    MarcSubfield marcSubfield = new MarcSubfield(subfieldDefinition, code, value);
    marcSubfield.setField(this);
    this.subfields.add(marcSubfield);
    indexSubfield(code, marcSubfield);
  }

  public Map<String, List<String>> getHumanReadableMap() {
    Map<String, List<String>> map = new LinkedHashMap<>();
    if (definition.getInd1().exists())
      map.put(definition.getInd1().getLabel(), Collections.singletonList(resolveInd1()));
    if (definition.getInd2().exists())
      map.put(definition.getInd2().getLabel(), Collections.singletonList(resolveInd2()));
    for (MarcSubfield subfield : subfields) {
      map.computeIfAbsent(subfield.getLabel(), s -> new ArrayList<>());
      map.get(subfield.getLabel()).add(subfield.resolve());
    }
    return map;
  }

  public String simpleFormat() {
    StringBuilder output = new StringBuilder();

    output.append(ind1);
    output.append(ind2);
    output.append(" ");

    for (MarcSubfield subfield : subfields) {
      output.append(String.format(
        "$%s%s",
        subfield.getDefinition().getCode(), subfield.getValue()
      ));
    }

    return output.toString();
  }

  public String format() {
    StringBuilder output = new StringBuilder();
    if (definition != null)
      output.append(String.format("[%s: %s]%n", definition.getTag(), definition.getLabel()));
    else
      output.append(String.format("[%s]%n", getTag()));

    if (definition != null && definition.getInd1() != null && definition.getInd1().exists())
      output.append(String.format("%s: %s%n", definition.getInd1().getLabel(), resolveInd1()));
    else if (StringUtils.isNotBlank(getInd1()))
      output.append(String.format("ind1: %s%n", getInd1()));

    if (definition != null && definition.getInd2() != null && definition.getInd2().exists())
      output.append(String.format("%s: %s%n", definition.getInd2().getLabel(), resolveInd2()));
    else if (StringUtils.isNotBlank(getInd2()))
      output.append(String.format("ind2: %s%n", getInd2()));

    for (MarcSubfield subfield : subfields) {
      output.append(String.format("%s: %s%n", subfield.getLabel(), subfield.resolve()));
    }

    return output.toString();
  }

  public String formatAsText() {
    StringBuilder output = new StringBuilder();
    output.append(getTag());
    output.append(" ").append(ind1).append(ind2).append(" ");

    boolean first = true;
    for (MarcSubfield subfield : subfields) {
      if (!first)
        output.append("       ");
      output.append("$").append(subfield.getCode()).append(" ").append(subfield.getValue()).append("\n");
      first = false;
    }

    return output.toString();
  }

  public String formatAsMarc() {
    StringBuilder output = new StringBuilder();

    if (definition != null && definition.getInd1().exists())
      output.append(String.format("%s_ind1: %s%n", getTag(), resolveInd1()));

    if (definition != null && definition.getInd2().exists())
      output.append(String.format("%s_ind2: %s%n", getTag(), resolveInd2()));

    for (MarcSubfield subfield : subfields) {
      output.append(String.format("%s_%s: %s%n", getTag(), subfield.getCode(), subfield.resolve()));
    }

    return output.toString();
  }

  public String formatForIndex() {
    StringBuilder output = new StringBuilder();

    if (definition.getInd1().exists())
      output.append(String.format("%s_ind1: %s%n", definition.getIndexTag(), resolveInd1()));

    if (definition.getInd2().exists())
      output.append(String.format("%s_ind2: %s%n", definition.getIndexTag(), resolveInd2()));

    for (MarcSubfield subfield : subfields) {
      String subfieldOutput = formatSubfieldForIndex(subfield);
      output.append(subfieldOutput);
    }

    return output.toString();
  }

  private String formatSubfieldForIndex(MarcSubfield subfield) {
    StringBuilder output = new StringBuilder();

    String code = subfield.getCodeForIndex();

    // If PICA, then code = code, otherwise code = '_' + code
    if (!bibliographicRecord.getSchemaType().equals(SchemaType.PICA) && !code.isEmpty()) {
      code = "_" + code;
    }

    output.append(String.format("%s%s: %s%n", definition.getIndexTag(), code, subfield.resolve()));
    if (subfield.getDefinition() == null || !subfield.getDefinition().hasContentParser()) {
      return output.toString();
    }
    Map<String, String> extra = subfield.parseContent();
    if (extra == null) {
      return output.toString();
    }

    for (Map.Entry<String, String> entry : extra.entrySet()) {
      output.append(String.format(
        "%s%s_%s: %s%n",
        definition.getIndexTag(), code, entry.getKey(), entry.getValue()
      ));
    }

    return output.toString();
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
  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type,
                                                    MarcVersion marcVersion) {
    Map<String, List<String>> pairs = new HashMap<>();

    // If the field is PICA and has a definition, use the definition's tag
    // Otherwise, use the tag found in the field
    determineTag();

    SchemaType schemaType = bibliographicRecord != null ? bibliographicRecord.getSchemaType() : SchemaType.MARC21;
    DataFieldKeyGenerator keyGenerator = DataFieldKeyGeneratorFactory.create(type, definition, tag, schemaType);
    keyGenerator.setMarcVersion(marcVersion);

    // Indicator 1
    boolean hasInd1def = (definition != null && definition.getInd1() != null && definition.getInd1().exists());
    if (hasInd1def || !getInd1().equals(" ")) {
      String value = resolveInd1();
      pairs.put(keyGenerator.forInd1(), Collections.singletonList(value));
    }

    // ind2
    boolean hasInd2def = (definition != null && definition.getInd2() != null && definition.getInd2().exists());
    if (hasInd2def || !getInd2().equals(" ")) {
      String value = resolveInd2();
      pairs.put(keyGenerator.forInd2(), Collections.singletonList(value));
    }

    // subfields
    for (MarcSubfield subfield : subfields) {
      Utils.mergeMap(pairs, subfield.getKeyValuePairs(keyGenerator));
    }

    // Index fields that have SourceSpecificationType defined (which also means a FieldIndexer)
    indexFieldsWithSchemas(pairs, keyGenerator);

    // Full field indexing for authority fields
    indexAllAuthoritySubfields(pairs, keyGenerator);

    // Full field indexing for subject fields
    indexClassifications(schemaType, pairs, keyGenerator);

    return pairs;
  }

  /**
   * Sets the value of the tag field based on the definition, schema type, and occurrence. That tag will later be
   * used to generate keys for the index.
   */
  private void determineTag() {
    if (bibliographicRecord == null || !bibliographicRecord.getSchemaType().equals(SchemaType.PICA) || definition == null) {
      tag = getTag();
      if (getOccurrence() != null) {
        tag += "/" + getOccurrence();
      }
      return;
    }

    PicaFieldDefinition picaDefinition = (PicaFieldDefinition) definition;
    tag = picaDefinition.getTag();
    if (picaDefinition.getCounter() != null) {
      tag += "_" + picaDefinition.getCounter();
    } else if (picaDefinition.getOccurrence() != null) {
      tag += "_" + picaDefinition.getOccurrence();
    }
  }

  private void indexClassifications(SchemaType schemaType,
                                    Map<String, List<String>> pairs,
                                    DataFieldKeyGenerator keyGenerator) {
    String fieldTag = schemaType.equals(SchemaType.PICA) ? this.getTagWithOccurrence() : this.getTag();

    if (bibliographicRecord == null || !bibliographicRecord.isSubjectTag(fieldTag)) {
      return;
    }

    List<String> subfieldValues = new ArrayList<>();
    for (MarcSubfield subfield : subfields) {
      if (bibliographicRecord.isSkippableSubjectSubfield(fieldTag, subfield.getCode())) {
        continue;
      }
      String value = subfield.getValue();
      subfieldValues.add(value);
    }
    String key = keyGenerator.forFullField();
    String value = StringUtils.join(subfieldValues, ", ");

    pairs.putIfAbsent(key, new ArrayList<>());
    pairs.get(key).add(value);
  }

  private void indexAllAuthoritySubfields(Map<String, List<String>> pairs, DataFieldKeyGenerator keyGenerator) {
    String fieldTag = getTag();
    if (bibliographicRecord == null || !bibliographicRecord.isAuthorityTag(fieldTag)) {
      return;
    }

    List<String> subfieldValues = new ArrayList<>();
    // This gets all subfield values in the field and concatenates them
    // If the field is PICA, then the value of subfield E is concatenated with the value of subfield M
    for (MarcSubfield subfield : subfields) {
      String value = indexAuthorityNameFullSomethingWhatever(fieldTag, subfield);
      if (value != null) {
        subfieldValues.add(value);
      }
    }

    String key = keyGenerator.forFullField();
    String value = StringUtils.join(subfieldValues, ", ");

    pairs.putIfAbsent(key, new ArrayList<>());
    pairs.get(key).add(value);
  }

  private String indexAuthorityNameFullSomethingWhatever(String fieldTag, MarcSubfield subfield) {

    if (bibliographicRecord.isSkippableAuthoritySubfield(fieldTag, subfield.getCode())) {
      return null;
    }

    String value = subfield.getValue();
    if (!bibliographicRecord.getSchemaType().equals(SchemaType.PICA)) {
      return value;
    }

    if (!subfield.getCode().equals("E") && subfield.getCode().equals("M") && subfieldIndex.containsKey("E")) {
        return null;
    }

    value += "-";
    if (subfieldIndex.containsKey("M")) {
      value += subfieldIndex.get("M").get(0).getValue();
    }
    return value;
  }

  private void indexFieldsWithSchemas(Map<String, List<String>> pairs, DataFieldKeyGenerator keyGenerator) {
    List<FieldIndexer> fieldWithSchemaIndexers = getFieldIndexers();

    if (fieldWithSchemaIndexers == null || fieldWithSchemaIndexers.isEmpty()) {
      return;
    }

    try {
      for (FieldIndexer indexer : getFieldIndexers()) {
        Map<String, List<String>> extra = indexer.index(this, keyGenerator);
        Utils.mergeMap(pairs, extra);
      }
    } catch (IllegalArgumentException e) {
      logger.log(Level.SEVERE, "{0} in record {1} {2}", new Object[]{e.getLocalizedMessage(), bibliographicRecord.getId(), this});
    }
  }

  private FieldIndexer getFieldIndexer() {
    FieldIndexer fieldIndexer = null;

    // If the Schema is UNIMARC and the field is either a classification (subject) or an authority field,
    // then use the subfield 2 to determine the schema
    boolean isUnimarc = bibliographicRecord != null && bibliographicRecord.getSchemaType().equals(SchemaType.UNIMARC);
    if (isUnimarc && (bibliographicRecord.isSubjectTag(getTag()) || bibliographicRecord.isAuthorityTag(getTag()))) {
        fieldIndexer = SchemaFromSubfield2.getInstance();
        return fieldIndexer;
    }

    // If there is no definition or no source specification type, return null
    if (definition == null || definition.getSourceSpecificationType() == null) {
      return null;
    }

    SourceSpecificationType specificationType = definition.getSourceSpecificationType();
    switch (specificationType) {
      case Indicator1Is7AndSubfield2:
        fieldIndexer = SchemaFromInd1OrIf7FromSubfield2.getInstance();
        break;
      case Indicator1IsSpaceAndSubfield2:
        fieldIndexer = SchemaFromInd1OrIfEmptyFromSubfield2.getInstance();
        break;
      case Indicator2AndSubfield2:
        fieldIndexer = SchemaFromInd2AndSubfield2.getInstance();
        break;
      case Indicator2For055AndSubfield2:
        fieldIndexer = SchemaFromInd2For055OrIf7FromSubfield2.getInstance();
        break;
      case Subfield2:
        fieldIndexer = SchemaFromSubfield2.getInstance();
        break;
      case Indicator2:
        fieldIndexer = SchemaFromInd2.getInstance();
        break;
    }
    return fieldIndexer;
  }

  public void addFieldIndexer(FieldIndexer indexer) {
    if (fieldIndexers == null)
      fieldIndexers = new ArrayList<>();
    if (indexer != null)
      fieldIndexers.add(indexer);
  }

  public List<FieldIndexer> getFieldIndexers() {
    if (!fieldIndexerInitialized) {
      addFieldIndexer(getFieldIndexer());
      fieldIndexerInitialized = true;
    }
    return fieldIndexers;
  }

  public String resolveInd1() {
    if (definition == null) {
      return ind1;
    }
    return resolveIndicator(definition.getInd1(), ind1);
  }

  public String resolveInd2() {
    if (definition == null) {
      return ind2;
    }
    return resolveIndicator(definition.getInd2(), ind2);
  }

  /**
   * Returns the label of the indicator if it can be found in the definition. Otherwise, returns the indicator code used.
   * @param indicatorDefinition The definition of the indicator.
   * @param indicator The indicator code.
   * @return The label or the indicator code depending on the existence of the indicator in the definition.
   */
  public String resolveIndicator(Indicator indicatorDefinition, String indicator) {
    if (indicatorDefinition == null) {
      return indicator;
    }

    if (!indicatorDefinition.exists()) {
      return indicator;
    }

    if (!indicatorDefinition.hasCode(indicator)) {
      return indicator;
    }

    EncodedValue indCode = indicatorDefinition.getCode(indicator);
    assert(indCode != null);

    // If it's a range, return the label and the indicator
    // For instance, code="0-9" -> "Number of parts: 3"
    if (indCode.isRange()) {
      return indCode.getLabel() + ": " + indicator;
    }

    return indCode.getLabel();
  }

  public String getTag() {
    return (definition != null)
      ? definition.getTag()
      : tag;
  }

  public String getInd1() {
    return ind1;
  }

  public String getInd2() {
    return ind2;
  }

  public List<MarcSubfield> getSubfield(String code) {
    return subfieldIndex.getOrDefault(code, null);
  }

  public List<MarcSubfield> getSubfields() {
    return subfields;
  }

  /**
   * Sets subfields without indexing them
   * @param subfields Subfields to set
   */
  public void setSubfields(List<MarcSubfield> subfields) {
    this.subfields = subfields;
  }

  public DataFieldDefinition getDefinition() {
    return definition;
  }

  public DataFieldKeyGenerator getKeyGenerator(SolrFieldType type) {
    return DataFieldKeyGeneratorFactory.create(type, getDefinition());
  }

  public void addUnhandledSubfields(String code) {
    if (unhandledSubfields == null)
      unhandledSubfields = new ArrayList<>();
    unhandledSubfields.add(code);
  }

  public List<String> getUnhandledSubfields() {
    return unhandledSubfields;
  }

  public String getOccurrence() {
    return occurrence;
  }

  public void setOccurrence(String occurrence) {
    this.occurrence = occurrence;
  }

  public String getTagWithOccurrence() {
    if (occurrence == null) {
      return getTag();
    }
    return getTag() + "/" + occurrence;
  }

  @Override
  public String toString() {

    String dataFieldTag;

    if (StringUtils.isNotBlank(tag)) {
      dataFieldTag = tag;
    } else if (definition != null) {
      dataFieldTag = definition.getTag();
    } else {
      dataFieldTag = "unknown";
    }

    return "DataField{"
      + dataFieldTag
      + ", ind1='" + ind1 + '\''
      + ", ind2='" + ind2 + '\''
      + ", subfields=" + subfields
      + '}';
  }
}
