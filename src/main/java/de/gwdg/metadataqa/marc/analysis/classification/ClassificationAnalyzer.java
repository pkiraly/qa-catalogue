package de.gwdg.metadataqa.marc.analysis.classification;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.ClassificationStatistics;
import de.gwdg.metadataqa.marc.cli.parameters.ClassificationParameters;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.general.indexer.subject.ClassificationSchemes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.gwdg.metadataqa.marc.Utils.count;

public abstract class ClassificationAnalyzer {
  private static final Logger logger = Logger.getLogger(
    ClassificationAnalyzer.class.getCanonicalName()
  );
  protected static final ClassificationSchemes classificationSchemes =
    ClassificationSchemes.getInstance();
  protected static final Pattern NUMERIC_REGEX = Pattern.compile("^\\d");
  protected final ClassificationStatistics statistics;
  protected ClassificationParameters parameters = null;
  protected BibliographicRecord marcRecord;
  protected List<Schema> schemasInRecord;

  protected ClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics) {
    this.marcRecord = marcRecord;
    this.statistics = statistics;
  }

  protected ClassificationAnalyzer(BibliographicRecord marcRecord, ClassificationStatistics statistics, ClassificationParameters parameters) {
    this(marcRecord, statistics);
    this.parameters = parameters;
  }

  public abstract int process();

  protected void updateSchemaSubfieldStatistics(DataField field, Schema currentSchema) {
    if (currentSchema == null) {
      return;
    }
    List<String> subfields = orderSubfields(field.getSubfields());

    statistics.getSubfields().computeIfAbsent(currentSchema, s -> new HashMap<>());
    Map<List<String>, Integer> subfieldsStatistics = statistics.getSubfields().get(currentSchema);
    if (!subfieldsStatistics.containsKey(subfields)) {
      subfieldsStatistics.put(subfields, 1);
    } else {
      subfieldsStatistics.put(subfields, subfieldsStatistics.get(subfields) + 1);
    }
  }

  /**
   * Order the subfields in the field in a way that the alphabetic subfields come first and the numeric subfields come after.
   * Also label repeated subfields with a '+' at the end.
   * @param originalSubfields The original list of subfields in the field
   * @return The ordered list of subfields
   */
  private List<String> orderSubfields(List<MarcSubfield> originalSubfields) {
    List<String> subfieldCodes = new ArrayList<>();
    Set<String> multiFields = new HashSet<>();

    // For each subfield in the original list, add the code to the subfieldCodes list
    // If the code isn't in the list, add it. If it is, add it to the multiFields list
    for (MarcSubfield subfield : originalSubfields) {
      String code = subfield.getCode();
      if (!subfieldCodes.contains(code)) {
        subfieldCodes.add(code);
      }
      else {
        multiFields.add(code);
      }
    }

    // If there are any multiFields, remove them from the subfieldCodes list and add them back with a '+' at the end
    for (String code : multiFields) {
      subfieldCodes.remove(code);
      subfieldCodes.add(code + "+");
    }

    List<String> alphabetic = new ArrayList<>();
    List<String> numeric = new ArrayList<>();

    // For each subfield in the subfieldCodes list, add it to the alphabetic list if it's not numeric,
    // and to the numeric list if it is
    for (String subfield : subfieldCodes) {
      if (NUMERIC_REGEX.matcher(subfield).matches()) {
        numeric.add(subfield);
      } else {
        alphabetic.add(subfield);
      }
    }

    // Sort the final list so that the alphabetic subfields come first and the numeric subfields come after
    Collections.sort(alphabetic);
    Collections.sort(numeric);
    subfieldCodes = alphabetic;
    subfieldCodes.addAll(numeric);

    return subfieldCodes;
  }

  protected void increaseCounters(int total) {
    count((total > 0), statistics.getHasClassifications());
    count(total, statistics.getSchemaHistogram());
    statistics.getFrequencyExamples().computeIfAbsent(total, s -> marcRecord.getId(true));

    if (parameters != null && parameters.doCollectCollocations()) {
      List<String> collocation = getCollocationInRecord();
      if (!collocation.isEmpty())
        count(collocation, statistics.getCollocationHistogram());
    }
  }

  protected void registerSchemas(List<Schema> schemas) {
    addSchemasToStatistics(statistics.getInstances(), schemas);

    List<Schema> uniqSchemas = deduplicateSchema(schemas);
    addSchemasToStatistics(statistics.getRecords(), uniqSchemas);
    schemasInRecord.addAll(uniqSchemas);
  }

  private void addSchemasToStatistics(Map<Schema, Integer> fieldStatistics,
                                      List<Schema> schemes) {
    if (schemes.isEmpty()) {
      return;
    }
    for (Schema scheme : schemes) {
      Utils.count(scheme, fieldStatistics);
    }
  }

  protected List<Schema> deduplicateSchema(List<Schema> schemas) {
    return new ArrayList<>(new HashSet<>(schemas));
  }

  public List<Schema> getSchemasInRecord() {
    return schemasInRecord;
  }

  public List<String> getCollocationInRecord() {
    return schemasInRecord
      .stream()
      .map(Schema::getAbbreviation)
      .sorted()
      .distinct()
      .collect(Collectors.toList());
  }
}
