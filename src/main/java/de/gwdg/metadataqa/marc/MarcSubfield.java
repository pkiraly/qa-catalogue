package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.general.Linkage;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MarcSubfield implements Serializable { // Validatable

  private static final Logger logger = Logger.getLogger(MarcSubfield.class.getCanonicalName());
  private static Map<String, String> prefixCache;
  private BibliographicRecord marcRecord;
  private DataField field;
  private SubfieldDefinition definition;
  private final String code;
  private final String value;
  private String codeForIndex = null;
  private Linkage linkage;
  private String referencePath;


  public MarcSubfield(SubfieldDefinition definition, String code, String value) {
    this.definition = definition;
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public DataField getField() {
    return field;
  }

  public void setField(DataField field) {
    this.field = field;
  }

  public Linkage getLinkage() {
    return linkage;
  }

  public void setLinkage(Linkage linkage) {
    this.linkage = linkage;
  }

  public String getReferencePath() {
    return referencePath;
  }

  public void setReferencePath(String referencePath) {
    this.referencePath = referencePath;
  }

  public String getLabel() {
    String label = code;
    if (definition != null && definition.getLabel() != null)
      label = definition.getLabel();
    return label;
  }

  /**
   * Resolve the subfield value.
   * @return Either the original value of the subfield or its label.
   */
  public String resolve() {
    if (definition == null)
      return value;

    return definition.resolve(value);
  }

  public List<String> split() {
    if (definition == null || !definition.hasContentSplitter())
      return List.of(value);

    return definition.getContentSplitter().parse(value);
  }

  public SubfieldDefinition getDefinition() {
    return definition;
  }

  public void setDefinition(SubfieldDefinition definition) {
    this.definition = definition;
  }

  public BibliographicRecord getMarcRecord() {
    return marcRecord;
  }

  public void setMarcRecord(BibliographicRecord marcRecord) {
    this.marcRecord = marcRecord;
  }

  /**
   * Get the index code for this subfield. If there's no definition, the code is just the subfield code.
   * If there's a definition, the code is the index code from the definition.
   * E.g. no definition: "a" -> "_a", definition: see SubfieldDefinition.getCodeForIndex()
   * @see SubfieldDefinition#getCodeForIndex()
   * @return The index code for this subfield.
   */
  public String getCodeForIndex() {
    if (codeForIndex != null) {
      return codeForIndex;
    }
    codeForIndex = code;
    if (definition != null && definition.getCodeForIndex() != null) {
      codeForIndex = definition.getCodeForIndex();
    }
    return codeForIndex;
  }

  public Map<String, String> parseContent() {
    if (!definition.hasContentParser()) {
      return Collections.emptyMap();
    }

    try {
      return definition.getContentParser().parse(value);
    } catch (ParserException e) {
      var msg = String.format(
        "Error in record: '%s' %s$%s: '%s'. Error message: '%s'",
        marcRecord.getId(), field.getTag(), definition.getCode(), value, e.getMessage()
      );
      logger.severe(msg);
    }

    return Collections.emptyMap();
  }

  public Map<String, List<String>> getKeyValuePairs(DataFieldKeyGenerator keyGenerator) {
    if (prefixCache == null) {
      prefixCache = new HashMap<>();
    }

    String tagForCache = this.getField().getTag();
    if (this.getField().getOccurrence() != null) {
      tagForCache += "/" + this.getField().getOccurrence();
    }

    String cacheKey = String.format(
      "%s$%s-%s-%s",
      tagForCache, code, keyGenerator.getClass().getSimpleName(), keyGenerator.getMarcVersion());
    if (!prefixCache.containsKey(cacheKey)) {
      String generatedPrefix = keyGenerator.forSubfield(this);
      prefixCache.put(cacheKey, generatedPrefix);
    }
    String prefix = prefixCache.get(cacheKey);

    Map<String, List<String>> pairs = new HashMap<>();
    if (definition != null && definition.hasContentSplitter()) {
      List<String> items = new ArrayList<>();
      for (String item : split())
        items.add(definition.resolve(item));

      pairs.put(prefix, items);
    } else
      pairs.put(prefix, new ArrayList<>(List.of(resolve())));

    if (getDefinition() == null) {
      return pairs;
    }

    getKeyValuePairsForPositionalSubfields(pairs, prefix);
    getKeyValuePairsFromContentParser(keyGenerator, pairs);
    return pairs;
  }

  private void getKeyValuePairsFromContentParser(DataFieldKeyGenerator keyGenerator,
                                                 Map<String,
                                                 List<String>> pairs) {
    if (!getDefinition().hasContentParser()) {
      return;
    }

    Map<String, String> extra = parseContent();
    if (extra == null) {
      return;
    }

    for (Map.Entry<String, String> entry : extra.entrySet()) {
      pairs.put(
        String.format("%s_%s", keyGenerator.forSubfield(this), entry.getKey()),
        new ArrayList<>(List.of(entry.getValue()))
      );
    }
  }

  private void getKeyValuePairsForPositionalSubfields(Map<String, List<String>> pairs, String prefix) {
    if (!getDefinition().hasPositions()) {
      return;
    }

    Map<String, String> extra = getDefinition().resolvePositional(getValue());
    for (Map.Entry<String, String> entry : extra.entrySet()) {
      pairs.put(prefix + "_" + entry.getKey(), new ArrayList<>(List.of(entry.getValue())));
    }
  }

  @Override
  public String toString() {
    return "MarcSubfield{" +
            "code='" + code + '\'' +
            ", value='" + value + '\'' +
            '}';
  }
}
