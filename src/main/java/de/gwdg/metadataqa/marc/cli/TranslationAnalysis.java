package de.gwdg.metadataqa.marc.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gwdg.metadataqa.api.configuration.SchemaConfiguration;
import de.gwdg.metadataqa.api.interfaces.MetricResult;
import de.gwdg.metadataqa.api.model.XmlFieldInstance;
import de.gwdg.metadataqa.api.rule.RuleCatalog;
import de.gwdg.metadataqa.api.rule.RuleCheckerOutput;
import de.gwdg.metadataqa.api.rule.RuleCheckingOutputStatus;
import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.RuleCatalogUtils;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.TranslationParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.BibSelector;
import de.gwdg.metadataqa.marc.cli.utils.BibSelectorFactory;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.cli.utils.ShaclUtils;
import de.gwdg.metadataqa.marc.cli.utils.TranslationModel;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordFilter;
import de.gwdg.metadataqa.marc.cli.utils.placename.PlaceNameNormaliser;
import de.gwdg.metadataqa.marc.cli.utils.translation.ContributorNormaliser;
import de.gwdg.metadataqa.marc.cli.utils.translation.PublicationYearNormaliser;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TranslationAnalysis extends QACli<TranslationParameters>
                                 implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(TranslationAnalysis.class.getCanonicalName());

  private boolean readyToProcess;
  private File outputFile;
  private RuleCatalog ruleCatalog;
  private SchemaConfiguration schema;
  private final RecordFilter recordFilter;
  private Map<String, Long> failed;
  private Map<String, String> rulePathMap;
  private Map<String, File> debugFiles;
  private PlaceNameNormaliser placeNameNormaliser;
  private ObjectMapper mapper;
  private File exportFile;
  private PublicationYearNormaliser publicationYearNormaliser;
  private ContributorNormaliser contributorNormaliser;

  public TranslationAnalysis(String[] args) throws ParseException {
    parameters = new TranslationParameters(args);
    recordFilter = parameters.getRecordFilter();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new TranslationAnalysis(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      System.exit(1);
    }
    if (processor.getParameters().getArgs().length < 1) {
      System.err.println("Please provide a MARC file name!");
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }
    if (processor.getParameters().doHelp()) {
      processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }
    RecordIterator iterator = new RecordIterator(processor);
    iterator.setProcessWithErrors(processor.getParameters().getProcessRecordsWithoutId());
    iterator.start();
  }

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    outputFile = new File(parameters.getOutputDir(), parameters.getShaclOutputFile());

    schema = ShaclUtils.setupSchema(parameters);
    ruleCatalog = ShaclUtils.setupRuleCatalog(schema, parameters);
    rulePathMap = ShaclUtils.createRulePathMap(schema);
    failed = new HashMap<>();
    if (parameters.getDebugFailedRules() != null && !parameters.getDebugFailedRules().isEmpty()) {
      debugFiles = new HashMap<>();
      for (String ruleId : parameters.getDebugFailedRules()) {
        debugFiles.put(ruleId, new File(parameters.getOutputDir(), String.format("translations-deubg-%s.txt", ruleId)));
      }
    }

    if (outputFile.exists()) {
      try {
        Files.delete(outputFile.toPath());
      } catch (IOException e) {
        logger.log(Level.SEVERE, "The output file ({}) has not been deleted", outputFile.getAbsolutePath());
      }
    }
    List<String> header = ruleCatalog.getHeader();
    header.add(0, "id");
    header.addAll(TranslationModel.header());
    printToFile(outputFile, CsvUtils.createCsv(header));

    if (parameters.getTranslationPlaceNameDictionaryDir() != null)
      placeNameNormaliser = new PlaceNameNormaliser(parameters.getTranslationPlaceNameDictionaryDir(), parameters.getOutputDir());

    if (parameters.getTranslationExport() != null) {
      exportFile = new File(parameters.getOutputDir(), parameters.getTranslationExport());
      if (exportFile.exists())
        exportFile.delete();
      mapper = new ObjectMapper();
    }

    publicationYearNormaliser = new PublicationYearNormaliser(parameters.getOutputDir());
    contributorNormaliser = new ContributorNormaliser(
      parameters.getOutputDir(),
      schema.asSchema().getPathByLabel("245$c").getRules().get(0).getMqafPattern().getCompiledPattern()
    );
  }

  @Override
  public void fileOpened(Path path) {
    logger.log(Level.INFO, "file opened: {0}", new Object[]{path});
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord,
                            int recordNumber,
                            List<ValidationError> errors) throws IOException {
    processRecord(bibliographicRecord, recordNumber);
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord,
                            int recordNumber)
      throws IOException {
    if (!recordFilter.isAllowable(bibliographicRecord)) {
      // logger.info("ignoring " + bibliographicRecord.getId());
      return;
    }

    BibSelector selector = BibSelectorFactory.create(schema.getFormat(), bibliographicRecord);

    if (selector != null) {
      List<MetricResult> results = ruleCatalog.measure(selector);
      Map<String, RuleCheckerOutput> resultMap = (Map<String, RuleCheckerOutput>) results.get(0).getResultMap();
      TranslationModel model = new TranslationModel(resultMap, selector, placeNameNormaliser, publicationYearNormaliser, contributorNormaliser, parameters.getMarcVersion());

      List<String> debugIds = parameters.getDebugFailedRules();
      if (debugIds != null && !debugIds.isEmpty()) {
        for (String debugId : debugIds) {
          if (resultMap.get(debugId).getStatus().equals(RuleCheckingOutputStatus.FAILED)) {
            failed.computeIfAbsent(debugId, k -> 0L);
            failed.put(debugId, failed.get(debugId) + 1);
            List<XmlFieldInstance> values = selector.get(rulePathMap.get(debugId));
            printToFile(debugFiles.get(debugId), values.get(0).getValue() + "\n");
          }
        }
      }

      List<Object> values = RuleCatalogUtils.extract(ruleCatalog, results);
      values.add(0, bibliographicRecord.getId(true));
      values.addAll(model.values());

      printToFile(outputFile, CsvUtils.createCsvFromObjects(values));

      if (model.isTranslation()) {
        Map<String, Object> extracted = model.extract();
        extracted.put("id", bibliographicRecord.getId());
        if (exportFile != null && mapper != null)
          printToFile(exportFile, mapper.writeValueAsString(extracted) + "\n");
      }
    }
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    String report = failed.entrySet().stream()
        .map(e ->
          String.format(
            "%s: %d failures (%.2f%% of records)",
            e.getKey(), e.getValue(), e.getValue() * 100.0 / numberOfprocessedRecords
          ))
          .collect(Collectors.joining(", "));
    if (!report.isEmpty())
      logger.log(Level.WARNING, "failed rules: {0}", report);
    copyFileToOutputDir(parameters.getShaclConfigurationFile());
    saveParameters("translation-analysis.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));

    placeNameNormaliser.reportUnresolvedPlaceNames();
    publicationYearNormaliser.reportUnresolvedYears();
    publicationYearNormaliser.reportPatterns();
    contributorNormaliser.reportUnresolvedContributors();
  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
