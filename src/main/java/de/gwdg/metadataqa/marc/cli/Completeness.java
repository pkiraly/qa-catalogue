package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.EncodedValue;
import de.gwdg.metadataqa.marc.analysis.completeness.CompletenessDAO;
import de.gwdg.metadataqa.marc.analysis.completeness.RecordCompleteness;
import de.gwdg.metadataqa.marc.definition.general.codelist.OrganizationCodes;
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
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.utils.BasicStatistics;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static de.gwdg.metadataqa.marc.Utils.quote;

public class Completeness extends QACli implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Completeness.class.getCanonicalName());
  private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");

  private CompletenessParameters parameters;

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
    initializeGroups(parameters.getGroupBy(), parameters.isPica());
    readyToProcess = true;
  }

  public static void main(String[] args) {
    BibliographicInputProcessor processor = null;
    try {
      processor = new Completeness(args);
    } catch (ParseException e) {
      System.err.println("ERROR. " + e.getLocalizedMessage());
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

  @Override
  public CommonParameters getParameters() {
    return parameters;
  }

  @Override
  public void processRecord(Record marc4jRecord, int recordNumber) throws IOException {
    // do nothing
  }

  @Override
  public void processRecord(BibliographicRecord bibliographicRecord, int recordNumber) throws IOException {
    if (!recordFilter.isAllowable(bibliographicRecord))
      return;

    if (recordIgnorator.isIgnorable(bibliographicRecord))
      return;

    RecordCompleteness recordCompleteness = new RecordCompleteness(bibliographicRecord, parameters, completenessDAO, plugin, groupBy);
    recordCompleteness.process();

    if (groupBy != null)
      for (String id : recordCompleteness.getGroupIds())
        count(id, completenessDAO.getGroupCounter());

    for (String key : recordCompleteness.getRecordFrequency().keySet()) {
      if (groupBy != null) {
        for (String groupId : recordCompleteness.getGroupIds()) {
          completenessDAO.getGrouppedElementFrequency().computeIfAbsent(groupId, s -> new TreeMap<>());
          completenessDAO.getGrouppedElementFrequency().get(groupId).computeIfAbsent(recordCompleteness.getDocumentType(), s -> new TreeMap<>());
          completenessDAO.getGrouppedElementFrequency().get(groupId).computeIfAbsent("all", s -> new TreeMap<>());
          count(key, completenessDAO.getGrouppedElementFrequency().get(groupId).get(recordCompleteness.getDocumentType()));
          count(key, completenessDAO.getGrouppedElementFrequency().get(groupId).get("all"));

          completenessDAO.getGrouppedFieldHistogram().computeIfAbsent(groupId, s -> new TreeMap<>());
          completenessDAO.getGrouppedFieldHistogram().get(groupId).computeIfAbsent(key, s -> new TreeMap<>());
          count(recordCompleteness.getRecordFrequency().get(key), completenessDAO.getGrouppedFieldHistogram().get(groupId).get(key));
        }
      } else {
        count(key, completenessDAO.getElementFrequency().get(recordCompleteness.getDocumentType()));
        count(key, completenessDAO.getElementFrequency().get("all"));
        completenessDAO.getFieldHistogram().computeIfAbsent(key, s -> new TreeMap<>());
        count(recordCompleteness.getRecordFrequency().get(key), completenessDAO.getFieldHistogram().get(key));
      }
    }

    for (String key : recordCompleteness.getRecordPackageCounter().keySet()) {
      if (groupBy != null) {
        for (String groupId : recordCompleteness.getGroupIds()) {
          completenessDAO.getGrouppedPackageCounter().computeIfAbsent(groupId, s -> new TreeMap<>());
          completenessDAO.getGrouppedPackageCounter().get(groupId).computeIfAbsent(recordCompleteness.getDocumentType(), s -> new TreeMap<>());
          completenessDAO.getGrouppedPackageCounter().get(groupId).computeIfAbsent("all", s -> new TreeMap<>());
          count(key, completenessDAO.getGrouppedPackageCounter().get(groupId).get(recordCompleteness.getDocumentType()));
          count(key, completenessDAO.getGrouppedPackageCounter().get(groupId).get("all"));
        }
      } else {
        completenessDAO.getPackageCounter().computeIfAbsent(recordCompleteness.getDocumentType(), s -> new TreeMap<>());
        count(key, completenessDAO.getPackageCounter().get(recordCompleteness.getDocumentType()));
        count(key, completenessDAO.getPackageCounter().get("all"));
      }
    }
  }

  private <T extends Object> void count(T key, Map<T, Integer> counter) {
    counter.computeIfAbsent(key, s -> 0);
    counter.put(key, counter.get(key) + 1);
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    completenessDAO.initialize();
    saveParameters("completeness.params.json", parameters);
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
  public void afterIteration(int numberOfprocessedRecords) {
    String fileExtension = ".csv";
    final char separator = getSeparator(parameters.getFormat());
    if (parameters.getFormat().equals(ValidationErrorFormat.TAB_SEPARATED)) {
      fileExtension = ".tsv";
    }

    saveLibraries003(fileExtension, separator);
    saveLibraries(fileExtension, separator);
    if (groupBy != null) {
      saveGroups(fileExtension, separator);
      saveGrouppedPackages(fileExtension, separator);
      saveGrouppedMarcElements(fileExtension, separator);
    } else {
      savePackages(fileExtension, separator);
      saveMarcElements(fileExtension, separator);
    }
  }

  private void saveLibraries003(String fileExtension, char separator) {
    logger.info("Saving libraries003...");
    var path = Paths.get(parameters.getOutputDir(), "libraries003" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("library" + separator + "count\n");
      completenessDAO.getLibrary003Counter().forEach((key, value) -> {
        try {
          writer.write(String.format("\"%s\"%s%d%n", key, separator, value));
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
      writer.write(createRow(
        "documenttype", "path", "packageid", "package", "tag", "subfield",
        "number-of-record", "number-of-instances",
        "min", "max", "mean", "stddev", "histogram"
      ));
      completenessDAO.getElementCardinality().forEach((documentType, cardinalities) -> {
        cardinalities.forEach((marcPath, cardinality) -> {
          try {
            writer.write(formatCardinality(separator, marcPath, cardinality, documentType, null));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "saveMarcElements", e);
          }
        });
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveMarcElements", e);
    }
  }

  private void saveGrouppedMarcElements(String fileExtension, char separator) {
    logger.info("saving groupped MARC elements...");
    Path path = Paths.get(parameters.getOutputDir(), "completeness-groupped-marc-elements" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow(
        "groupId", "documenttype", "path", "packageid", "package", "tag", "subfield",
        "number-of-record", "number-of-instances",
        "min", "max", "mean", "stddev", "histogram"
      ));
      completenessDAO.getGrouppedElementCardinality().forEach((groupId, documentTypes) -> {
        documentTypes.forEach((documentType, cardinalities) -> {
          cardinalities.forEach((marcPath, cardinality) -> {
            try {
              writer.write(formatCardinality(separator, marcPath, cardinality, documentType, groupId));
            } catch (IOException e) {
              logger.log(Level.SEVERE, "saveMarcElements", e);
            }
          });
        });
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveMarcElements", e);
    }
  }

  private void savePackages(String fileExtension, char separator) {
    logger.info("saving packages...");
    var path = Paths.get(parameters.getOutputDir(), "packages" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow(separator, "documenttype", "packageid", "name", "label", "iscoretag", "count"));
      completenessDAO.getPackageCounter().forEach((documentType, packages) -> {
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
            writer.write(createRow(
              separator, quote(documentType), id, quote(range), quote(label), isPartOfMarcScore, count
            ));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "savePackages", e);
          }
        });
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "savePackages", e);
    }
  }

  private void saveGrouppedPackages(String fileExtension, char separator) {
    logger.info("saving groupped packages...");
    var path = Paths.get(parameters.getOutputDir(), "completeness-groupped-packages" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow(separator, "group", "documenttype", "packageid", "name", "label", "iscoretag", "count"));
      completenessDAO.getGrouppedPackageCounter().forEach((groupId, documentTypes) -> {
        documentTypes.forEach((documentType, packages) -> {
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
              writer.write(createRow(
                separator, quote(groupId), quote(documentType), id, quote(range), quote(label), isPartOfMarcScore, count
              ));
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
      writer.write("library" + separator + "count\n");
      completenessDAO.getLibraryCounter().forEach((key, value) -> {
        try {
          writer.write(String.format("\"%s\"%s%d%n", key, separator, value));
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
    OrganizationCodes org = OrganizationCodes.getInstance();
    var path = Paths.get(parameters.getOutputDir(), "completeness-groups" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("id" + separator + "group" + separator + "count\n");
      completenessDAO.getGroupCounter().forEach((key, value) -> {
        try {
          EncodedValue x = org.getCode("DE-" + key);
          writer.write(String.format("%s%s\"%s\"%s%d%n", key, separator, (x == null ? key : x.getLabel()), separator, value));
        } catch (IOException e) {
          logger.log(Level.SEVERE, "saveLibraries", e);
        }
      });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveLibraries", e);
    }
  }

  private String formatCardinality(char separator,
                                   String marcPath,
                                   int cardinality,
                                   String documentType,
                                   String groupId) {
    if (marcPath.equals("")) {
      logger.severe("Empty key from " + marcPath);
    }

    String marcPathLabel = marcPath.replace("!ind", "ind").replaceAll("\\|(\\d)$", "$1");
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
      logger.severe("Key can not be found in the TagHierarchy: " + marcPathLabel);
    }

    // Integer cardinality = entry.getValue();
    Integer frequency = (groupId != null)
      ? completenessDAO.getGrouppedElementFrequency().get(groupId).get(documentType).get(marcPath)
      : completenessDAO.getElementFrequency().get(documentType).get(marcPath);

    Map<Integer, Integer> histogram = null;
    if (groupId != null) {
      histogram = completenessDAO.getGrouppedFieldHistogram().get(groupId).get(marcPath);
      if (!completenessDAO.getGrouppedFieldHistogram().get(groupId).containsKey(marcPath)) {
        logger.warning(String.format("Field %s is not registered in histogram", marcPath));
      }
    } else {
      histogram = completenessDAO.getFieldHistogram().get(marcPath);
      if (!completenessDAO.getFieldHistogram().containsKey(marcPath)) {
        logger.warning(String.format("Field %s is not registered in histogram", marcPath));
      }
    }
    BasicStatistics statistics = new BasicStatistics(histogram);

    List<Object> values = quote(
      Arrays.asList(
        documentType, marcPathLabel, packageId, packageLabel, tagLabel, subfieldLabel,
        frequency,   // = number-of-record
        cardinality, // = number-of-instances
        statistics.getMin(), statistics.getMax(),
        statistics.getMean(), statistics.getStdDev(),
        statistics.formatHistogram()
      )
    );
    if (groupId != null)
      values.add(0, groupId);

    return StringUtils.join(values, separator) + "\n";
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
    String message = String.format("java -cp metadata-qa-marc.jar %s [options] [file]", this.getClass().getCanonicalName());
    formatter.printHelp(message, options);
  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
