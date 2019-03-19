package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.MarcSubfield;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.CompletenessParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.definition.tags.TagCategories;
import de.gwdg.metadataqa.marc.model.validation.ValidationErrorFormat;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class Completeness implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(Completeness.class.getCanonicalName());

  private final Options options;
  private CompletenessParameters parameters;
  private Map<String, Integer> library003Counter = new TreeMap<>();
  private Map<String, Integer> libraryCounter = new TreeMap<>();
  private Map<String, Integer> elementCounter = new TreeMap<>();
  private Map<String, String> tagCache = new HashMap<>();

  public Completeness(String[] args) throws ParseException {
    parameters = new CompletenessParameters(args);
    options = parameters.getOptions();
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
    count(marcRecord.getControl003().getContent(), library003Counter);
    for (String library : extract(marcRecord, "852", "a")) {
      count(library, libraryCounter);
    }
    for (DataField field : marcRecord.getDatafields()) {
      String packageName = extractPackageName(field);
      for (MarcSubfield subfield : field.getSubfields()) {
        String key = String.format("%s/%s$%s (%s > %s > %s)",
          packageName, field.getTag(), subfield.getCode(),
          TagCategories.getPackage(packageName), field.getDefinition().getLabel(), subfield.getLabel()
        );
        count(key, elementCounter);
      }
    }
  }

  private String extractPackageName(DataField field) {
    if (!tagCache.containsKey(field.getTag())) {
      String packageName = field.getDefinition().getClass().getPackage().getName()
        .replace("de.gwdg.metadataqa.marc.definition.tags.", "");
      tagCache.put(field.getTag(), packageName);
    }
    return tagCache.get(field.getTag());
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

  private void count(String key, Map<String, Integer> counter) {
    if (!counter.containsKey(key)) {
      counter.put(key, 0);
    }
    counter.put(key, counter.get(key) + 1);
  }

  @Override
  public void beforeIteration() {
    logger.info(parameters.formatParameters());
  }

  @Override
  public void fileOpened() {

  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration() {
    DecimalFormat format = new DecimalFormat();
    String fileExtension = ".csv";
    final char separator = getSeparator(parameters.getFormat());
    if (parameters.getFormat().equals(ValidationErrorFormat.TAB_SEPARATED)) {
      fileExtension = ".tsv";
    }

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

    System.err.println("Libraries");
    path = Paths.get(parameters.getOutputDir(), "libraries" + fileExtension);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write("library;count\n");
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

    System.err.println("MARC elements");
    path = Paths.get(parameters.getOutputDir(), "marc-elements" + fileExtension);
    try (BufferedWriter writer = Files.newBufferedWriter(path)) {
      writer.write("library;count\n");
      elementCounter
        .entrySet()
        .stream()
        .forEach(entry -> {
          String key = entry.getKey().replaceAll("^[^/]+/", "");
          // String number = format.format(entry.getValue());
          Integer number = entry.getValue();
          try {
            writer.write(String.format("%s%s%d%n", key, separator, number));
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private char getSeparator(ValidationErrorFormat format) {
    if (format.equals(ValidationErrorFormat.TAB_SEPARATED)) {
      return '\t';
    } else {
      return ';';
    }
  }

  @Override
  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    String message = String.format("java -cp metadata-qa-marc.jar %s [options] [file]", this.getClass().getCanonicalName());
    formatter.printHelp(message, options);
  }
}
