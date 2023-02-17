package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.analysis.validator.Validator;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorConfiguration;
import de.gwdg.metadataqa.marc.analysis.validator.ValidatorDAO;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.cli.parameters.ValidatorParameters;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorCategory;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormatter;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorType;
import de.gwdg.metadataqa.marc.utils.pica.path.PicaPath;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.gwdg.metadataqa.marc.Utils.*;
import static de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat.TAB_SEPARATED;

/**
 * usage:
 * java -cp target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar de.gwdg.metadataqa.marc.cli.Validator [MARC21 file]
 *
 * @author Péter Király <peter.kiraly at gwdg.de>
 */
public class ValidatorCli extends QACli implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(ValidatorCli.class.getCanonicalName());
  private Options options;

  private final ValidatorParameters parameters;
  private final Map<Integer, Integer> hashedIndex = new HashMap<>();
  private final Map<String, Set<String>> isbnCollector = new TreeMap<>();
  private final Map<String, Set<String>> issnCollector = new TreeMap<>();
  private File detailsFile = null;
  private File summaryFile = null;
  private File collectorFile = null;
  private boolean doPrintInProcessRecord = true;
  private boolean readyToProcess;
  private int counter;
  private int numberOfprocessedRecords;
  private char separator;
  private boolean hasSeparator = false;
  private int vErrorId = 1;
  private List<ValidationError> allValidationErrors;
  private ValidatorConfiguration validatorConfiguration;
  private ValidatorDAO validatorDAO = new ValidatorDAO();

  public ValidatorCli(String[] args) throws ParseException {
    this(new ValidatorParameters(args));
  }

  public ValidatorCli(ValidatorParameters parameters) throws ParseException {
    this.parameters = parameters;
    options = parameters.getOptions();
    readyToProcess = true;
    counter = 0;
    validatorConfiguration = new ValidatorConfiguration()
      .withMarcVersion(parameters.getMarcVersion())
      .withDoSummary(parameters.doSummary())
      .withIgnorableFields(parameters.getIgnorableFields())
      .withIgnorableIssueTypes(parameters.getIgnorableIssueTypes());
    initializeGroups(parameters.getGroupBy(), parameters.isPica());
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new ValidatorCli(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
      // processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
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
    iterator.start();
  }

  public void printHelp(Options opions) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format("java -cp metadata-qa-marc.jar %s [options] [file]",
      this.getClass().getCanonicalName());
    formatter.printHelp(message, options);
  }

  @Override
  public ValidatorParameters getParameters() {
    return parameters;
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    if (!parameters.useStandardOutput()) {
      detailsFile = prepareReportFile(parameters.getOutputDir(), parameters.getDetailsFileName());
      logger.info("details output: " + detailsFile.getPath());
      if (parameters.getSummaryFileName() != null) {
        summaryFile = prepareReportFile(parameters.getOutputDir(), parameters.getSummaryFileName());
        logger.info("summary output: " + summaryFile.getPath());

        collectorFile = prepareReportFile(parameters.getOutputDir(), "issue-collector.csv");
        String header = ValidationErrorFormatter.formatHeaderForCollector(
          parameters.getFormat()
        );
        print(collectorFile, header + "\n");

      } else {
        if (parameters.doSummary())
          summaryFile = detailsFile;
      }
    }
    if (parameters.doDetails()) {
      String header = ValidationErrorFormatter.formatHeaderForDetails(parameters.getFormat());
      print(detailsFile, header + "\n");
    }

    if (parameters.collectAllErrors())
      allValidationErrors = new ArrayList<>();

    saveParameters("validation.params.json", parameters);
  }

  private File prepareReportFile(String outputDir, String fileName) {
    File reportFile = new File(outputDir, fileName);
    if (reportFile.exists())
      if (!reportFile.delete())
        logger.log(Level.SEVERE, "File {} hasn't been deleted", reportFile.getAbsolutePath());
    return reportFile;
  }

  @Override
  public void fileOpened(Path currentFile) {
    // do nothing
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int i) {
    if (bibliographicRecord.getId() == null)
      logger.severe("No record number at " + i);

    if (i % 100000 == 0)
      logger.info("Number of error types so far: " + validatorDAO.getInstanceBasedErrorCounter().size());

    if (parameters.getRecordIgnorator().isIgnorable(bibliographicRecord)) {
      logger.info("skip " + bibliographicRecord.getId() + " (ignorable record)");
      return;
    }

    Set<String> groupIds = new HashSet<>();
    if (groupBy != null) {
      List<String> idLists = parameters.isPica() ? bibliographicRecord.select((PicaPath) groupBy) : null; // TODO: MARC21
      groupIds = extractGroupIds(idLists);
    }

    Validator validator = new Validator(validatorConfiguration);
    boolean isValid = validator.validate(bibliographicRecord);
    if (!isValid && doPrintInProcessRecord) {
      if (parameters.doSummary())
        processSummary(bibliographicRecord, validator, groupIds);

      if (parameters.doDetails())
        processDetails(bibliographicRecord, validator);
    } else {
      if (parameters.doSummary())
        count(0, validatorDAO.getTotalRecordCounter());
    }
    if (parameters.collectAllErrors())
      allValidationErrors.addAll(validator.getValidationErrors());
    counter++;
  }

  private void processDetails(BibliographicRecord marcRecord, Validator validator) {
    List<ValidationError> errors = validator.getValidationErrors();
    if (!errors.isEmpty()) {
      String message = null;
      if (parameters.doSummary()) {
        Map<Integer, Integer> errorIds = new HashMap<>();
        for (ValidationError error : errors) {
          if (error.getId() == null)
            error.setId(hashedIndex.get(error.hashCode()));
          count(error.getId(), errorIds);
        }
        message = ValidationErrorFormatter.formatSimple(
          marcRecord.getId(parameters.getTrimId()), parameters.getFormat(), errorIds
        );
      } else {
        message = ValidationErrorFormatter.format(errors, parameters.getFormat(), parameters.getTrimId());
      }
      if (message != null)
        print(detailsFile, message);
    }
  }

  private void processSummary(BibliographicRecord marcRecord, Validator validator) {
    processSummary(marcRecord, validator, null);
  }

  private void processSummary(BibliographicRecord marcRecord,
                              Validator validator,
                              Set<String> groupIds) {
    List<ValidationError> errors = validator.getValidationErrors();
    List<ValidationError> allButInvalidFieldErrors = new ArrayList<>();
    Set<Integer> uniqueErrors = new HashSet<>();
    Set<ValidationErrorType> uniqueTypes = new HashSet<>();
    Set<ValidationErrorCategory> uniqueCategories = new HashSet<>();
    for (ValidationError error : errors) {
      // set error ID
      if (!validatorDAO.getInstanceBasedErrorCounter().containsKey(error)) {
        error.setId(vErrorId++);
        hashedIndex.put(error.hashCode(), error.getId());
      } else {
        error.setId(hashedIndex.get(error.hashCode()));
      }

      if (!error.getType().equals(ValidationErrorType.FIELD_UNDEFINED)) {
        count(2, validatorDAO.getTotalInstanceCounter());
        allButInvalidFieldErrors.add(error);
      }

      count(error, validatorDAO.getInstanceBasedErrorCounter());
      for (String groupId : groupIds) {
        validatorDAO.getGrouppedInstanceBasedErrorCounter().computeIfAbsent(groupId, s -> new HashMap<>());
        count(error, validatorDAO.getGrouppedInstanceBasedErrorCounter().get(groupId));
      }

      count(error.getType(), validatorDAO.getTypeInstanceCounter());
      count(error.getType().getCategory(), validatorDAO.getCategoryInstanceCounter());
      count(1, validatorDAO.getTotalInstanceCounter());
      updateErrorCollector(marcRecord.getId(true), error.getId());
      uniqueErrors.add(error.getId());
      uniqueTypes.add(error.getType());
      uniqueCategories.add(error.getType().getCategory());
    }

    for (Integer id : uniqueErrors) {
      count(id, validatorDAO.getRecordBasedErrorCounter());
    }
    for (ValidationErrorType id : uniqueTypes) {
      count(id, validatorDAO.getTypeRecordCounter());
    }
    for (ValidationErrorCategory id : uniqueCategories) {
      count(id, validatorDAO.getCategoryRecordCounter());
    }
    count(1, validatorDAO.getTotalRecordCounter());
    if (!allButInvalidFieldErrors.isEmpty())
      count(2, validatorDAO.getTotalRecordCounter());
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {
    logger.info("printCounter");
    this.numberOfprocessedRecords = numberOfprocessedRecords;
    printCounter();

    char separator = getSeparator();
    if (parameters.doSummary()) {
      logger.info("printSummary");
      printSummary(separator);
      logger.info("printCategoryCounts");
      printCategoryCounts();
      logger.info("printTypeCounts");
      printTypeCounts();
      logger.info("printTotalCounts");
      printTotalCounts();
      logger.info("printCollector");
      printCollector();
    }
    logger.info("all printing is DONE");
  }

  private void printCounter() {
    File countFile = prepareReportFile(parameters.getOutputDir(), "count.csv");
    if (parameters.getRecordIgnorator().isEmpty()) {
      printToFile(countFile, "total\n");
      printToFile(countFile, String.valueOf(numberOfprocessedRecords) + "\n");
    } else {
      printToFile(countFile, StringUtils.join(Arrays.asList("total", "processed"), ",") + "\n");
      printToFile(countFile, StringUtils.join(Arrays.asList(numberOfprocessedRecords, counter), ",") + "\n");
    }
  }

  private void printCollector() {
    for (Map.Entry<Integer, Set<String>> entry : validatorDAO.getErrorCollector().entrySet()) {
      printCollectorEntry(entry.getKey(), entry.getValue());
    }
  }

  private void printSummary(char separator) {
    String header = ValidationErrorFormatter.formatHeaderForSummary(parameters.getFormat());
    print(summaryFile, header);
    System.err.println(validatorDAO.getGrouppedInstanceBasedErrorCounter());
    validatorDAO.getInstanceBasedErrorCounter()
      .entrySet()
      .stream()
      .sorted((a,b) -> {
        Integer typeIdA = Integer.valueOf(a.getKey().getType().getId());
        Integer typeIdB = Integer.valueOf(b.getKey().getType().getId());
        int result = typeIdA.compareTo(typeIdB);
        if (result == 0) {
          Integer recordCountA = validatorDAO.getRecordBasedErrorCounter().get(a.getKey().getId());
          Integer recordCountB = validatorDAO.getRecordBasedErrorCounter().get(b.getKey().getId());
          result = recordCountB.compareTo(recordCountA);
        }
        return result;
      }) // sort
      .forEach(
        entry -> {
          ValidationError error = entry.getKey();
          int instanceCount = entry.getValue();
          List<Serializable> cells = new ArrayList<>();
          cells.add(error.getId());
          cells.addAll(Arrays.asList(ValidationErrorFormatter.asArrayWithoutId(error)));
          cells.addAll(Arrays.asList(instanceCount, validatorDAO.getRecordBasedErrorCounter().get(error.getId())));
          // String formattedOutput = ValidationErrorFormatter.formatForSummary(
          //   error, parameters.getFormat()
          // );
          // print(summaryFile, createRow(
          //   separator, error.getId(), formattedOutput, instanceCount, validatorDAO.getRecordBasedErrorCounter().get(error.getId())
          // ));
          // TODO: separator
          print(summaryFile, CsvUtils.createCsv(cells));
        }
      );
  }

  private void printTypeCounts() {
    var path = Paths.get(parameters.getOutputDir(), "issue-by-type.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("id", "categoryId", "category", "type", "instances", "records"));
      validatorDAO.getTypeRecordCounter()
        .entrySet()
        .stream()
        .sorted((a, b) -> ((Integer)a.getKey().getId()).compareTo((Integer) b.getKey().getId()))
        .forEach(entry -> {
          ValidationErrorType type = entry.getKey();
          int records = entry.getValue();
          int instances = validatorDAO.getTypeInstanceCounter().get(entry.getKey());
          try {
            writer.write(createRow(
              type.getId(), type.getCategory().getId(), type.getCategory().getName(), quote(type.getMessage()), instances, records
            ));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "printTypeCounts", e);
          }
        });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printTypeCounts", e);
    }
  }

  private void printTotalCounts() {
    var path = Paths.get(parameters.getOutputDir(), "issue-total.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("type", "instances", "records"));
      validatorDAO.getTotalRecordCounter()
        .entrySet()
        .stream()
        .forEach(entry -> {
          int records = entry.getValue();
          int instances = validatorDAO.getTotalInstanceCounter().getOrDefault(entry.getKey(), 0);
          try {
            writer.write(createRow(entry.getKey(), instances, records));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "printTotalCounts", e);
          }
        });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printTotalCounts", e);
    }
  }

  private void printCategoryCounts() {
    var path = Paths.get(parameters.getOutputDir(), "issue-by-category.csv");
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow("id", "category", "instances", "records"));
      validatorDAO.getCategoryRecordCounter()
        .entrySet()
        .stream()
        .sorted((a, b) -> ((Integer)a.getKey().getId()).compareTo((Integer) b.getKey().getId()))
        .forEach(entry -> {
          ValidationErrorCategory category = entry.getKey();
          int records = entry.getValue();
          int instances = validatorDAO.getCategoryInstanceCounter().getOrDefault(entry.getKey(), -1);
          try {
            writer.write(createRow(category.getId(), category.getName(), instances, records));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "printCategoryCounts", e);
          }
        });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "printCategoryCounts", e);
    }
  }

  private char getSeparator() {
    if (!hasSeparator) {
      separator = parameters.getFormat().equals(TAB_SEPARATED) ? '\t' : ',';
    }
    return separator;
  }

  private void printCollectorEntry(Integer errorId, Set<String> recordIds) {
    print(collectorFile, String.valueOf(errorId) + separator);
    boolean isFirst = true;
    for (String recordId : recordIds) {
      print(collectorFile, (isFirst ? "" : ";") + recordId);
      if (isFirst)
        isFirst = false;
    }
    print(collectorFile, "\n");
  }

  /**
   * Print to the standard output or into file
   * @param file The output file
   * @param content Te content to write
   */
  private void print(File file, String content) {
    if (parameters.useStandardOutput())
      System.out.print(content);
    else {
      printToFile(file, content);
    }
  }

  /**
   * Print to file
   * @param file The output file
   * @param content The content
   */
  private void printToFile(File file, String content) {
    try {
      FileUtils.writeStringToFile(file, content, Charset.defaultCharset(), true);
    } catch (IOException e) {
      if (parameters.doLog())
        logger.log(Level.SEVERE, "printToFile", e);
    }
  }

  private void updateErrorCollector(String recordId, int errorId) {
    if (!validatorDAO.getErrorCollector().containsKey(errorId)) {
      validatorDAO.getErrorCollector().put(errorId, new HashSet<>());
    } else if (parameters.doEmptyLargeCollectors()) {
      if (validatorDAO.getErrorCollector().get(errorId).size() >= 1000) {
        printCollectorEntry(errorId, validatorDAO.getErrorCollector().get(errorId));
        validatorDAO.getErrorCollector().put(errorId, new HashSet<>());
      }
    }
    validatorDAO.getErrorCollector().get(errorId).add(recordId);
  }

  public boolean doPrintInProcessRecord() {
    return doPrintInProcessRecord;
  }

  public void setDoPrintInProcessRecord(boolean doPrintInProcessRecord) {
    this.doPrintInProcessRecord = doPrintInProcessRecord;
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }

  public List<ValidationError> getAllValidationErrors() {
    return allValidationErrors;
  }

  public int getCounter() {
    return counter;
  }

  public int getNumberOfprocessedRecords() {
    return numberOfprocessedRecords;
  }

  public ValidatorConfiguration getValidityConfiguration() {
    return validatorConfiguration;
  }

  private class Counter {
    int id;
    int count;

    public Counter(int count, int id) {
      this.count = count;
      this.id = id;
    }
  }
}