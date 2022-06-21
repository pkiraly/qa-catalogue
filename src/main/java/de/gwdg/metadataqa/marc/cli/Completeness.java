package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.plugin.CompletenessFactory;
import de.gwdg.metadataqa.marc.cli.plugin.CompletenessPlugin;
import de.gwdg.metadataqa.marc.cli.processor.BibliographicInputProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.dao.DataField;
import de.gwdg.metadataqa.marc.dao.MarcControlField;
import de.gwdg.metadataqa.marc.dao.MarcPositionalControlField;
import de.gwdg.metadataqa.marc.dao.MarcRecord;
import de.gwdg.metadataqa.marc.definition.ControlValue;
import de.gwdg.metadataqa.marc.definition.structure.DataFieldDefinition;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static de.gwdg.metadataqa.marc.Utils.quote;

public class Completeness implements BibliographicInputProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Completeness.class.getCanonicalName());
  private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");
  private static final Pattern numericalPattern = Pattern.compile("^(\\d)$");

  private final Options options;
  private CompletenessParameters parameters;
  private Map<String, Integer> library003Counter = new TreeMap<>();
  private Map<String, Integer> libraryCounter = new TreeMap<>();
  private Map<String, Map<String, Integer>> packageCounter = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementCardinality = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementFrequency = new TreeMap<>();
  // private Map<String, String> tagCache = new HashMap<>();
  // private Map<String, Integer> libraryMap = new HashMap<>();
  // private Map<String, Integer> fieldMap = new HashMap<>();
  private Map<String, Map<Integer, Integer>> fieldHistogram = new HashMap<>();
  private boolean readyToProcess;
  private CompletenessPlugin plugin;
  private Map<DataFieldDefinition, String> packageNameCache = new HashMap<>();

  public Completeness(String[] args) throws ParseException {
    parameters = new CompletenessParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
    plugin = CompletenessFactory.create(parameters);
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
  public void processRecord(MarcRecord marcRecord, int recordNumber) throws IOException {
    if (parameters.getRecordIgnorator().isIgnorable(marcRecord))
      return;

    Map<String, Integer> recordFrequency = new TreeMap<>();
    Map<String, Integer> recordPackageCounter = new TreeMap<>();
    // private Map<String, Map<String, Integer>>

    String documentType = plugin.getDocumentType(marcRecord);
    elementCardinality.computeIfAbsent(documentType, s -> new TreeMap<>());
    elementFrequency.computeIfAbsent(documentType, s -> new TreeMap<>());

    if (marcRecord.getControl003() != null)
      count(marcRecord.getControl003().getContent(), library003Counter);

    for (String library : extract(marcRecord, "852", "a")) {
      count(library, libraryCounter);
    }

    if (!parameters.isPica()) {
      processLeader(marcRecord, recordFrequency, recordPackageCounter, documentType);
      processSimpleControlfields(marcRecord, recordFrequency, recordPackageCounter, documentType);
      processPositionalControlFields(marcRecord, recordFrequency, recordPackageCounter, documentType);
    }
    processDataFields(marcRecord, recordFrequency, recordPackageCounter, documentType);

    for (String key : recordFrequency.keySet()) {
      count(key, elementFrequency.get(documentType));
      count(key, elementFrequency.get("all"));

      fieldHistogram.computeIfAbsent(key, s -> new TreeMap<>());
      count(recordFrequency.get(key), fieldHistogram.get(key));
    }

    for (String key : recordPackageCounter.keySet()) {
      packageCounter.computeIfAbsent(documentType, s -> new TreeMap<>());
      count(key, packageCounter.get(documentType));
      count(key, packageCounter.get("all"));
    }
  }

  private void processLeader(MarcRecord marcRecord, Map<String, Integer> recordFrequency, Map<String, Integer> recordPackageCounter, String documentType) {
    if (marcRecord.getLeader() != null) {
      for (ControlValue position : marcRecord.getLeader().getValuesList()) {
        String marcPath = position.getDefinition().getId();
        count(marcPath, elementCardinality.get(documentType));
        count(marcPath, elementCardinality.get("all"));
        count(marcPath, recordFrequency);
        count(TagCategory.tags00x.getPackageName(), recordPackageCounter);
      }
    }
  }

  private void processSimpleControlfields(MarcRecord marcRecord, Map<String, Integer> recordFrequency, Map<String, Integer> recordPackageCounter, String documentType) {
    for (MarcControlField field : marcRecord.getSimpleControlfields()) {
      if (field != null) {
        String marcPath = field.getDefinition().getTag();
        count(marcPath, elementCardinality.get(documentType));
        count(marcPath, elementCardinality.get("all"));
        count(marcPath, recordFrequency);
        count(TagCategory.tags00x.getPackageName(), recordPackageCounter);
      }
    }
  }

  private void processPositionalControlFields(MarcRecord marcRecord, Map<String, Integer> recordFrequency, Map<String, Integer> recordPackageCounter, String documentType) {
    for (MarcPositionalControlField field : marcRecord.getPositionalControlfields()) {
      if (field != null) {
        for (ControlValue position : field.getValuesList()) {
          String marcPath = position.getDefinition().getId();
          count(marcPath, elementCardinality.get(documentType));
          count(marcPath, elementCardinality.get("all"));
          count(marcPath, recordFrequency);
          count(TagCategory.tags00x.getPackageName(), recordPackageCounter);
        }
      }
    }
  }

  private void processDataFields(MarcRecord marcRecord, Map<String, Integer> recordFrequency, Map<String, Integer> recordPackageCounter, String documentType) {
    for (DataField field : marcRecord.getDatafields()) {
      if (parameters.getIgnorableFields().contains(field.getTag()))
        continue;

      count(getPackageName(field), recordPackageCounter);

      List<String> marcPaths = getMarcPaths(field);
      for (String marcPath : marcPaths) {
        count(marcPath, elementCardinality.get(documentType));
        count(marcPath, elementCardinality.get("all"));
        count(marcPath, recordFrequency);
      }
    }
  }

  private List<String> getMarcPaths(DataField field) {
    List<String> marcPaths = new ArrayList<>();

    if (parameters.isMarc21()) {
      if (field.getInd1() != null)
        if (field.getDefinition() != null && field.getDefinition().getInd1().exists() || !field.getInd1().equals(" "))
          marcPaths.add(String.format("%s$!ind1", field.getTag()));

      if (field.getInd2() != null)
        if (field.getDefinition() != null && field.getDefinition().getInd2().exists() || !field.getInd2().equals(" "))
          marcPaths.add(String.format("%s$!ind2", field.getTag()));
    }

    for (MarcSubfield subfield : field.getSubfields())
      if (numericalPattern.matcher(subfield.getCode()).matches())
        marcPaths.add(String.format("%s$|%s", field.getTag(), subfield.getCode()));
      else
        marcPaths.add(String.format("%s$%s", field.getTag(), subfield.getCode()));

    return marcPaths;
  }

  private String getPackageName(DataField field) {
    String packageName;
    if (field.getDefinition() != null) {
      if (packageNameCache.containsKey(field.getDefinition()))
        packageName = packageNameCache.get(field.getDefinition());
      else {
        packageName = plugin.getPackageName(field);
        if (StringUtils.isBlank(packageName)) {
          logger.warning(String.format("%s has no package. /%s", field, field.getDefinition().getClass()));
          packageName = TagCategory.other.getPackageName();
        }
        packageNameCache.put(field.getDefinition(), packageName);
      }
    } else {
      packageName = TagCategory.other.getPackageName();
    }
    return packageName;
  }

  private List<String> extract(MarcRecord marcRecord, String tag, String subfield) {
    List<String> values = new ArrayList<>();
    List<DataField> fields = marcRecord.getDatafield(tag);
    if (fields != null && !fields.isEmpty()) {
      for (DataField field : fields) {
        List<MarcSubfield> subfieldInstances = field.getSubfield(subfield);
        if (subfieldInstances != null) {
          for (MarcSubfield subfieldInstance : subfieldInstances) {
            values.add(subfieldInstance.getValue());
          }
        }
      }
    }
    return values;
  }

  private <T extends Object> void count(T key, Map<T, Integer> counter) {
    counter.computeIfAbsent(key, s -> 0);
    counter.put(key, counter.get(key) + 1);
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    elementCardinality.put("all", new TreeMap<>());
    elementFrequency.put("all", new TreeMap<>());
    packageCounter.put("all", new TreeMap<>());
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
    savePackages(fileExtension, separator);
    saveMarcElements(fileExtension, separator);
  }

  private void saveLibraries003(String fileExtension, char separator) {
    logger.info("Saving Libraries003 file");
    var path = Paths.get(parameters.getOutputDir(), "libraries003" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("library" + separator + "count\n");
      library003Counter
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            writer.write(String.format("\"%s\"%s%d%n", entry.getKey(), separator, entry.getValue()));
          } catch (IOException e) {
            logger.log(Level.SEVERE, "saveLibraries003", e);
          }
        });
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveLibraries003", e);
    }
  }

  private void saveMarcElements(String fileExtension, char separator) {
    System.err.println("MARC elements");
    Path path = Paths.get(parameters.getOutputDir(), "marc-elements" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow(
        "documenttype", "path", "packageid", "package", "tag", "subfield",
        "number-of-record", "number-of-instances",
        "min", "max", "mean", "stddev", "histogram"
      ));
      elementCardinality
        .keySet()
        .stream()
        .forEach(documentType ->
          elementCardinality
            .get(documentType)
            .entrySet()
            .stream()
            .forEach(entry -> {
              try {
                String marcPath = entry.getKey();
                int cardinality = entry.getValue();
                writer.write(formatCardinality(separator, marcPath, cardinality, documentType));
              } catch (IOException e) {
                logger.log(Level.SEVERE, "saveMarcElements", e);
              }
            })
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "saveMarcElements", e);
    }
  }

  private void savePackages(String fileExtension, char separator) {
    logger.info("saving Packages...");
    var path = Paths.get(parameters.getOutputDir(), "packages" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write(createRow(separator, "documenttype", "packageid", "name", "label", "iscoretag", "count"));
      packageCounter
        .forEach((documentType, packages) ->
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
          })
        );
    } catch (IOException e) {
      logger.log(Level.SEVERE, "savePackages", e);
    }
  }

  private void saveLibraries(String fileExtension, char separator) {
    logger.info("Saving Libraries");
    var path = Paths.get(parameters.getOutputDir(), "libraries" + fileExtension);
    try (var writer = Files.newBufferedWriter(path)) {
      writer.write("library" + separator + "count\n");
      libraryCounter
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            writer.write(String.format("\"%s\"%s%d%n", entry.getKey(), separator, entry.getValue()));
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
                                   String documentType) {
    if (marcPath.equals("")) {
      logger.severe("Empty key from " + marcPath);
    }

    String marcPathLabel = marcPath.replace("!ind", "ind").replaceAll("\\|(\\d)$", "$1");
    int packageId = TagCategory.other.getId();
    String packageLabel = TagCategory.other.getLabel();
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
    Integer frequency = elementFrequency.get(documentType).get(marcPath);
    BasicStatistics statistics = new BasicStatistics(fieldHistogram.get(marcPath));
    if (!fieldHistogram.containsKey(marcPath)) {
      logger.warning(String.format("Field %s is not registered in histogram", marcPath));
    }

    List<Object> values = quote(
      Arrays.asList(
        documentType, marcPathLabel, packageId, packageLabel, tagLabel, subfieldLabel,
        frequency, cardinality,
        statistics.getMin(), statistics.getMax(),
        statistics.getMean(), statistics.getStdDev(),
        statistics.formatHistogram()
      )
    );

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
