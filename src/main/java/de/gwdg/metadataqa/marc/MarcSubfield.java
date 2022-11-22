package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.general.Linkage;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.definition.structure.SubfieldDefinition;
import de.gwdg.metadataqa.marc.model.validation.ErrorsCollector;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.*;

public class MarcSubfield implements Serializable { // Validatable

  private static final Logger logger = Logger.getLogger(MarcSubfield.class.getCanonicalName());

  private BibliographicRecord marcRecord;
  private DataField field;
  private SubfieldDefinition definition;
  private final String code;
  private final String value;
  private String codeForIndex = null;
  private ErrorsCollector errors = null;
  private Linkage linkage;
  private String referencePath;
  private static Map<String, String> prefixCache;

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

  public String resolve() {
    if (definition == null)
      return value;

    return definition.resolve(value);
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

  public String getCodeForIndex() {
    if (codeForIndex == null) {
      codeForIndex = "_" + code;
      if (definition != null && definition.getCodeForIndex() != null) {
        codeForIndex = definition.getCodeForIndex();
      }
    }
    return codeForIndex;
  }

  public Map<String, String> parseContent() {
    if (definition.hasContentParser())
      try {
        return definition.getContentParser().parse(value);
      } catch (ParserException e) {
        var msg = String.format(
          "Error in record: '%s' %s$%s: '%s'. Error message: '%s'",
          marcRecord.getId(), field.getTag(), definition.getCode(), value, e.getMessage()
        );
        logger.severe(msg);
      }

    return null;
  }

  public Map<String, List<String>> getKeyValuePairs(DataFieldKeyGenerator keyGenerator) {
    if (prefixCache == null) {
      prefixCache = new HashMap<>();
    }

    String cacheKey = String.format("%s$%s-%s-%s", this.getField().getTag(), code, keyGenerator.getType().getType(), keyGenerator.getMarcVersion());
    if (!prefixCache.containsKey(cacheKey))
      prefixCache.put(cacheKey, keyGenerator.forSubfield(this));
    String prefix = prefixCache.get(cacheKey);

    Map<String, List<String>> pairs = new HashMap<>();
    pairs.put(prefix, Collections.singletonList(resolve()));
    if (getDefinition() != null) {
      getKeyValuePairsForPositionalSubfields(pairs, prefix);
      getKeyValuePairsFromContentParser(keyGenerator, pairs);
    }

    return pairs;
  }

  private void getKeyValuePairsFromContentParser(DataFieldKeyGenerator keyGenerator, Map<String, List<String>> pairs) {
    if (getDefinition().hasContentParser()) {
      Map<String, String> extra = parseContent();
      if (extra != null) {
        for (Map.Entry<String, String> entry : extra.entrySet()) {
          pairs.put(
            keyGenerator.forSubfield(this, entry.getKey()),
            Collections.singletonList(entry.getValue())
          );
        }
      }
    }
  }

  private void getKeyValuePairsForPositionalSubfields(Map<String, List<String>> pairs, String prefix) {
    if (getDefinition().hasPositions()) {
      Map<String, String> extra = getDefinition().resolvePositional(getValue());
      for (Map.Entry<String, String> entry : extra.entrySet()) {
        pairs.put(prefix + "_" + entry.getKey(), Collections.singletonList(entry.getValue()));
      }
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
