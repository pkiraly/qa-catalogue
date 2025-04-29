package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.CsvUtils;
import de.gwdg.metadataqa.marc.Utils;
import de.gwdg.metadataqa.marc.analysis.GroupSelector;
import de.gwdg.metadataqa.marc.analysis.completeness.CompletenessDAO;
import de.gwdg.metadataqa.marc.analysis.completeness.RecordCompleteness;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.plugin.CompletenessFactory;
import de.gwdg.metadataqa.marc.cli.plugin.CompletenessPlugin;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordFilter;
import de.gwdg.metadataqa.marc.cli.utils.ignorablerecords.RecordIgnorator;
import de.gwdg.metadataqa.marc.dao.record.BibliographicRecord;
import de.gwdg.metadataqa.marc.definition.tags.TagCategory;
import de.gwdg.metadataqa.marc.model.validation.ValidationError;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.utils.BasicStatistics;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Completeness extends QACli<CompletenessParameters> implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Completeness.class.getCanonicalName());
  private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");
  public static final String ALL_TYPE = "all";
  private CompletenessDAO completenessDAO = new CompletenessDAO();

  private boolean readyToProcess;
  private CompletenessPlugin plugin;
  private RecordFilter recordFilter;
  private RecordIgnorator recordIgnorator;

  public Completeness(String[] args) throws ParseException {
    parameters = new CompletenessParameters(args);
    plugin = CompletenessFactory.create(parameters);
    recordFilter = parameters.getRecordFilter();
    recordIgnorator = parameters.getRecordIgnorator();
    readyToProcess = true;
    initializeGroups(parameters.getGroupBy(), parameters.isPica());
    if (doGroups()) {
      initializeMeta(parameters);
      if (doSaveGroupIds) {
        idCollectorFile = prepareReportFile(parameters.getOutputDir(), "id-groupid.csv");
        printToFile(idCollectorFile, CsvUtils.createCsv("id", "groupId"));
      }
    }
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new Completeness(args);
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
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord marcRecord, int recordNumber, List<ValidationError> errors) throws IOException {
    processRecord(marcRecord, recordNumber);
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber) throws IOException {
    if (!recordFilter.isAllowable(bibliographicRecord))
      return;

    if (recordIgnorator.isIgnorable(bibliographicRecord))
      return;

    RecordCompleteness recordCompleteness = new RecordCompleteness(bibliographicRecord, parameters, completenessDAO, plugin, groupBy);
    recordCompleteness.process();

    if (doSaveGroupIds)
      saveGroupIds(bibliographicRecord.getId(true), recordCompleteness.getGroupIds());
    if (doGroups())
      for (String id : recordCompleteness.getGroupIds())
        Utils.count(id, completenessDAO.getGroupCounter());

    for (String path : recordCompleteness.getRecordFrequency().keySet()) {
      if (groupBy != null) {
        for (String groupId : recordCompleteness.getGroupIds()) {
          completenessDAO.getGroupedElementFrequency().computeIfAbsent(groupId, s -> new HashMap<>());
          completenessDAO.getGroupedElementFrequency().get(groupId).computeIfAbsent(recordCompleteness.getDocumentType(), s -> new HashMap<>());
          completenessDAO.getGroupedElementFrequency().get(groupId).computeIfAbsent(ALL_TYPE, s -> new HashMap<>());
          Utils.count(path, completenessDAO.getGroupedElementFrequency().get(groupId).get(recordCompleteness.getDocumentType()));
          Utils.count(path, completenessDAO.getGroupedElementFrequency().get(groupId).get(ALL_TYPE));

          completenessDAO.getGroupedFieldHistogram().computeIfAbsent(groupId, s -> new HashMap<>());
          completenessDAO.getGroupedFieldHistogram().get(groupId).computeIfAbsent(path, s -> new HashMap<>());
          Utils.count(recordCompleteness.getRecordFrequency().get(path), completenessDAO.getGroupedFieldHistogram().get(groupId).get(path));
        }
      } else {
        Utils.count(path, completenessDAO.getElementFrequency().get(recordCompleteness.getDocumentType()));
        Utils.count(path, completenessDAO.getElementFrequency().get(ALL_TYPE));
        completenessDAO.getFieldHistogram().computeIfAbsent(path, s -> new HashMap<>());
        Utils.count(recordCompleteness.getRecordFrequency().get(path), completenessDAO.getFieldHistogram().get(path));
      }
    }

    for (String key : recordCompleteness.getRecordPackageCounter().keySet()) {
      if (groupBy != null) {
        for (String groupId : recordCompleteness.getGroupIds()) {
          completenessDAO.getGroupedPackageCounter().computeIfAbsent(groupId, s -> new HashMap<>());
          completenessDAO.getGroupedPackageCounter().get(groupId).computeIfAbsent(recordCompleteness.getDocumentType(), s -> new HashMap<>());
          completenessDAO.getGroupedPackageCounter().get(groupId).computeIfAbsent(ALL_TYPE, s -> new HashMap<>());
          Utils.count(key, completenessDAO.getGroupedPackageCounter().get(groupId).get(recordCompleteness.getDocumentType()));
          Utils.count(key, completenessDAO.getGroupedPackageCounter().get(groupId).get(ALL_TYPE));
        }
      } else {
        completenessDAO.getPackageCounter().computeIfAbsent(recordCompleteness.getDocumentType(), s -> new HashMap<>());
        completenessDAO.getPackageCounter().computeIfAbsent(ALL_TYPE, s -> new HashMap<>());
        Utils.count(key, completenessDAO.getPackageCounter().get(recordCompleteness.getDocumentType()));
        Utils.count(key, completenessDAO.getPackageCounter().get(ALL_TYPE));
      }
    }
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    completenessDAO.initialize();
  }

  @Override
  public void fileOpened(Path file) {
    // do nothing
  }

  @Override
  public void fileProcessed() {
    // do nothing
  }

  @Override
  public void afterIteration(int numberOfprocessedRecords, long duration) {
    String fileExtension = ".csv";
    final char separator = getSeparator(parameters.getFormat());
    if (parameters.getFormat().equals(ValidationErrorFormat.TAB_SEPARATED)) {
      fileExtension = ".tsv";
    }

    saveLibraries003(fileExtension, separator);
    saveLibraries(fileExtension, separator);
    if (groupBy != null) {
      saveGroups(fileExtension, separator);
      saveGroupedPackages(fileExtension, separator);
      saveGroupedMarcElements(fileExtension, separator);
    } else {
      savePackages(fileExtension, separator);
      saveMarcElements(fileExtension, separator);
    }
    saveParameters("completeness.params.json", parameters, Map.of("numberOfprocessedRecords", numberOfprocessedRecords, "duration", duration));
  }

  private void saveLibraries003(String fileExtension, char separator) {
    logger.info("Saving libraries003...");
    var path = Paths.get(parameters.getOutputDir(), "libraries003" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(CsvUtils.createCsv("library", "count"));
      completenessDAO.getLibrary003Counter().forEach((key, value) -> {
        try {
          writer.write(CsvUtils.createCsv(key, value));
        } catch (IOException e) {
          logger.log(Level.SEVERE, "saveLibraries003", e);
        }
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveLibraries003", e);
    }
  }

  private void saveMarcElements(String fileExtension, char separator) {
    Path path = Paths.get(parameters.getOutputDir(), "marc-elements" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(CsvUtils.createCsv(
        "groupId", "documenttype", "path", "sortkey", "packageid", "package", "tag", "subfield",
        "number-of-record", "number-of-instances",
        "min", "max", "mean", "stddev", "histogram"
      ));
      completenessDAO.getElementCardinality().forEach((documentType, cardinalities) ->
        cardinalities.forEach((marcPath, cardinality) -> {
          try {
            writer.write(formatCardinality(marcPath, cardinality, documentType, null));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "saveMarcElements", e);
          }
        })
      );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveMarcElements", e);
    }
  }

  private void saveGroupedMarcElements(String fileExtension, char separator) {
    logger.info("saving grouped MARC elements...");
    Path path = Paths.get(parameters.getOutputDir(), "completeness-grouped-marc-elements" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(CsvUtils.createCsv(
        "groupId", "documenttype", "path", "sortkey", "packageid", "package", "tag", "subfield",
        "number-of-record", "number-of-instances",
        "min", "max", "mean", "stddev", "histogram"
      ));
      completenessDAO.getGroupedElementCardinality().forEach((groupId, documentTypes) ->
        documentTypes.forEach((documentType, cardinalities) ->
          cardinalities.forEach((marcPath, cardinality) -> {
            try {
              writer.write(formatCardinality(marcPath, cardinality, documentType, groupId));
            } catch (IOException e) {
              logger.log(Level.SEVERE, "saveMarcElements", e);
            }
          })
        )
      );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveMarcElements", e);
    }
  }

  private void savePackages(String fileExtension, char separator) {
    logger.info("saving packages...");
    var path = Paths.get(parameters.getOutputDir(), "packages" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(CsvUtils.createCsv("documenttype", "packageid", "name", "label", "iscoretag", "count"));
      completenessDAO.getPackageCounter().forEach((documentType, packages) ->
        packages.forEach((packageName, count) -> {
          try {
            TagCategory tagCategory = TagCategory.getPackage(packageName);
            String range = packageName;
            String label = "";
            int id = 100;
            boolean isPartOfMarcScore = false;
            if (tagCategory != null) {
              id = tagCategory.getId();
              range = tagCategory.getRange();
              label = tagCategory.getLabel();
              isPartOfMarcScore = tagCategory.isPartOfMarcCore();
            } else {
              logger.severe(packageName + " has not been found in TagCategory");
            }
            writer.write(CsvUtils.createCsv(documentType, id, range, label, isPartOfMarcScore, count));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "savePackages", e);
          }
        })
      );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "savePackages", e);
    }
  }

  private void saveGroupedPackages(String fileExtension, char separator) {
    logger.info("saving grouped packages...");
    var path = Paths.get(parameters.getOutputDir(), "completeness-grouped-packages" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(CsvUtils.createCsv("group", "documenttype", "packageid", "name", "label", "iscoretag", "count"));
      completenessDAO.getGroupedPackageCounter()
        .entrySet()
        .stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(groupData -> {
          String groupId = groupData.getKey();
          Map<String, Map<String, Integer>> documentTypes = groupData.getValue();
          documentTypes
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(doctypeData -> {
              String documentType = doctypeData.getKey();
              Map<String, Integer> packages = doctypeData.getValue();
              packages
                .forEach((packageName, count) -> {
                try {
                  TagCategory tagCategory = TagCategory.getPackage(packageName);
                  String range = packageName;
                  String label = "";
                  int id = 100;
                  boolean isPartOfMarcScore = false;
                  if (tagCategory != null) {
                    id = tagCategory.getId();
                    range = tagCategory.getRange();
                    label = tagCategory.getLabel();
                    isPartOfMarcScore = tagCategory.isPartOfMarcCore();
                  } else {
                    logger.severe(packageName + " has not been found in TagCategory");
                  }
                  writer.write(CsvUtils.createCsv(groupId, documentType, id, range, label, isPartOfMarcScore, count));
                } catch (IOException e) {
                  logger.log(Level.SEVERE, "savePackages", e);
                }
              });
            });
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "savePackages", e);
    }
  }

  private void saveLibraries(String fileExtension, char separator) {
    logger.info("Saving libraries...");
    var path = Paths.get(parameters.getOutputDir(), "libraries" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(CsvUtils.createCsv("library", "count"));
      completenessDAO.getLibraryCounter().forEach((key, value) -> {
        try {
          writer.write(CsvUtils.createCsv(key, value));
        } catch (IOException e) {
          logger.log(Level.SEVERE, "saveLibraries", e);
        }
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveLibraries", e);
    }
  }

  private void saveGroups(String fileExtension, char separator) {
    logger.info("Saving groups...");
    GroupSelector groupSelector = new GroupSelector(parameters.getGroupListFile());
    var path = Paths.get(parameters.getOutputDir(), "completeness-groups" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(CsvUtils.createCsv("id", "group", "count"));
      completenessDAO.getGroupCounter()
        .entrySet()
        .stream()
        .sorted((a,b) -> a.getKey().compareTo(b.getKey()))
        .forEach(item -> {
          try {
            writer.write(CsvUtils.createCsv(item.getKey(), groupSelector.getOrgName(item.getKey()), item.getValue()));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "saveLibraries", e);
          }
        });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveLibraries", e);
    }
  }

  private String formatCardinality(String marcPath,
                                   int cardinality,
                                   String documentType,
                                   String groupId) {
    if (marcPath.equals("")) {
      logger.severe("Empty key from " + marcPath);
    }

    String marcPathLabel = marcPath.replace("!ind", "ind").replaceAll("\\|(\\d)$", "$1");
    String sortkey = marcPath.replaceAll("^leader", "000");
    int packageId = TagCategory.OTHER.getId();
    String packageLabel = TagCategory.OTHER.getLabel();
    String tagLabel = "";
    String subfieldLabel = "";
    TagHierarchy tagHierarchy = plugin.getTagHierarchy(marcPathLabel);
    if (tagHierarchy != null) {
      packageId = tagHierarchy.getPackageId();
      packageLabel = tagHierarchy.getPackageLabel();
      tagLabel = tagHierarchy.getTagLabel();
      subfieldLabel = tagHierarchy.getSubfieldLabel();
    } else {
      logger.severe(() -> "Key can not be found in the TagHierarchy: " + marcPathLabel);
    }

    // Integer cardinality = entry.getValue();
    Integer frequency = (groupId != null)
      ? completenessDAO.getGroupedElementFrequency().get(groupId).get(documentType).get(marcPath)
      : completenessDAO.getElementFrequency().get(documentType).get(marcPath);

    Map<Integer, Integer> histogram = null;
    if (groupId != null) {
      histogram = completenessDAO.getGroupedFieldHistogram().get(groupId).get(marcPath);
      if (!completenessDAO.getGroupedFieldHistogram().get(groupId).containsKey(marcPath)) {
        logger.log(Level.WARNING,"Field {0} is not registered in histogram", marcPath);
      }
    } else {
      histogram = completenessDAO.getFieldHistogram().get(marcPath);
      if (!completenessDAO.getFieldHistogram().containsKey(marcPath)) {
        logger.log(Level.WARNING,"Field {0} is not registered in histogram", marcPath);
      }
    }
    BasicStatistics statistics = new BasicStatistics(histogram);

    List<Object> values = Arrays.asList(
      (groupId != null ? groupId : 0),
      documentType, marcPathLabel, sortkey, packageId, packageLabel, tagLabel, subfieldLabel,
      frequency,   // = number-of-record
      cardinality, // = number-of-instances
      statistics.getMin(), statistics.getMax(),
      statistics.getMean(), statistics.getStdDev(),
      statistics.formatHistogram()
    );

    return CsvUtils.createCsvFromObjects(values);
  }

  private char getSeparator(ValidationErrorFormat format) {
    if (format.equals(ValidationErrorFormat.TAB_SEPARATED)) {
      return '\t';
    } else {
      return ',';
    }
  }

  @Override
  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format("java -cp qa-catalogue.jar %s [options] [file]", this.getClass().getCanonicalName());
    formatter.printHelp(message, options);
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
