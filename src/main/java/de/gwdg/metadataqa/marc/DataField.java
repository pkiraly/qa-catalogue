package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.Linkage;
import de.gwdg.metadataqa.marc.definition.general.indexer.FieldIndexer;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.*;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ErrorsCollector;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorType.*;

public class DataField implements Extractable, Validatable, Serializable {

  private static final Logger logger = Logger.getLogger(DataField.class.getCanonicalName());

  private DataFieldDefinition definition;
  private String tag;
  private String ind1;
  private String ind2;
  private List<MarcSubfield> subfields;
  private Map<String, List<MarcSubfield>> subfieldIndex = new LinkedHashMap<>();
  private ErrorsCollector errors = null;
  private List<String> unhandledSubfields = null;
  private MarcRecord record;

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
    if (subfields != null) {
      for (Map<String, String> subfield : subfields) {
        String code = subfield.get("code");
        String value = subfield.get("content");
        SubfieldDefinition subfieldDefinition = definition.getSubfield(code);
        if (subfieldDefinition == null) {
          if (!(definition.getTag().equals("886") && code.equals("k")) && !definition.getTag().equals("936"))
            System.err.printf("no definition for %s$%s (value: '%s') %s %s%n",
              definition.getTag(), code, value, definition.getTag().equals("886"),
              code.equals("k"));
        } else {
          MarcSubfield marcSubfield = new MarcSubfield(subfieldDefinition, code, value);
          marcSubfield.setField(this);
          this.subfields.add(marcSubfield);
          indexSubfield(code, marcSubfield);
        }
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

  private void parseAndAddSubfields(String content) {
    for (String[] sf : parseSubfields(content))
      addSubfield(sf[0], sf[1]);
  }

  public static List<String[]> parseSubfields(String content) {
    List<String[]> subfields = new ArrayList<>();

    boolean codeFlag = false;
    String code = null;
    StringBuffer value = new StringBuffer();
    for (int i = 0; i < content.length(); i++) {
      String c = Character.toString(content.charAt(i));
      if (c.equals("$")) {
        codeFlag = true;
        if (code != null)
          subfields.add(new String[]{code, value.toString()});
        code = null;
        value = new StringBuffer();
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

  public MarcRecord getRecord() {
    return record;
  }

  public void setRecord(MarcRecord record) {
    this.record = record;
    for (MarcSubfield marcSubfield : subfields)
      marcSubfield.setRecord(record);
  }

  public void indexSubfields() {
    for (MarcSubfield marcSubfield : subfields)
      indexSubfield(marcSubfield.getCode(), marcSubfield);
  }

  private void indexSubfield(String code, MarcSubfield marcSubfield) {
    if (!subfieldIndex.containsKey(code))
      subfieldIndex.put(code, new LinkedList<>());
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
    SubfieldDefinition subfieldDefinition = definition != null ? definition.getSubfield(code) : null;
    MarcSubfield marcSubfield = new MarcSubfield(subfieldDefinition, code, value);
    marcSubfield.setField(this);
    this.subfields.add(marcSubfield);
    indexSubfield(code, marcSubfield);
  }

  public Map<String, List<String>> getHumanReadableMap() {
    Map<String, List<String>> map = new LinkedHashMap<>();
    if (definition.getInd1().exists())
      map.put(definition.getInd1().getLabel(), Arrays.asList(resolveInd1()));
    if (definition.getInd2().exists())
      map.put(definition.getInd2().getLabel(), Arrays.asList(resolveInd2()));
    for (MarcSubfield subfield : subfields) {
      if (!map.containsKey(subfield.getLabel()))
        map.put(subfield.getLabel(), new ArrayList<>());
      map.get(subfield.getLabel()).add(subfield.resolve());
    }
    return map;
  }

  public String simpleFormat() {
    StringBuffer output = new StringBuffer();

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
    StringBuffer output = new StringBuffer();
    output.append(String.format("[%s: %s]%n", definition.getTag(), definition.getLabel()));

    if (definition.getInd1().exists())
      output.append(String.format("%s: %s%n", definition.getInd1().getLabel(), resolveInd1()));

    if (definition.getInd2().exists())
      output.append(String.format("%s: %s%n", definition.getInd2().getLabel(), resolveInd2()));

    for (MarcSubfield subfield : subfields) {
      output.append(String.format("%s: %s%n", subfield.getLabel(), subfield.resolve()));
    }

    return output.toString();
  }

  public String formatAsMarc() {
    StringBuffer output = new StringBuffer();

    if (definition.getInd1().exists())
      output.append(String.format("%s_ind1: %s%n", definition.getTag(), resolveInd1()));

    if (definition.getInd2().exists())
      output.append(String.format("%s_ind2: %s%n", definition.getTag(), resolveInd2()));

    for (MarcSubfield subfield : subfields) {
      output.append(String.format("%s_%s: %s%n", definition.getTag(), subfield.getCode(), subfield.resolve()));
    }

    return output.toString();
  }

  public String formatForIndex() {
    StringBuffer output = new StringBuffer();

    if (definition.getInd1().exists())
      output.append(String.format("%s_ind1: %s%n", definition.getIndexTag(), resolveInd1()));

    if (definition.getInd2().exists())
      output.append(String.format("%s_ind2: %s%n", definition.getIndexTag(), resolveInd2()));

    for (MarcSubfield subfield : subfields) {
      String code = subfield.getCodeForIndex();
      output.append(String.format("%s%s: %s%n", definition.getIndexTag(), code, subfield.resolve()));
      if (subfield.getDefinition() != null && subfield.getDefinition().hasContentParser()) {
        Map<String, String> extra = subfield.parseContent();
        if (extra != null) {
          for (Map.Entry<String, String> entry : extra.entrySet()) {
            output.append(String.format(
              "%s%s_%s: %s%n",
              definition.getIndexTag(), code, entry.getKey(), entry.getValue()
            ));
          }
        }
      }
    }

    return output.toString();
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs() {
    return getKeyValuePairs(SolrFieldType.MARC);
  }

  @Override
  public Map<String, List<String>> getKeyValuePairs(SolrFieldType type) {
    Map<String, List<String>> pairs = new HashMap<>();

    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(
      definition, type, getTag()
    );

    if (definition != null && definition.getInd1().exists()) {
      pairs.put(keyGenerator.forInd1(), Arrays.asList(resolveInd1()));
    } else if (getInd1() != null) {
      pairs.put(keyGenerator.forInd1(), Arrays.asList(getInd1()));
    }

    if (definition != null && definition.getInd2().exists()) {
      pairs.put(keyGenerator.forInd2(), Arrays.asList(resolveInd2()));
    }

    for (MarcSubfield subfield : subfields) {
      pairs.putAll(subfield.getKeyValuePairs(keyGenerator));
    }

    if (getFieldIndexer() != null) {
      try {
        Map<String, List<String>> extra = getFieldIndexer().index(this, keyGenerator);
        pairs.putAll(extra);
      } catch (IllegalArgumentException e) {
        logger.severe(String.format(
          "%s  in record %s %s",
          e.getLocalizedMessage(), record.getId(), this.toString()
        ));
      }
    }

    return pairs;
  }

  public FieldIndexer getFieldIndexer() {
    FieldIndexer fieldIndexer = null;
    if (definition != null && definition.getFieldIndexer() != null) {
      fieldIndexer = definition.getFieldIndexer();
    } else if (definition != null
      && definition.getSourceSpecificationType() != null) {
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
      }
    }
    return fieldIndexer;
  }

  public String resolveInd1() {
    return resolveIndicator(definition.getInd1(), ind1);
  }

  public String resolveInd2() {
    return resolveIndicator(definition.getInd2(), ind2);
  }

  public String resolveIndicator(Indicator indicatorDefinition, String indicator) {
    if (indicatorDefinition.getLabel().equals(""))
      return "";

    if (!indicatorDefinition.hasCode(indicator))
      return indicator;

    Code indCode = indicatorDefinition.getCode(indicator);
    assert(indCode != null);
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

  public List<MarcSubfield> parseSubfields() {
    return subfields;
  }

  public DataFieldDefinition getDefinition() {
    return definition;
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    boolean isValid = true;
    errors = new ErrorsCollector();
    DataFieldDefinition referencerDefinition = null;
    List<MarcSubfield> _subfields = null;
    boolean ambiguousLinkage = false;

    if (marcVersion == null)
      marcVersion = MarcVersion.MARC21;

    if (TagDefinitionLoader.load(definition.getTag(), marcVersion) == null) {
      addError(FIELD_UNDEFINED, "");
      return false;
    }

    if (getTag().equals("880")) {
      List<MarcSubfield> subfield6s = getSubfield("6");
      if (subfield6s == null) {
        addError(FIELD_MISSING_REFERENCE_SUBFIELD, "$6");
        isValid = false;
      } else {
        if (!subfield6s.isEmpty()) {
          if (subfield6s.size() != 1) {
            addError(definition.getTag() + "$6", RECORD_AMBIGUOUS_LINKAGE, "There are multiple $6");
            isValid = false;
            ambiguousLinkage = true;
          } else {
            MarcSubfield subfield6 = subfield6s.get(0);
            Linkage linkage = null;
            try {
              linkage = LinkageParser.getInstance().create(subfield6.getValue());
              if (linkage == null || linkage.getLinkingTag() == null) {
                String message = String.format("Unparseable reference: '%s'", subfield6.getValue());
                addError(RECORD_INVALID_LINKAGE, message);
              } else {
                referencerDefinition = definition;
                definition = TagDefinitionLoader.load(linkage.getLinkingTag(), marcVersion);

                if (definition == null) {
                  definition = referencerDefinition;
                  String message = String.format("refers to field %s, which is not defined",
                    linkage.getLinkingTag());
                  addError(definition.getTag() + "$6", RECORD_INVALID_LINKAGE, message);
                  isValid = false;
                } else {
                  _subfields = subfields;
                  List<MarcSubfield> _subfieldsNew = new ArrayList<>();
                  for (MarcSubfield subfield : subfields) {
                    MarcSubfield alternativeSubfield = new MarcSubfield(
                      definition.getSubfield(subfield.getCode()),
                      subfield.getCode(),
                      subfield.getValue()
                    );
                    alternativeSubfield.setField(this);
                    alternativeSubfield.setRecord(record);
                    alternativeSubfield.setLinkage(linkage);
                    alternativeSubfield.setReferencePath(referencerDefinition.getTag());
                    _subfieldsNew.add(alternativeSubfield);
                  }
                  subfields = _subfieldsNew;
                }
              }
            } catch (ParserException e) {
              addError(definition.getTag() + "$6", RECORD_INVALID_LINKAGE, e.getMessage());
            }
          }
        }
      }
    }

    if (unhandledSubfields != null) {
      addError(SUBFIELD_UNDEFINED, StringUtils.join(unhandledSubfields, ", "));
      isValid = false;
    }

    if (ind1 != null) {
      if (!validateIndicator(definition.getInd1(), ind1, marcVersion, referencerDefinition))
        isValid = false;
    }

    if (ind2 != null) {
      if (!validateIndicator(definition.getInd2(), ind2, marcVersion, referencerDefinition))
        isValid = false;
    }

    if (!ambiguousLinkage) {
      Map<SubfieldDefinition, Integer> counter = new HashMap<>();
      for (MarcSubfield subfield : subfields) {
        if (subfield.getDefinition() == null) {
          if (definition.isVersionSpecificSubfields(marcVersion, subfield.getCode())) {
            subfield.setDefinition(
              definition.getVersionSpecificSubfield(
                marcVersion, subfield.getCode()));
          } else {
            addError(SUBFIELD_UNDEFINED, subfield.getCode());
            isValid = false;
            continue;
          }
        }
        Utils.count(subfield.getDefinition(), counter);
        /*
        if (!counter.containsKey(subfield.getDefinition())) {
          counter.put(subfield.getDefinition(), 0);
        }
        counter.put(subfield.getDefinition(), counter.get(subfield.getDefinition()) + 1);
         */

        if (!subfield.validate(marcVersion)) {
          errors.addAll(subfield.getValidationErrors());
          isValid = false;
        }
      }

      for (Map.Entry<SubfieldDefinition, Integer> entry : counter.entrySet()) {
        SubfieldDefinition subfieldDefinition = entry.getKey();
        Integer count = entry.getValue();
        if (count > 1
            && subfieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
          addError(subfieldDefinition, SUBFIELD_NONREPEATABLE,
            String.format("there are %d instances", count));
          isValid = false;
        }
      }
    }

    if (referencerDefinition != null)
      definition = referencerDefinition;
    if (_subfields != null)
      subfields = _subfields;

    return isValid;
  }

  private boolean validateIndicator(Indicator indicatorDefinition,
                                    String value,
                                    MarcVersion marcVersion,
                                    DataFieldDefinition referencerDefinition) {
    boolean isValid = true;
    String path = indicatorDefinition.getPath();
    if (referencerDefinition != null)
      path = String.format("%s->%s", referencerDefinition.getTag(), path);

    if (indicatorDefinition.exists()) {
      if (!indicatorDefinition.hasCode(value)) {
        if (!indicatorDefinition.isVersionSpecificCode(marcVersion, value)) {
          isValid = false;
          if (indicatorDefinition.isHistoricalCode(value)) {
            addError(path, INDICATOR_OBSOLETE, value);
          } else {
            addError(path, INDICATOR_INVALID_VALUE, value);
          }
        }
      }
    } else {
      if (!value.equals(" ")) {
        if (!indicatorDefinition.isVersionSpecificCode(marcVersion, value)) {
          addError(path, INDICATOR_NON_EMPTY, value);
          isValid = false;
        }
      }
    }
    return isValid;
  }

  public DataFieldKeyGenerator getKeyGenerator(SolrFieldType type) {
    return new DataFieldKeyGenerator(getDefinition(), type);
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return errors.getErrors();
  }

  public void addUnhandledSubfields(String code) {
    if (unhandledSubfields == null)
      unhandledSubfields = new ArrayList<>();
    unhandledSubfields.add(code);
  }

  private void addError(ValidationErrorType type, String message) {
    addError(definition.getTag(), type, message);
  }

  private void addError(SubfieldDefinition subfieldDefinition, ValidationErrorType type, String message) {
    addError(subfieldDefinition.getPath(), type, message);
  }

  private void addError(String path, ValidationErrorType type, String message) {
    String url = definition.getDescriptionUrl();
    errors.add(record.getId(), path, type, message, url);
  }

  @Override
  public String toString() {
    return "DataField{"
      + definition.getTag()
      + ", ind1='" + ind1 + '\''
      + ", ind2='" + ind2 + '\''
      + ", subfields=" + subfields
      + '}';
  }
}
