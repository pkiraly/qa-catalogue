package de.gwdg.metadataqa.marc.cli;

import de.gwdg.metadataqa.marc.DataField;
import de.gwdg.metadataqa.marc.MarcRecord;
import de.gwdg.metadataqa.marc.analysis.NetworkAnalyzer;
import de.gwdg.metadataqa.marc.cli.parameters.CommonParameters;
import de.gwdg.metadataqa.marc.cli.parameters.NetworkAction;
import de.gwdg.metadataqa.marc.cli.parameters.NetworkParameters;
import de.gwdg.metadataqa.marc.cli.processor.MarcFileProcessor;
import de.gwdg.metadataqa.marc.cli.utils.RecordIterator;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.marc4j.marc.Record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static de.gwdg.metadataqa.marc.Utils.createRow;

public class NetworkAnalysis implements MarcFileProcessor, Serializable {

  private static final Logger logger = Logger.getLogger(NetworkAnalysis.class.getCanonicalName());

  private final NetworkParameters parameters;
  private final Options options;
  private final boolean readyToProcess;
  private final List<String> orphans = new ArrayList<>();
  private Path path;
  private BufferedWriter writer;

  public NetworkAnalysis(String[] args) throws ParseException {
    parameters = new NetworkParameters(args);
    options = parameters.getOptions();
    readyToProcess = true;
  }

  public static void main(String[] args) {
    MarcFileProcessor processor = null;
    try {
      processor = new NetworkAnalysis(args);
    } catch (ParseException e) {
      System.err.println(createRow("ERROR. ", e.getLocalizedMessage()));
      // processor.printHelp(processor.getParameters().getOptions());
      System.exit(0);
    }
    logger.info("Action: " + ((NetworkParameters)processor.getParameters()).getAction());
    if (((NetworkParameters)processor.getParameters()).getAction().equals(NetworkAction.PAIRING)) {
      ((NetworkAnalysis)processor).pairIds();
    } else {
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
  }

  private void pairIds() {
    logger.info("pairIds");
    Path outputPath = Paths.get(parameters.getOutputDir(), "network-pairs.csv");
    try {
      writer = Files.newBufferedWriter(outputPath);
      writer.write(createRow("id1", "id2"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    AtomicInteger lineNr = new AtomicInteger();
    Path inputPath = Paths.get(parameters.getOutputDir(), "network-by-concepts.csv");
    try (Stream<String> stream = Files.lines(Paths.get(inputPath.toString()))) {
      stream.forEach(
        line -> {
          lineNr.getAndIncrement();
          if (lineNr.get() % 100 == 0)
            logger.info("" + lineNr.get());
          String[] parts = line.split(",");
          String[] ids = parts[2].split(";");
          if (ids.length > 1) {
            if (ids.length > parameters.getGroupLimit()) {
              logger.info(String.format("%d is greater than %d. The array will will truncated.", ids.length, parameters.getGroupLimit()));
              ids = Arrays.copyOfRange(ids, 0, parameters.getGroupLimit());
            }

            String[] encoded = new String[ids.length];
            for (int i = 0; i<ids.length; i++) {
              encoded[i] = encode(ids[i]);
            }
            for (int i = 0; i < encoded.length-1; i++) {
              for (int j = (i+1); j < encoded.length; j++) {
                try {
                  writer.write(createRow(encoded[i], encoded[j]));
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }
          }
        }
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String encode(String id) {
    if (id.contains("+"))
      return id;
    else
      return Integer.toString(Integer.parseInt(id), Character.MAX_RADIX);
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
    NetworkAnalyzer analyzer = new NetworkAnalyzer(marcRecord);
    Set<DataField> collector = analyzer.process(recordNumber);
    if (collector.size() > 0) {
      for (DataField field : collector) {
        writer.write(createRow(field.toString().hashCode(), recordNumber));
      }
    }
    orphans.add(marcRecord.getId(true));
  }

  @Override
  public void beforeIteration() {
    path = Paths.get(parameters.getOutputDir(), "network.csv");
    logger.info(parameters.formatParameters());
    try {
      writer = Files.newBufferedWriter(path);
      writer.write(createRow("concept", "id"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void fileOpened(Path path) {

  }

  @Override
  public void fileProcessed() {

  }

  @Override
  public void afterIteration(int numberOfprocessedRecords) {
  }

  @Override
  public void printHelp(Options options) {

  }

  @Override
  public boolean readyToProcess() {
    return readyToProcess;
  }
}
