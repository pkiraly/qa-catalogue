package de.gwdg.metadataqa.marc;

import de.gwdg.metadataqa.marc.definition.*;
import de.gwdg.metadataqa.marc.definition.general.Linkage;
import de.gwdg.metadataqa.marc.definition.general.parser.LinkageParser;
import de.gwdg.metadataqa.marc.definition.general.parser.ParserException;
import de.gwdg.metadataqa.marc.model.SolrFieldType;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.keygenerator.DataFieldKeyGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

public class DataField implements Extractable, Validatable, Serializable {

  private static final Logger logger = Logger.getLogger(DataField.class.getCanonicalName());

  private DataFieldDefinition definition;
  private String ind1;
  private String ind2;
  private List<MarcSubfield> subfields;
  private Map<String, List<MarcSubfield>> subfieldIndex = new LinkedHashMap<>();
  private List<String> errors = null;
  private List<ValidationError> validationErrors = null;
  private List<String> unhandledSubfields = null;
  private MarcRecord record;

  public <T extends DataFieldDefinition> DataField(T definition, String ind1, String ind2) {
    this.definition = definition;
    this.ind1 = ind1;
    this.ind2 = ind2;
    this.subfields = new ArrayList<>();
  }

  public <T extends DataFieldDefinition> DataField(T definition, String ind1, String ind2,
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

  public <T extends DataFieldDefinition> DataField(T definition, String ind1, String ind2, String... subfields) {
    this(definition, ind1, ind2);
    if (subfields != null) {
      parseSubfieldArray(definition, subfields);
    }
  }

  private <T extends DataFieldDefinition> void parseSubfieldArray(T definition, String[] subfields) {
    for (int i = 0; i < subfields.length; i += 2) {
      String code = subfields[i];
      String value = subfields[i + 1];
      SubfieldDefinition subfieldDefinition = definition.getSubfield(code);
      MarcSubfield marcSubfield = new MarcSubfield(subfieldDefinition, code, value);
      marcSubfield.setField(this);
      this.subfields.add(marcSubfield);
      indexSubfield(code, marcSubfield);
    }
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
    DataFieldKeyGenerator keyGenerator = new DataFieldKeyGenerator(definition, type);

    if (definition.getInd1().exists())
      pairs.put(keyGenerator.forInd1(), Arrays.asList(resolveInd1()));

    if (definition.getInd2().exists()) {
      pairs.put(keyGenerator.forInd2(), Arrays.asList(resolveInd2()));
    }

    for (MarcSubfield subfield : subfields) {
      pairs.put(keyGenerator.forSubfield(subfield), Arrays.asList(subfield.resolve()));

      if (subfield.getDefinition() != null
          && subfield.getDefinition().hasContentParser()) {
        Map<String, String> extra = subfield.parseContent();
        if (extra != null) {
          for (Map.Entry<String, String> entry : extra.entrySet()) {
            pairs.put(
              keyGenerator.forSubfield(subfield, entry.getKey()),
              Arrays.asList(entry.getValue())
            );
          }
        }
      }
    }

    if (definition.getFieldIndexer() != null) {
      try {
        Map<String, List<String>> extra = definition.getFieldIndexer().index(this, keyGenerator);
        pairs.putAll(extra);
      } catch (IllegalArgumentException e) {
        logger.severe(this.toString() + ": " + e.getLocalizedMessage());
      }
    }

    return pairs;
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
    return definition.getTag();
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

  public DataFieldDefinition getDefinition() {
    return definition;
  }

  @Override
  public boolean validate(MarcVersion marcVersion) {
    boolean isValid = true;
    errors = new ArrayList<>();
    validationErrors = new ArrayList<>();
    DataFieldDefinition referencerDefinition = null;
    List<MarcSubfield> _subfields = null;
    boolean ambiguousLinkage = false;
    if (definition.getTag().equals("880")) {
      List<MarcSubfield> subfield6s = getSubfield("6");
      if (subfield6s == null) {
        validationErrors.add(new ValidationError(record.getId(), definition.getTag(),
          ValidationErrorType.FIELD_MISSING_REFERENCE_SUBFIELD, "$6", definition.getDescriptionUrl()));
        errors.add(String.format("%s should have subfield $a (%s)", definition.getTag(), definition.getDescriptionUrl()));
        isValid = false;
      } else {
        if (!subfield6s.isEmpty()) {
          if (subfield6s.size() != 1) {
            validationErrors.add(
              new ValidationError(
                record.getId(), definition.getTag() + "$6",
                ValidationErrorType.RECORD_AMBIGUOUS_LINKAGE, "There are multiple $6",
                definition.getDescriptionUrl()
              )
            );
            isValid = false;
            ambiguousLinkage = true;
          } else {
            MarcSubfield subfield6 = subfield6s.get(0);
            Linkage linkage = null;
            try {
              linkage = LinkageParser.getInstance().create(subfield6.getValue());
              if (linkage == null || linkage.getLinkingTag() == null) {
                validationErrors.add(
                  new ValidationError(
                    record.getId(), definition.getTag() + "$6",
                    ValidationErrorType.RECORD_INVALID_LINKAGE,
                    String.format("Unparseable reference: '%s'", subfield6.getValue()),
                    definition.getDescriptionUrl()
                  )
                );
              } else {
                referencerDefinition = definition;
                definition = TagDefinitionLoader.load(linkage.getLinkingTag());
                if (definition == null) {
                  definition = referencerDefinition;
                  validationErrors.add(
                    new ValidationError(
                      record.getId(), definition.getTag() + "$6",
                      ValidationErrorType.RECORD_INVALID_LINKAGE,
                      String.format("refers to field %s, which is not defined", linkage.getLinkingTag()),
                      definition.getDescriptionUrl()));
                  errors.add(String.format("%s refers to field %s, which is not defined (%s)",
                    definition.getTag(), linkage.getLinkingTag(), definition.getDescriptionUrl()));
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
              validationErrors.add(
                new ValidationError(
                  record.getId(), definition.getTag() + "$6",
                  ValidationErrorType.RECORD_INVALID_LINKAGE, e.getMessage(),
                  definition.getDescriptionUrl()
                )
              );
            }
          }
        }
      }
    }

    if (unhandledSubfields != null) {
      validationErrors.add(new ValidationError(record.getId(), definition.getTag(),
        ValidationErrorType.SUBFIELD_UNDEFINED, StringUtils.join(unhandledSubfields, ", "),
        definition.getDescriptionUrl()));
      errors.add(String.format("%s has invalid %s: '%s' (%s)",
        definition.getTag(),
        (unhandledSubfields.size() == 1 ? "subfield" : "subfields"),
        StringUtils.join(unhandledSubfields, "', '"), definition.getDescriptionUrl()));
      isValid = false;
    }

    if (ind1 != null) {
      if (!validateIndicator("ind1", definition.getInd1(), ind1, marcVersion, referencerDefinition))
        isValid = false;
    }

    if (ind2 != null) {
      if (!validateIndicator("ind2", definition.getInd2(), ind2, marcVersion, referencerDefinition))
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
            validationErrors.add(
              new ValidationError(
                record.getId(), definition.getTag(),
                ValidationErrorType.SUBFIELD_UNDEFINED, subfield.getCode(), definition.getDescriptionUrl()));

            errors.add(String.format("%s has invalid subfield %s (%s)",
              definition.getTag(),
              subfield.getCode(),
              definition.getDescriptionUrl()));
            isValid = false;
            continue;
          }
        }
        if (!counter.containsKey(subfield.getDefinition())) {
          counter.put(subfield.getDefinition(), 0);
        }
        counter.put(subfield.getDefinition(), counter.get(subfield.getDefinition()) + 1);

        if (!subfield.validate(marcVersion)) {
          validationErrors.addAll(subfield.getValidationErrors());
          errors.addAll(subfield.getErrors());
          isValid = false;
        }
      }

      for (Map.Entry<SubfieldDefinition, Integer> entry : counter.entrySet()) {
        SubfieldDefinition subfieldDefinition = entry.getKey();
        Integer count = entry.getValue();
        if (count > 1
          && subfieldDefinition.getCardinality().equals(Cardinality.Nonrepeatable)) {
          validationErrors.add(new ValidationError(record.getId(), subfieldDefinition.getPath(),
            ValidationErrorType.SUBFIELD_NONREPEATABLE,
            String.format("there are %d instances", count),
            definition.getDescriptionUrl()));
          errors.add(String.format(
            "%s$%s is not repeatable, however there are %d instances (%s)",
            definition.getTag(), subfieldDefinition.getCode(),
            count, definition.getDescriptionUrl()));
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

  private boolean validateIndicator(String prefix, Indicator indicatorDefinition,
                                    String value, MarcVersion marcVersion,
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
            validationErrors.add(
              new ValidationError(
                record.getId(),
                path,
                ValidationErrorType.INDICATOR_OBSOLETE,
                value,
                definition.getDescriptionUrl()));
            errors.add(String.format("%s has obsolete code: '%s' (%s)",
              path, value, definition.getDescriptionUrl()));
          } else {
            validationErrors.add(new ValidationError(record.getId(), path,
              ValidationErrorType.INDICATOR_INVALID_VALUE, value, definition.getDescriptionUrl()));
            errors.add(String.format("%s has invalid code: '%s' (%s)",
              path, value, definition.getDescriptionUrl()));
          }
        }
      }
    } else {
      if (!value.equals(" ")) {
        if (!indicatorDefinition.isVersionSpecificCode(marcVersion, value)) {
          validationErrors.add(new ValidationError(record.getId(), path,
            ValidationErrorType.INDICATOR_NON_EMPTY, value, definition.getDescriptionUrl()));
          errors.add(String.format("%s should be empty, it has '%s' (%s)",
            path, value, definition.getDescriptionUrl()));
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
  public List<String> getErrors() {
    return errors;
  }

  @Override
  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

  public void addUnhandledSubfields(String code) {
    if (unhandledSubfields == null)
      unhandledSubfields = new ArrayList<>();
    unhandledSubfields.add(code);
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
