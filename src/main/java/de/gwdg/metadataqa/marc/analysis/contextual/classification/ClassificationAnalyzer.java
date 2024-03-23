package de.gwdg.metadataqa.marc.analysis.contextual.classification;

import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.contextual.ContextualAnalyzer;
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

public abstract class ClassificationAnalyzer extends ContextualAnalyzer<ClassificationStatistics> {
  private static final Logger logger = Logger.getLogger(
    ClassificationAnalyzer.class.getCanonicalName()
  );
  protected static final ClassificationSchemes classificationSchemes = ClassificationSchemes.getInstance();
  protected ClassificationParameters parameters = null;

  protected ClassificationAnalyzer(BibliographicRecord bibliographicRecord, ClassificationStatistics statistics) {
    super(bibliographicRecord, statistics);
  }

  protected ClassificationAnalyzer(BibliographicRecord bibliographicRecord, ClassificationStatistics statistics, ClassificationParameters parameters) {
    this(bibliographicRecord, statistics);
    this.parameters = parameters;
  }

  public abstract int process();

  protected void increaseCounters(int total) {
    // Increase the counter that this record has at least one classification
    count((total > 0), statistics.getHasClassifications());

    // Increase the counter for the number of classifications in the record
    count(total, statistics.getSchemaHistogram());

    // Add this record as an example for the obtained number of classifications
    statistics.getFrequencyExamples().computeIfAbsent(total, s -> bibliographicRecord.getId(true));

    if (parameters == null || !parameters.doCollectCollocations()) {
      return;
    }
    logger.info("Collecting collocations");
    List<String> collocation = getCollocationInRecord();
    if (!collocation.isEmpty()) {
      count(collocation, statistics.getCollocationHistogram());
    }
  }

  public List<Schema> getSchemasInRecord() {
    return schemasInRecord;
  }

  /**
   * Get the list of classification schemas in the current record, by their abbreviations, sorted and unique.
   */
  public List<String> getCollocationInRecord() {
    return schemasInRecord
      .stream()
      .map(Schema::getAbbreviation)
      .sorted()
      .distinct()
      .collect(Collectors.toList());
  }
}
