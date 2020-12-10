package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.*;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import de.gwdg.metadataqa.marc.definition.tags.TagCategory;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import de.gwdg.metadataqa.marc.utils.BasicStatistics;
import de.gwdg.metadataqa.marc.utils.TagHierarchy;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static de.gwdg.metadataqa.marc.Utils.createRow;
import static de.gwdg.metadataqa.marc.Utils.quote;

public class Completeness implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Completeness.class.getCanonicalName());
  private static final Pattern dataFieldPattern = Pattern.compile("^(\\d\\d\\d)\\$(.*)$");

  private final Options options;
  private CompletenessParameters parameters;
  private Map<String, Integer> library003Counter = new TreeMap<>();
  private Map<String, Integer> libraryCounter = new TreeMap<>();
  private Map<String, Integer> packageCounter = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementCardinality = new TreeMap<>();
  private Map<String, Map<String, Integer>> elementFrequency = new TreeMap<>();
  // private Map<String, String> tagCache = new HashMap<>();
  // private Map<String, Integer> libraryMap = new HashMap<>();
  // private Map<String, Integer> fieldMap = new HashMap<>();
  private Map<String, Map<Integer, Integer>> fieldHistogram = new HashMap<>();
  private boolean readyToProcess;

  public Completeness(String[] args) throws ParseException {
    parameters = new CompletenessParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
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

  }

  @Override
  public void processRecord(MarcRecord marcRecord, int recordNumber) throws IOException {
    Map<String, Integer> recordFrequency = new TreeMap<>();
    Map<String, Integer> recordPackageCounter = new TreeMap<>();

    String type = marcRecord.getType().getValue();
    if (marcRecord.getControl003() != null)
      count(marcRecord.getControl003().getContent(), library003Counter);

    for (String library : extract(marcRecord, "852", "a")) {
      count(library, libraryCounter);
    }

    for (DataField field : marcRecord.getDatafields()) {
      if (!parameters.getIgnorableFields().isEmpty() &&
          parameters.getIgnorableFields().contains(field.getTag()))
        continue;

      if (field.getDefinition() != null) {
        String packageName = Utils.extractPackageName(field);
        if (StringUtils.isBlank(packageName)) {
          System.err.println(field + " has no package. /" + field.getDefinition().getClass());
        }
        count(packageName, recordPackageCounter);
      }

      for (MarcSubfield subfield : field.getSubfields()) {
        String key = String.format("%s$%s", field.getTag(), subfield.getCode());
        if (!elementCardinality.containsKey(type))
          elementCardinality.put(type, new TreeMap<>());
        count(key, elementCardinality.get(type));
        count(key, elementCardinality.get("all"));
        count(key, recordFrequency);
      }
    }

    for (String key : recordFrequency.keySet()) {
      if (!elementFrequency.containsKey(type))
        elementFrequency.put(type, new TreeMap<>());
      count(key, elementFrequency.get(type));
      count(key, elementFrequency.get("all"));

      if (!fieldHistogram.containsKey(key)) {
        fieldHistogram.put(key, new TreeMap<>());
      }

      count(recordFrequency.get(key), fieldHistogram.get(key));
    }

    for (String key : recordPackageCounter.keySet()) {
      count(key, packageCounter);
    }
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
    if (!counter.containsKey(key)) {
      counter.put(key, 0);
    }
    counter.put(key, counter.get(key) + 1);
  }

  private void mapItem(String key, Map<String, Integer> counter) {
    if (!counter.containsKey(key)) {
      counter.put(key, counter.size() + 1);
    }
    counter.get(key);
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
    elementCardinality.put("all", new TreeMap<>());
    elementFrequency.put("all", new TreeMap<>());
  }

  @Override
  public void fileOpened(Path file) {

  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {
    DecimalFormat format = new DecimalFormat();
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
    System.err.println("Libraries003");
    Path path = Paths.get(parameters.getOutputDir(), "libraries003" + fileExtension);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write("library" + separator + "count\n");
      library003Counter
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            writer.write(String.format("\"%s\"%s%d%n", entry.getKey(), separator, entry.getValue()));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveMarcElements(String fileExtension, char separator) {
    Path path;
    System.err.println("MARC elements");
    path = Paths.get(parameters.getOutputDir(), "marc-elements" + fileExtension);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(
        StringUtils.join(
          Arrays.asList(
            "type", "path", "package", "tag", "subfield",
            "number-of-record", "number-of-instances",
            "min", "max", "mean", "stddev", "histogram"
          ),
          separator
        ) + "\n"
      );
      elementCardinality
        .keySet()
        .stream()
        .forEach(type -> {
          elementCardinality
            .get(type)
            .entrySet()
            .stream()
            .forEach(entry -> {
              try {
                writer.write(formatCardinality(separator, entry, type));
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          );
        });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void savePackages(String fileExtension, char separator) {
    Path path;
    System.err.println("Packages");
    path = Paths.get(parameters.getOutputDir(), "packages" + fileExtension);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write(createRow(separator, "name", "label", "iscoretag", "count"));
      packageCounter
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            String name = entry.getKey();
            int count = entry.getValue();
            TagCategory tagCategory = TagCategory.getPackage(name);
            String label = "";
            boolean isPartOfMarcScore = false;
            if (tagCategory != null) {
              name = tagCategory.getRange();
              label = tagCategory.getLabel();
              isPartOfMarcScore = tagCategory.isPartOfMarcCore();
            }
            writer.write(createRow(
              separator, quote(name), quote(label), isPartOfMarcScore, count
            ));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveLibraries(String fileExtension, char separator) {
    Path path;
    System.err.println("Libraries");
    path = Paths.get(parameters.getOutputDir(), "libraries" + fileExtension);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write("library" + separator + "count\n");
      libraryCounter
        .entrySet()
        .stream()
        .forEach(entry -> {
          try {
            writer.write(String.format("\"%s\"%s%d%n", entry.getKey(), separator, entry.getValue()));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @NotNull
  private String formatCardinality(char separator,
                                   Map.Entry<String, Integer> entry,
                                   String type) {
    String key = entry.getKey();
    if (key.equals("")) {
      logger.severe("Empty key from " + key);
    }

    TagHierarchy tagHierarchy = TagHierarchy.createFromPath(key);
    String packageLabel = "";
    String tagLabel = "";
    String subfieldLabel = "";
    if (tagHierarchy != null) {
      packageLabel = tagHierarchy.getPackageLabel();
      tagLabel = tagHierarchy.getTagLabel();
      subfieldLabel = tagHierarchy.getSubfieldLabel();
    } else {
      logger.severe("Key can not be found in the TagHierarchy: " + key);
    }

    Integer cardinality = entry.getValue();
    Integer frequency = elementFrequency.get(type).get(key);
    BasicStatistics statistics = new BasicStatistics(fieldHistogram.get(key));
    if (!fieldHistogram.containsKey(key)) {
      logger.warning(String.format(
        "Field %s is not registered in histogram", key));
    }

    List<Object> values = quote(
      Arrays.asList(
        type, key, packageLabel, tagLabel, subfieldLabel,
        frequency, cardinality,
        statistics.getMin(), statistics.getMax(),
        statistics.getMean(), statistics.getStdDev(),
        statistics.formatHistogram()
      )
    );

    String record = StringUtils.join(values, separator) + "\n";
    return record;
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
