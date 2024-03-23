package de.gwdg.metadataqa.marc.analysis.contextual;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.cli.utils.Schema;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public abstract class ContextualAnalyzer<T extends ContextualStatistics> {
  protected static final Pattern NUMERIC_REGEX = Pattern.compile("^\\d");

  protected List<Schema> schemasInRecord = new ArrayList<>();
  protected BibliographicRecord bibliographicRecord;

  /**
   * Statistics are updated during the analysis of the bibliographic record and are used to generate reports.
   * Usually, statistics are shared among all the analyzers that are part of the same analysis, which means that
   * the same statistics object is passed to all the analyzers.
   */
  protected final T statistics;

  protected ContextualAnalyzer(BibliographicRecord bibliographicRecord, T statistics) {
    this.bibliographicRecord = bibliographicRecord;
    this.statistics = statistics;
  }

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

  /**
   * Given the fieldStatistics map and a list of schemas, increment the count of each schema in the map.
   * @param fieldStatistics The map of schemas and their counts
   * @param schemas The list of schemas to be added to the map
   */
  protected void addSchemasToStatistics(Map<Schema, Integer> fieldStatistics, List<Schema> schemas) {
    if (schemas.isEmpty()) {
      return;
    }

    for (Schema scheme : schemas) {
      Utils.count(scheme, fieldStatistics);
    }
  }

  protected void registerSchemas(List<Schema> schemas) {
    addSchemasToStatistics(statistics.getInstances(), schemas);

    List<Schema> uniqueSchemas = deduplicateSchema(schemas);
    addSchemasToStatistics(statistics.getRecords(), uniqueSchemas);
    schemasInRecord.addAll(uniqueSchemas);
  }

  protected List<Schema> deduplicateSchema(List<Schema> schemas) {
    return new ArrayList<>(new HashSet<>(schemas));
  }

}
